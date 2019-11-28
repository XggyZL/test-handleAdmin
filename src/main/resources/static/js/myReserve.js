layui.use(['element', 'layer', 'form', 'laydate', 'form', 'table'], function () {
    var element = layui.element,
        layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate;

    $(document).ready(function () {
        $(".page_bd_spacing :first-child").find(".other-content").addClass("openFirst");
    });

    form.verify({
        tel: [/^1[34578]\d{9}$/, '电话号码格式不正确']
    });

    $(".cancelReserve").on('click', function () {
        var stoReserveId = $(this).parent().parent().parent().find(".queueStoReserveId").text();
        $.ajax({
            type: 'post',
            url: 'cancelReserveOrder?stoReserveId=' + stoReserveId,
            success: function (data) {
                if (data.code == '00000') {
                    window.location.href = "cancelSucess?stoReserveId=" + stoReserveId;
                } else {
                    window.location.href = "cancelFail?stoReserveId=" + stoReserveId;
                }
            }
        })
    });

    $(".arrivaled").on('click', function () {
        var stoReserveId = $(this).parent().parent().parent().find(".queueStoReserveId").text();
        $.ajax({
            type: 'post',
            url: 'saveQueueStoCodeInfo',
            data: {
                "stoReserveId": stoReserveId
            },
            dataType: 'json',
            success: function (data) {
                if (data.code == '00000') {
                    window.location.href = "queueCodeSucess?stoReserveId=" + stoReserveId;
                } else {
                    window.location.href = "queueCodeFail?stoReserveId=" + stoReserveId;
                }
            }
        })
    });


    $(".title-flow").on('click', function () {
        $(this).parent().children(".other-content").slideToggle();
    });

    $(".toFinish").on('click', function () {
        var stoReserveId = $(this).parent().parent().parent().find(".queueStoReserveId").text();
        $.ajax({
            type: 'post',
            url: 'finishOrder',
            data: {
                "stoReserveId": stoReserveId
            },
            dataType: 'json',
            success: function (data) {
                if (data.code == '00000') {
                    window.location.href("finishSucess");
                } else {
                    window.location.href("finishFail");
                }
            }
        })
    });


    $("#squreBack").on('click', function () {
        window.location.href = "home.html";
    });


})