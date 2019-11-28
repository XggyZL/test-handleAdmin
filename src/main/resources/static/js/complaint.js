layui.use(['element', 'layer', 'form', 'laydate', 'form', 'table'], function () {
    var element = layui.element,
        layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate;


    form.verify({
        driverTel: [/^1[34578]\d{9}$/, '电话号码格式不正确']
    });


    $("#driverTel").on('change', function () {
        $.ajax({
            type: 'post',
            url: 'panduanTel',
            data: {
                driverTel: $("#driverTel").val()
            },
            dataType: 'json',
            success: function (data) {
                if (data.code != '00000') {
                    $("#contentSubmit").attr("disabled", "disabled");
                    layer.alert("该手机号，暂时不可提交")
                } else {
                    $("#contentSubmit").removeAttr("disabled", "disabled");
                }
            }
        });
    });
    form.on("submit(contentSubFilter)", function (data) {
        if ($("#driverContent").val() == 0) {
            layer.alert("请填写内容再提交");
            return;
        }
        $.ajax({
            type: 'post',
            url: 'saveComplaintInfo',
            data: JSON.stringify({
                "driverTel": $("#driverTel").val(),
                "driverContent": $(".driverContent").val()
            }),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                if (data.code == '00000') {
                    window.location.href = "complaintSucess";
                } else {
                    window.location.href = "complaintFail";
                }
            }
        });
        return false;
    });

})