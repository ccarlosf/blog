/*!
 * u main JS.
 *
 * @since: 1.0.0 2017/3/9
 * @author Way Lau <https://waylau.com>
 */
"use strict";
//# sourceURL=u.js

// DOM 加载完再执行
$(function () {

    var _pageSize; // 存储用于搜索
    getCategoryByName(username);

    // 根据用户名、页面索引、页面大小获取博文列表
    function getBlogsByName(pageIndex, pageSize) {
        $.ajax({
            url: "/u/" + username + "/blog_list",
            contentType: 'application/json',
            data: {
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
                "category": categoryId,
                "keyword": $("#keyword").val()
            },
            success: function (data) {
                $("#mainContainer").html(data);

                // 如果是分类查询，则取消最新、最热选中样式
                if (categoryId) {
                    $(".nav-item .nav-link").removeClass("active");
                }
            },
            error: function () {
                toastr.error("获取用户博客列表失败!");
            }
        });
    }

    // 分页
    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        debugger
        getBlogsByName(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    // 最新\最热切换事件
    $(".nav-item .nav-link").click(function () {

        var url = $(this).attr("url");

        // 先移除其他的点击样式，再添加当前的点击样式
        $(".nav-item .nav-link").removeClass("active");
        $(this).addClass("active");

        // 加载其他模块的页面到右侧工作区
        $.ajax({
            url: url + '&async=true',
            success: function (data) {
                $("#mainContainer").html(data);
            },
            error: function () {
                toastr.error("切换个人最新、最热博文列表失败!");
            }
        })
        // 清空搜索框内容
        $("#keyword").val('');
    });

    // 获取分类列表
    function getCategoryByName(username) {
        // 获取 CSRF Token
        debugger
        $.ajax({
            url: '/category',
            type: 'GET',
            data: {"username": username},
            success: function (data) {
                $("#catalogMain").html(data);
            },
            error: function () {
                toastr.error("获取分类列表失败!");
            }
        });
    }

    // 获取添加、修改分类的页面
    $(".blog-content-container").on("click", ".blog-add-catalog", function () {
        $.ajax({
            url: '/category/edit',
            type: 'GET',
            success: function (data) {
                $("#catalogFormContainer").html(data);
            },
            error: function () {
                toastr.error("获取添加、修改分类的页面失败!");
            }
        });
    });

    // 提交分类
    $("#submitEditCatalog").click(function () {
        $.ajax({
            url: '/category',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                "username": username,
                "category": {"id": $('#categoryId').val(), "categoryName": $('#categoryName').val()}
            }),
            success: function (data) {
                debugger
                if (data.status == 0) {
                    toastr.success(data.message);
                    getCategoryByName(username);
                } else if (data.status == 1) {
                    toastr.warning(data.message);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("添加分类失败!");
            }
        });
    });

    // 删除分类
    $(".blog-content-container").on("click", ".blog-delete-catalog", function () {
        debugger
        $.ajax({
            url: '/category/' + $(this).attr('categoryId') + '?username=' + username,
            type: 'DELETE',
            success: function (data) {
                debugger
                if (data.status == 0) {
                    toastr.success(data.message);
                    getCategoryByName(username);
                } else if (data.status == 1) {
                    toastr.warning(data.message);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("删除分类失败!");
            }
        });
    });

    // 获取修改某个分类的页面
    $(".blog-content-container").on("click",".blog-edit-catalog", function () {
        $.ajax({
            url: '/category/edit/'+$(this).attr('categoryId'),
            type: 'GET',
            success: function(data){
                $("#catalogFormContainer").html(data);
            },
            error : function() {
                toastr.error("获取修改分类的界面失败!");
            }
        });
    });

});