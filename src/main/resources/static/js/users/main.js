$(function () {

    var _pageSize; // 存储用于搜索

    // 获取添加用户的界面
    $("#addUser").click(function () {
        debugger
        $.ajax({
            url: "/manage/add_user_view",
            success: function (data) {
                $("#userFormContainer").html(data);
            },
            error: function (data) {
                toastr.error("获取添加用户页面失败!");
            }
        });
    });

    // 添加用户(提交变更后，清空表单)
    $("#submitEdit").click(function () {
        debugger
        var id = $("input[name = 'id']").val();
        var url;
        if (id==undefined){
            url="/localAuth/register";
            $.ajax({
                url: url,
                type: 'POST',
                data: $('#userForm').serialize(),
                success: function (data) {
                    $('#userForm')[0].reset();
                    debugger
                    if (data.status == 0) {
                        toastr.success(data.message);
                        // 从新刷新主界面
                        getUersByName(0, 10);
                    } else if (data.status == 1) {
                        toastr.warning(data.message);
                    } else {
                        toastr.error(data.message);
                    }
                },
                error: function () {
                    toastr.error("添加用户失败!");
                }
            });
        } else {
            url="/manage/user/";
            $.ajax({
                url: url,
                type: 'POST',
                data: $('#userFormEdit').serialize(),
                success: function (data) {
                    // $('#userForm')[0].reset();
                    debugger
                    if (data.status == 0) {
                        debugger
                        toastr.success(data.message);
                        // 从新刷新主界面
                        getUersByName(0, 10);
                    } else if (data.status == 1) {
                        toastr.warning(data.message);
                    } else {
                        toastr.error(data.message);
                    }
                },
                error: function () {
                    toastr.error("修改用户信息失败!");
                }
            });
        }
    });

    // 根据用户名、页面索引、页面大小获取用户列表
    function getUersByName(pageIndex, pageSize) {
        debugger
        $.ajax({
            url: "/manage/user/list",
            contentType: 'application/json',
            data: {
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
                "keyword": $("#searchName").val()
            },
            success: function (data) {
                $("#mainContainer").html(data);
            },
            error: function () {
                toastr.error("查询用户列表失败!");
            }
        });
    }

    // 删除用户
    $("#rightContainer").on("click", ".blog-delete-user", function () {

        $.ajax({
            url: "/manage/user/" + $(this).attr("userId"),
            type: 'DELETE',
            success: function (data) {
                debugger
                if (data.status == 0) {
                    toastr.success(data.message);
                    // 从新刷新主界面
                    getUersByName(0, 10);
                } else if (data.status == 1) {
                    toastr.warning(data.message);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("删除用户失败!");
            }
        });
    });

    // 搜索用户
    $("#searchNameBtn").click(function() {
        getUersByName(0, _pageSize);
    });

    // 获取修改用户的界面
    $("#rightContainer").on("click",".blog-edit-user", function () {
        $.ajax({
            url: "/manage/user/edit/" + $(this).attr("userId"),
            success: function(data){
                $("#userFormContainer").html(data);
            },
            error : function() {
                toastr.error("获取修改用户的页面失败!");
            }
        });
    });

});