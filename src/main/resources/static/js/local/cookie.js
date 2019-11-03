$(function () {
    var allcookies = document.cookie;
    var cookie_start = allcookies.indexOf("token"); //寻找名第一次出现的位置

    if (cookie_start != -1) {
        $('#login').hide();
    } else {
        $('#myblog').hide();
    }
});