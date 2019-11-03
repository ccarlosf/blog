$(function () {
    // 登录验证的controller url
    var loginUrl = '/localAuth/login';
    /**
     * 登录验证
     */
    $('#submit').click(function () {
        var username = $('#username').val();
        var password = $('#password').val();
        // 访问后台进行登录验证
        $.ajax({
            url: loginUrl,
            async: false,
            cache: false,
            type: "post",
            dataType: 'json',
            data: {
                username: username,
                password: password
            },
            success: function (data) {
                if (data.status == 0) {
                    // 登录成功，跳转到首页页
                    toastr.success(data.message);
                    window.location.href = '/frontend/index';
                } else {
                    toastr.warning(data.message);
                }
            }
        });
    });
});