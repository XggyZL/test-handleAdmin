function lingFunction() {
    $("#lingHandleId").val($("#lingProductId").val())
}

function zhengFunction() {
    $("#zhengHandleId").val($("#zhengProductId").val())
}

function reset(){
    window.location.reload();
}


layui.use(['element', 'layer', 'form', 'laydate', 'form', 'table'], function () {
    var element = layui.element,
        layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate;


    laydate.render({
        elem: '#lingChuchangDate'
    });
    laydate.render({
        elem: '#zhengChuchangDate'
    });

    //注册、零部件handle编码
    form.on("submit(reserveLingFilter)", function (data) {
        if ($("#lingProduceId").val() == "") {
            layer.alert("请填写内容再提交");
            return;
        }
        $.ajax({
            type: 'post',
            async: true,
            url: 'lingZhuceSub',
            data: JSON.stringify({
                "handleId": "86.1000.1002/" + $("#lingProductId").val(),
                "productId": $("#lingProductId").val(),
                "productName": $("#lingProductName").val(),
                "produceModel": $("#lingProductModel").val(),
                "chuchangDate": $("#lingChuchangDate").val(),
                "productCompany": $("#lingProductCompany").val()
            }),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                if (data.code == '00000') {
                    $("#lingCode").empty();
                    var qrcode = new QRCode(document.getElementById("lingCode"), {
                        width: 100,
                        height: 100,
                        text: data.data
                    });
                    qrcode.makeCode(data.data);
                    layer.alert("零部件注册成功,handleId：" + $("#lingProductId").val());
                } else {
                    layer.alert("零部件注册失败，请更新handleID,重新尝试");
                }
            }
        });
        return false;
    });

//注册、整机产品handle编码
    form.on("submit(reserveZhengFilter)", function (data) {
        if ($("#zhengProduceId").val() == "") {
            layer.alert("请填写内容再提交");
            return;
        }
        $.ajax({
            type: 'post',
            async: true,
            url: 'zhengjiZhuceSub',
            data: JSON.stringify({
                "handleId": "86.1000.1002/" + $("#zhengProductId").val(),
                "productId": $("#zhengProductId").val(),
                "productName": $("#zhengProductName").val(),
                "produceModel": $("#zhengProductModel").val(),
                "chuchangDate": $("#zhengChuchangDate").val(),
                "productCompany": $("#zhengProductCompany").val(),
                "childHandleId": $("#childHandleId").val()
            }),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                if (data.code == '00000') {
                    $("#zhengCode").empty();
                    var qrcode = new QRCode(document.getElementById("zhengCode"), {
                        width: 100,
                        height: 100,
                        text: data.data
                    });
                    qrcode.makeCode(data.data);
                    layer.alert("整机注册成功,handleId：" + $("#zhengProductId").val());
                } else {
                    layer.alert("整机注册失败，请更新handleID,重新尝试");
                }
            }
        });
        return false;
    });


});