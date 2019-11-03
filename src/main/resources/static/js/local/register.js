$(function () {
    // 注册验证的controller url
    var registerUrl = '/localAuth/register';
    $('#submit').click(function () {
        var email = $('#email').val();
        var mobile = $('#mobile').val();
        var username = $('#username').val();
        var nickname = $('#nickname').val();
        var password = $('#password').val();
        // 访问后台进行注册验证
        $.ajax({
            url: registerUrl,
            async: false,
            cache: false,
            type: "post",
            dataType: 'json',
            data: {
                email: email,
                mobile: mobile,
                username: username,
                nickname: nickname,
                password: password
            },
            success: function (data) {
                if (data.status == 0) {
                    // 注册成功，跳转到登录页
                    toastr.success(data.message);
                    window.location.href = '/local/login_view';
                } else if (data.status == 1) {
                    toastr.warning(data.message);
                } else {
                    toastr.error(data.message);
                }
            }
        });
    });
});