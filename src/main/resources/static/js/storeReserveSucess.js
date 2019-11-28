layui.use(['element', 'layer', 'form', 'laydate', 'form', 'table'], function () {
    var element = layui.element,
        layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate;

    var stoReserveId = $(".stoReserveIdSpan").text();

    $(".cancelReserveBtn").on('click', function () {
        $.ajax({
            type: 'post',
            url: 'cancelReserveOrder?stoReserveId=' + stoReserveId,
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                if (data.code == '00000') {
                    window.location.href = "cancelSucess?stoReserveId=" + stoReserveId;
                } else {
                    window.location.href = "cancelFail?stoReserveId=" + stoReserveId;
                }
            }
        });
    })
})