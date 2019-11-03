/*!
 * blog.html 页面脚本.
 * 
 * @since: 1.0.0 2017-03-26
 * @author Way Lau <https://waylau.com>
 */
"use strict";
//# sourceURL=blog.js

// DOM 加载完再执行
$(function() {
	$.catalog("#catalog", ".post-content");
	
	// 处理删除博客事件
	
	$(".blog-content-container").on("click",".blog-delete-blog", function () {
		// 获取 CSRF Token
		// var csrfToken = $("meta[name='_csrf']").attr("content");
		// var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		$.ajax({ 
			 url: blogUrl,
			 type: 'DELETE', 
			/* beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
             },*/
            success: function (data) {
                debugger
                if (data.status == 0) {
                    toastr.success(data.message);
                    //跳转
                    window.location = data.data;
                } else if (data.status == 1) {
                    toastr.warning(data.message);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                debugger
                toastr.error("删除博客失败!");
            }
		 });
	});
	
	// 获取评论列表
	function getCommnet(blogId) {
		$.ajax({ 
			 url: '/comment/list',
			 type: 'GET', 
			 data:{"blogId":blogId},
			 success: function(data){
				$("#mainContainer").html(data);
	
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	}
	
	// 提交评论
	$(".blog-content-container").on("click","#submitComment", function () {
		$.ajax({ 
			 url: '/comment',
			 type: 'POST', 
			 data:{"blogId":blogId, "commentContent":$('#commentContent').val()},
			 success: function(data){

                 if (data.status == 0) {
                     toastr.success(data.message);
                     // 清空评论框
                     $('#commentContent').val('');
                     // 获取评论列表
                     getCommnet(blogId);
                 } else if (data.status == 1) {
                     toastr.warning(data.message);
                 } else {
                     toastr.error(data.message);
                 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	// 删除评论
	/*$(".blog-content-container").on("click",".blog-delete-comment", function () {
		// 获取 CSRF Token 
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
 		
		$.ajax({ 
			 url: '/comments/'+$(this).attr("commentId")+'?blogId='+blogId, 
			 type: 'DELETE', put
			 beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
             },
			 success: function(data){
				 if (data.success) {
					 // 获取评论列表
					 getCommnet(blogId);
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});*/
	
	
	// 提交点赞
	/*$(".blog-content-container").on("click","#submitVote", function () {
		// 获取 CSRF Token 
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
 		
		$.ajax({ 
			 url: '/votes', 
			 type: 'POST', 
			 data:{"blogId":blogId},
			 beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
             },
			 success: function(data){
				 if (data.success) {
					 toastr.info(data.message);
						// 成功后，重定向
					 window.location = blogUrl;
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});*/
	
	// 提交点赞
	/*$(".blog-content-container").on("click","#cancelVote", function () {
		// 获取 CSRF Token 
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
 		
		$.ajax({ 
			 url: '/votes/'+$(this).attr('voteId')+'?blogId='+blogId, 
			 type: 'DELETE', 
			 beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
             },
			 success: function(data){
				 if (data.success) {
					 toastr.info(data.message);
					// 成功后，重定向
					 window.location = blogUrl;
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});*/
	
	// 初始化 博客评论
	// getCommnet(blogId);
	
});