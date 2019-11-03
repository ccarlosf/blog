package com.ccarlos.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.ccarlos.blog.common.CodeMessage;
import com.ccarlos.blog.common.constant.BlogConst;
import com.ccarlos.blog.common.constant.MQConst;
import com.ccarlos.blog.common.constant.RabbitMQConst;
import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.dao.BlogMapper;
import com.ccarlos.blog.dao.MessageLogMapper;
import com.ccarlos.blog.model.Blog;
import com.ccarlos.blog.model.BlogWithBLOBs;
import com.ccarlos.blog.model.MessageLog;
import com.ccarlos.blog.mq.rabbitmq.producer.RabbitMQBlogProducer;
import com.ccarlos.blog.repository.BlogRepository;
import com.ccarlos.blog.service.BlogService;
import com.ccarlos.blog.util.UUIDUtil;
import com.github.rjeschke.txtmark.Processor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @description: 博客服务实现
 * @author: ccarlos
 * @date: 2019/5/28 13:53
 */
@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private BlogMapper blogMapper;

	@Autowired
	private MessageLogMapper messageLogMapper;

	@Autowired
	private RabbitMQBlogProducer rabbitMQBlogProducer;

	/**
	 * @description: 根据用户名和标题查询个人最热的博客列表
	 * @author: ccarlos
	 * @date: 2019/5/28 14:16
	 * @param: user 用户对象
	 * @param: title 标题
	 * @param: pageable 分页对象
	 * @return: org.springframework.data.domain.Page<com.ccarlos.blog.model.Blog>
	 */
	@Override
	public Page<Blog> getHottestBlogListByUserIdAndTitle(Pageable pageable, Long userId, String title) {
//		title="%"+title+"%";
		title = new StringBuilder().append("%").append(title).append("%").toString();
		Page<Blog> blogPage = blogRepository.findByUserIdAndTitleLike(userId, title, pageable);
		return blogPage;
	}

	/**
	 * @description: 根据用户名和标题查询个人最新的博客列表
	 * @author: ccarlos
	 * @date: 2019/5/28 16:02
	 * @param: pageable 分页对象
	 * @param: user 用户对象
	 * @param: title 标题
	 * @return: org.springframework.data.domain.Page<com.ccarlos.blog.model.Blog>
	 */
	@Override
	public Page<Blog> getNewestBlogListByUserIdAndTitle(Pageable pageable, Long userId, String title) {
//		title="%"+title+"%";
		title = new StringBuilder().append("%").append(title).append("%").toString();
		Page<Blog> blogPage = blogRepository.findByUserIdAndTitleLikeOrderByCreateTimeDesc(pageable, userId, title);
		return blogPage;
	}

	/**
	 * @description: 添加博客
	 * @author: ccarlos
	 * @date: 2019/6/18 15:33
	 * @param: blogWithBLOBs 博客
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@Override
	@Transactional
	public JsonResponse<String> addBlog(BlogWithBLOBs blogWithBLOBs) {
		JsonResponse checkResponse = this.checkBlogIsNull(blogWithBLOBs);
		if (checkResponse.isSuccess()) {

			//插入博客表
			blogWithBLOBs.setHtmlContent(Processor.process(blogWithBLOBs.getContent()));
			blogWithBLOBs.setCreateMessageId(System.currentTimeMillis() + "$" + UUIDUtil.getUUID());
			blogWithBLOBs.setReadCount(BlogConst.Count.DEFAULT_READ_COUNT);
			blogWithBLOBs.setCommentCount(BlogConst.Count.DEFAULT_COMMENT_COUNT);
			blogWithBLOBs.setVoteCount(BlogConst.Count.DEFAULT_VOTE_COUNT);
			int rowCount = blogMapper.insert(blogWithBLOBs);
			if (rowCount > 0) {

				//发送消息
//				rabbitMQBlogProducer.sendBlog(blogWithBLOBs);

				//插入消息记录表
				MessageLog messageLog = MessageLog.builder().messageId(blogWithBLOBs.getCreateMessageId())
						.message(JSON.toJSONString(blogWithBLOBs)).status(RabbitMQConst.MessageStatus.SENDING)
						.messageType(RabbitMQConst.MessageType.CREATE).mqType(MQConst.MQType.RABBIT_MQ)
						.sendCount(RabbitMQConst.MessageCount.DEFAULT_SEND_COUNT) //TODO
						.consumeCount(RabbitMQConst.MessageCount.DEFAULT_CONSUME_COUNT)
						.tryCount(RabbitMQConst.MessageCount.DEFAULT_TRY_COUNT)
						.nextRetryTime(DateUtils.addMinutes(new Date(), RabbitMQConst.MessageTime.SEND_TIMEOUT))
						.createTime(new Date()).updateTime(new Date()).build();
				messageLogMapper.insert(messageLog);

				//发送消息
				rabbitMQBlogProducer.sendBlog(blogWithBLOBs);

				return JsonResponse.createBySuccessMessage("添加博客成功");
			}
			return JsonResponse.createByErrorMessage("添加博客失败");
		} else {
			return checkResponse;
		}
	}

	/**
	 * @description: 根据博客id获取博客信息
	 * @author: ccarlos
	 * @date: 2019/7/2 15:24
	 * @param: id 博客id
	 * @return: com.ccarlos.blog.common.JsonResponse<com.ccarlos.blog.model.BlogWithBLOBs>
	 */
	@Override
	public JsonResponse<BlogWithBLOBs> getBlogById(Long id) {
		if (id == null) {
//			return JsonResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
			return JsonResponse.createByErrorCodeMessage(CodeMessage.BLOG_ID_NULL.getCode(), CodeMessage.BLOG_ID_NULL.getMessage());
		}
		BlogWithBLOBs blogWithBLOBs = blogMapper.selectByPrimaryKey(id);
		if (blogWithBLOBs == null) {
			return JsonResponse.createByErrorMessage("该博客不存在");
		}
		return JsonResponse.createBySuccess(blogWithBLOBs);
	}

	/**
	 * @description: 增加阅读量
	 * @author: ccarlos
	 * @date: 2019/7/2 15:42
	 * @param: id 博客id
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@Override
	public JsonResponse<String> increaseReadCount(Long id) {
		BlogWithBLOBs blogWithBLOBs = new BlogWithBLOBs();
		Blog blog = Blog.builder().id(id).readCount(BlogConst.Count.READ_ADD_COUNT).build();
		BeanUtils.copyProperties(blog, blogWithBLOBs);
		int resultCount = blogMapper.updateBlogCount(blogWithBLOBs);
		if (resultCount > 0) {
			return JsonResponse.createBySuccessMessage("更新阅读量成功");
		}
		return JsonResponse.createByErrorMessage("更新阅读量失败");
	}

	/**
	 * @description: 根据id删除博客
	 * @author: ccarlos
	 * @date: 2019/7/3 10:59
	 * @param: id 博客id
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@Override
	@Transactional
	public JsonResponse<String> removeBlog(Long id) {
		int resultCount = blogMapper.deleteByPrimaryKey(id);
		if (resultCount == 0) {
			return JsonResponse.createBySuccessMessage("删除博客失败");
		}
		return JsonResponse.createBySuccessMessage("删除博客成功");
	}

	/**
	 * @description: 校验博客数据是否为空
	 * @author: ccarlos
	 * @date: 2019/6/18 17:03
	 * @param: blogWithBLOBs 博客
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	private JsonResponse<String> checkBlogIsNull(BlogWithBLOBs blogWithBLOBs) {
		if (StringUtils.isBlank(blogWithBLOBs.getTitle())) {
			return JsonResponse.createByErrorMessage("标题不能为空");
		}
		if (StringUtils.isBlank(blogWithBLOBs.getBlogDigest())) {
			return JsonResponse.createByErrorMessage("摘要不能为空");
		}
		if (StringUtils.isBlank(blogWithBLOBs.getContent())) {
			return JsonResponse.createByErrorMessage("内容不能为空");
		}
		return JsonResponse.createBySuccess();
	}

	/**
	 * @description:c 修改博客
	 * @author: ccarlos
	 * @date: 2019/7/4 10:58
	 * @param: blogWithBLOBs 博客
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@Override
	public JsonResponse<String> modifyBlog(BlogWithBLOBs blogWithBLOBs) {
//		blogWithBLOBs.setHtmlContent(Processor.process(blogWithBLOBs.getContent()));
		blogWithBLOBs.setHtmlContent(blogWithBLOBs.getContent());
		JsonResponse checkResponse = this.checkBlogIsNull(blogWithBLOBs);
		if (checkResponse.isSuccess()) {
			int resultCount = blogMapper.updateByPrimaryKeySelective(blogWithBLOBs);
			if (resultCount == 0) {
				return JsonResponse.createByErrorMessage("修改博客失败");
			}
			return JsonResponse.createBySuccessMessage("修改博客成功");
		}else {
			return checkResponse;
		}
	}
}
