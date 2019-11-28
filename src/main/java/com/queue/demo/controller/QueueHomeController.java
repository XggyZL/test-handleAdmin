package com.queue.demo.controller;

import com.queue.demo.domain.HandleParam;
import com.queue.demo.service.QueueHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vip.justlive.common.base.domain.Response;

import java.util.List;


@Controller
public class QueueHomeController {
    @Autowired
    private QueueHomeService queueHomeService;


    @RequestMapping("/home")
    public String init() {
        return "home";
    }

    @RequestMapping("lingZhuceSub")
    @ResponseBody
    public Response lingZhuceSub(@RequestBody HandleParam handleParam) throws Exception {
        //生成xml文件，并压缩为zip格式，调注册接口。
        Response response = queueHomeService.lingZhuceSub(handleParam);
        if (response.isSuccess()) {
//            File xmlLingFile = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\" + "lingCode.jpg");
//            QrCodeUtil.generate("127.0.0:80/handleSystemCode?handleId=" + handleId, 50, 50, xmlLingFile);
//            QrCodeUtil.generatePng("127.0.0:80/handleSystemCode?handleId=" + handleId, 50, 50);
            String dirUrl = "http://h.xcmgzhilian.com/handleSystemCode?handleId=" + handleParam.getHandleId();
//           String dirUrl = "http://127.0.0.1/handleSystemCode?handleId=" + handleParam.getHandleId();

            return Response.success(dirUrl);
        } else {
            return Response.error("文件生成异常，请稍后重试");
        }
    }

    @RequestMapping("zhengjiZhuceSub")
    @ResponseBody
    public Response zhengjiZhuceSub(@RequestBody HandleParam handleParam) throws Exception {
        String childHandleId = handleParam.getChildHandleId();
        String str1 = childHandleId.substring(0, childHandleId.indexOf("="));
        childHandleId = childHandleId.substring(str1.length() + 1, childHandleId.length());

        handleParam.setChildHandleId(childHandleId);
        //生成xml文件，并压缩为zip格式，调注册接口。
        Response response = queueHomeService.zhengjiZhuceSub(handleParam);
        if (response.isSuccess()) {
            String dirUrl = "http://h.xcmgzhilian.com/handleSystemCode?handleId=" + handleParam.getHandleId();
//            String dirUrl = "http://127.0.0.1/handleSystemCode?handleId=" + handleParam.getHandleId();
            return Response.success(dirUrl);
        } else {
            return Response.error("文件生成异常，请稍后重试");
        }
    }

    @RequestMapping("handleSystemCode")
    public String handleSystemCode(Model model, String handleId) throws Exception {
        List<HandleParam> handleParamList = queueHomeService.jiexiJob(handleId);
        for (HandleParam handleParam : handleParamList) {
            handleParam.setUrl("http://h.xcmgzhilian.com/handleSystemCode?handleId=" + handleParam.getHandleId());
            if (handleId.equals(handleParam.getHandleId())) {
                handleParam.setBasicInfoTitle("基本信息");
            } else {
                handleParam.setBasicInfoTitle("关联信息");
            }
        }
        model.addAttribute("handleParamList", handleParamList);
        return "handleSystemCode";
    }


}
