$(function () {
    var avatarApi;

    // 获取编辑用户头像的界面
    $(".blog-content-container").on("click", ".blog-edit-avatar", function () {
        avatarApi = "/u/avatar";
        $.ajax({
            url: avatarApi,
            success: function (data) {
                $("#avatarFormContainer").html(data);
            },
            error: function () {
                toastr.error("获取编辑用户头像的界面失败!");
            }
        });
    });

    // 提交用户头像的图片数据
    $("#submitEditAvatar").on("click", function () {
        var form = $('#avatarformid')[0];
        var formData = new FormData(form);   //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数
        var base64Codes = $(".cropImg > img").attr("src");
        formData.append("file", convertBase64UrlToBlob(base64Codes));  //append函数的第一个参数是后台获取数据的参数名,和html标签的input的name属性功能相同

        $.ajax({
            url: '/mongo/file/upload',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false,
            success: function (data) {
                // toastr.success("成功存储个人头像!");
                var avatarUrl = data;

                // 保存头像更改到数据库
                $.ajax({
                    url: avatarApi,
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({"id": Number($("#userId").val()), "avatar": avatarUrl}),
                    success: function (data) {
                        debugger
                        if (data.status == 0) {
                            // 成功后，置换头像图片
                            $(".blog-avatar").attr("src", data.data);
                            toastr.success(data.message)
                        } else if (data.status == 1) {
                            debugger
                            toastr.warning(data.message);
                        } else {
                            toastr.error(data.message);
                        }
                    },
                    error: function () {
                        toastr.error("修改个人头像失败，请稍后重试!");
                    }
                });
            },
            error: function () {
                toastr.error("修改个人头像失败，请稍后重试!");
            }
        })
    });

    /**
     * 将以base64的图片url数据转换为Blob
     * @param urlData
     *            用url方式表示的base64图片数据
     */
    function convertBase64UrlToBlob(urlData) {

        var bytes = window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte

        //处理异常,将ascii码小于0的转换为大于0
        var ab = new ArrayBuffer(bytes.length);
        var ia = new Uint8Array(ab);
        for (var i = 0; i < bytes.length; i++) {
            ia[i] = bytes.charCodeAt(i);
        }

        return new Blob([ab], {type: 'image/png'});
    }
})