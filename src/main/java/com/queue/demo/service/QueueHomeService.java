package com.queue.demo.service;

import com.queue.demo.controller.ClientParse;
import com.queue.demo.controller.HttpClientReg;
import com.queue.demo.domain.HandleParam;
import com.queue.demo.util.PaseXml;
import org.springframework.stereotype.Service;
import vip.justlive.common.base.domain.Response;
import vip.justlive.oxygen.core.template.SimpleTemplateEngine;
import vip.justlive.oxygen.core.template.Templates;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class QueueHomeService {
    private static String xmlLingFilePath = "C:\\xmlAndZip\\xml\\saveLingXml.xml";
    private static String xmlZhengFilePath = "C:\\xmlAndZip\\xml\\saveZhengXml.xml";
    private static String zipLingFilePath = "C:\\xmlAndZip\\zip\\zipLingFile.zip";
    private static String zipZhengFilePath = "C:\\xmlAndZip\\zip\\zipZhengFile.zip";

    private static String lingFileName = "saveLingXml.xml";
    private static String zhengFileName = "saveZhengXml.xml";



    //调用零部件接口
    public Response lingZhuceSub(HandleParam handleParam) throws Exception {
        //将用户页面注册数据，写入xml;
        SimpleTemplateEngine engine = new SimpleTemplateEngine();
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("identifier", handleParam.getHandleId());
        attrs.put("WYSBM", handleParam.getProductId());
        attrs.put("CPMC", handleParam.getProductName());
        attrs.put("GGXH", handleParam.getProduceModel());
        attrs.put("CCRQ", handleParam.getChuchangDate());
        attrs.put("SCCJ", handleParam.getProductCompany());

        String lingTempData = Templates.template("classpath:lingZhuce.xml");
        String xmlResult = engine.render(lingTempData, attrs);
        System.out.println(xmlResult);

        //写入内容前，先把文件删除 再创建，确保文件内容已被清空
        File xmlLingFile = new File(xmlLingFilePath);
        File zipLingFile = new File(zipLingFilePath);

        if (xmlLingFile.exists() && xmlLingFile.isFile()) {
            xmlLingFile.delete();
            xmlLingFile.createNewFile();

            zipLingFile.delete();
        }

        //将内容写入xml文件，并保存
        FileWriter fwriter = new FileWriter(xmlLingFilePath, true);
        fwriter.write(xmlResult);
        fwriter.flush();
        fwriter.close();
        zip(zipLingFilePath, xmlLingFilePath, lingFileName);

        //调注册接口
        Response response = getCallClientReq(zipLingFilePath);
        return response;
    }





    public Response zhengjiZhuceSub(HandleParam handleParam) throws Exception {
        //将用户页面注册数据，写入xml;
        SimpleTemplateEngine engine = new SimpleTemplateEngine();
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("identifier", handleParam.getHandleId());
        attrs.put("WYSBM", handleParam.getProductId());
        attrs.put("CPMC", handleParam.getProductName());
        attrs.put("GGXH", handleParam.getProduceModel());
        attrs.put("CCRQ", handleParam.getChuchangDate());
        attrs.put("SCCJ", handleParam.getProductCompany());
        attrs.put("handleId", handleParam.getChildHandleId()); //关联的handleId

        String lingTempData = Templates.template("classpath:zhengjiZhuce.xml");
        String xmlResult = engine.render(lingTempData, attrs);
        System.out.println(xmlResult);

        //写入内容前，先把文件删除 再创建，确保文件内容已被清空
        File xmlZhengFile = new File(xmlZhengFilePath);
        File zipZhengFile = new File(zipZhengFilePath);

        if (xmlZhengFile.exists() && xmlZhengFile.isFile()) {
            xmlZhengFile.delete();
            xmlZhengFile.createNewFile();

            zipZhengFile.delete();
        }

        //将内容写入xml文件，并保存
        FileWriter fwriter = new FileWriter(xmlZhengFilePath, true);
        fwriter.write(xmlResult);
        fwriter.flush();
        fwriter.close();
        zip(zipZhengFilePath, xmlZhengFilePath, zhengFileName);

        //调注册接口
        Response response = getCallClientReq(zipZhengFilePath);
        return response;
    }


    public List<HandleParam> jiexiJob(String handleId) throws Exception {
        //调解析接口
        return new ClientParse().post(handleId);
    }

    public void zip(String zipFilePathName, String filePathName, String fileName) {
        try {
            //1,创建文件输出时的文件
            ZipOutputStream ops = new ZipOutputStream(new FileOutputStream(zipFilePathName));
            //2,创建输出文件中的对象
            ZipEntry entry = new ZipEntry(fileName);
            //创建一个文档注释
            ops.setComment("Here is how we compressed in Java");
            //将对象放到压缩文档中
            ops.putNextEntry(entry);
            //3,创建文件输入流
            InputStream fis = new FileInputStream(filePathName);
            int len = fis.read();
            while (len != -1) {
                ops.write(len);
                len = fis.read();
            }
            fis.close();
            ops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Response getCallClientReq(String filePath) {
        long a = System.currentTimeMillis();
        try {
            String xml = new HttpClientReg().post(filePath);
            List<String> key = new ArrayList<String>();
            key.add("serialNumber");
            key.add("type");
            key.add("message");
            key.add("errorFileUrl");
            Map<String, String> map = PaseXml.getXmlValue(xml.toString(), key);
            if (map.size() != 0) {
                String type = map.get("type").toString();
                String errorFileUrl = map.get("errorFileUrl");
                if (type.equals("300") && (errorFileUrl == null || "".equals(errorFileUrl))) {
                    System.out.println("注册成功");
                } else {
                    System.out.println(map.toString());
                }
            } else {
                System.out.println(xml);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - a);
        return Response.success("注册成功");
    }
}

