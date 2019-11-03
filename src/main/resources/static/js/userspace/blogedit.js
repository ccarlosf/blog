/*!
 * blogedit.html 页面脚本.
 *
 * @since: 1.0.0 2017-03-26
 * @author Way Lau <https://waylau.com>
 */
"use strict";
//# sourceURL=blogedit.js

// DOM 加载完再执行
$(function() {

    // 初始化 md 编辑器
    $("#md").markdown({
        language: 'zh',
        fullscreen: {
            enable: true
        },
        resize:'vertical',
        localStorage:'md',
        imgurl: '/mongo/file',
        base64url: '/mongo/file'
    });



    // 初始化下拉
    $('.form-control-chosen').chosen();


    // 初始化标签
    $('.form-control-tag').tagsInput({
        'defaultText':'输入标签'

    });

    $("#uploadImage").click(function() {
        debugger
        $.ajax({
            url: '/mongo/file/upload',
            type: 'POST',
            cache: false,
            data: new FormData($('#uploadformid')[0]),
            processData: false,
            contentType: false,
            success: function(data){
                var mdcontent=$("#md").val();
                $("#md").val(mdcontent + "\n![]("+data +") \n");

            }
        }).done(function(res) {
            $('#file').val('');
        }).fail(function(res) {});
    })

    // 发布博客
    $("#submitBlog").click(function() {
        $.ajax({
            url: '/u/blogs/edit',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data:JSON.stringify({"id":$('#blogId').val(),
                "title": $('#title').val(),
                "blogDigest": $('#blogDigest').val() ,
                "content": $('#md').val(),
                "categoryId":$('#catalogSelect').val(),
                "label":$('.form-control-tag').val()
            }),
            success: function (data) {
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
                toastr.error("添加博客失败!");
            }
        })
    })


});