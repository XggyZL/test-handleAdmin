layui.use(['element', 'layer', 'form', 'laydate', 'form', 'table'], function () {
    var element = layui.element,
        layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate;

    $(document).ready(function () {
        $("#xiehuo-list_dummy").attr('placeholder', '请选择卸货口');
    });

    form.verify({
        supplierCode: function (value) {
            if (!(value.length == 4 || value.length == 6)) {
                return '供应商代码四位或者六位';
            }
        }
        , tel: [/^1[34578]\d{9}$/, '电话号码格式不正确']
        // , reserveArrivalTime: function (value) {
        //     var myDate = new Date();//获取当前年
        //     var year = myDate.getFullYear();//获取当前月
        //     var month = myDate.getMonth() + 1;//获取当前日
        //     var date = myDate.getDate();
        //     if (month >= 1 && month <= 9) {
        //         month = "0" + month;
        //     }
        //     if (date >= 0 && date <= 9) {
        //         date = "0" + date;
        //     }
        //     var now = year + '-' + month + "-" + date
        //     value = value.substring(0, 10);
        //     var days = Math.abs(parseInt((value - now) / 1000 / 3600 / 24))
        //     if (days > 3) {
        //         return '预约时间超过三天，请修改';
        //     }
        // }
    });

    $("#reserveArrivalTime").mobiscroll().datetime({
        preset: 'datetime', //日期
        theme: 'android-holo-light', //皮肤样式
        display: 'bottom', //显示方式
        mode: 'scroller', //日期选择模式
        dateFormat: 'yy-mm-dd', // 日期格式
        setText: '确定', //确认按钮名称
        cancelText: '取消',//取消按钮名籍我
        dateOrder: 'yymmdd', //面板中日期排列格式
        dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字
        endYear: 2019,//结束年份
        startYear: 2019,
        lang: "zh",
        minDate: new Date()
    });

    $("#company-list").mobiscroll().treelist({
        theme: "android-holo-light",
        lang: "zh",
        display: 'bottom',
        inputClass: 'tmp',
        headerText: '选择配送工厂',
        onSelect: function (valueText) {
            var m = $(this).find("li").eq(valueText).html();
            $("#company-list_dummy").val(m);
        }
    });


    $("#xiehuo-list").mobiscroll().treelist({
        theme: "android-holo-light",
        lang: "zh",
        display: 'bottom',
        inputClass: 'tmp',
        headerText: '选择卸货口',
        onSelect: function (valueText) {
            var m = $(this).find("li").eq(valueText).html();
            $("#xiehuo-list_dummy").val(m);
        }
    });

    form.on("submit(reserveFilter)", function (data) {
        if ($("#company-list_dummy").val() == 0) {
            layer.alert("请选择对应的工厂");
            return;
        }
        if ($("#xiehuo-list_dummy").val() == 0) {
            layer.alert("请选择对应的卸货口");
            return;
        }
        $.ajax({
            type: 'post',
            url: 'saveStoreReserveInfo',
            data: JSON.stringify({
                "supplierCode": $("#supplierCode").val(),
                "dischargePort": $("#xiehuo-list_dummy").val(),
                "driverTel": $("#tel").val(),
                "reserveArrivalTime": $("#reserveArrivalTime").val(),
                "companyName": $("#companyName").val(),
                "carNum": $("#carNum").val()
            }),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                if (data.code == '00000') {
                    window.location.href = "stoReserveSucess?stoReserveId=" + data.data;
                } else {
                    window.location.href = "stoReserveFail?stoReserveId=" + data.data;
                }
            }
        });
        return false;
    });

    // $("#reserveSubmit").on('click', function () {
    //     $.ajax({
    //         type: 'post',
    //         url: 'saveStoreReserveInfo',
    //         data: JSON.stringify({
    //             "supplierCode": $("#supplierCode").val(),
    //             "dischargePort": $("#xiehuo-list_dummy").val(),
    //             "driverTel": $("#tel").val(),
    //             "reserveArrivalTime": $("#reserveArrivalTime").val()
    //         }),
    //         dataType: 'json',
    //         contentType: 'application/json',
    //         success: function (data) {
    //             if (data.code == '00000') {
    //                 window.open("stoReserveSucess?stoReserveId=" + data.data);
    //             } else {
    //                 window.open("stoReserveFail?stoReserveId=" + data.data);
    //             }
    //         }
    //     });
    // });

    $("#xiehuo-search").on('click', function () {
        $(".weui-gallery").show();
    });
    $("#place-search").on('click', function () {
        $(".weui-driverImg").show();
    });


    $("#reserveArrivalTime").on('change', function () {
        $.ajax({
            type: 'post',
            url: 'getSortNum',
            data: {
                "dischargePort": $("#xiehuo-list_dummy").val(),
                "reserveArrivalTime": $("#reserveArrivalTime").val()
            },
            dataType: 'json',
            // contentType: 'application/json',
            success: function (data) {
                if (data.code == '00000') {
                    if (data.data > 5) {
                        $("#reserveArrivalTip").html("您当前排队数为：" + data.data + "排队人数较多，建议更改预约时间");
                    } else {
                        $("#reserveArrivalTip").html("您当前排队数为：" + data.data);
                    }
                }
            }
        });
    })

    $("#squreBack").on('click', function () {
        window.location.href ="home.html";
    })


});