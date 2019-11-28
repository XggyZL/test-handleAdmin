package com.queue.demo.controller;

import cn.hutool.core.util.XmlUtil;
import com.queue.demo.domain.HandleParam;
import com.queue.demo.domain.Metadata;
import com.queue.demo.domain.Record;
import com.queue.demo.domain.Relation;
import com.queue.demo.util.RecordXmlHandler;
import com.queue.demo.util.Util;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析
 */
@Component
public class ClientParse {


    public List<HandleParam> post(String handleId) throws Exception {
        List<HandleParam> handleParamList = new ArrayList<>();
        String httpUrl = "https://" + Util.ip + ":" + Util.port + "/handleSystem/service/HandleParseService/handleParse?wsdl";
        HttpClient httpClient = Util.geHttpClient();
        HttpPost httpPost = new HttpPost(httpUrl);

//        String handleId = "86.1000.1002/B0002";
        //	String handleId1 = "86.1000.10000.470/100";
        long a = System.currentTimeMillis();

        String soapRequestData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<ns1:handleParse xmlns:ns1=\"http://service.handleparse.cdi.cn/\">"
                + " <handleId>" + handleId + "</handleId>"
                + "  <isAuthentication>0</isAuthentication>"
                + "  <relationFlag>1</relationFlag>"
                + "  <deepRelationFlag>1</deepRelationFlag>"
                + "  <isReverse>0</isReverse>"
                + "  <userGroupIds>0</userGroupIds>"
                + "  <onlyHandleIds>0</onlyHandleIds>"
                + "  <appHandleId>" + Util.appHandleId + "</appHandleId>"
                + "  <appHandleIdEncryptBase64>" + Util.appHandleIdEncryptBase64 + "</appHandleIdEncryptBase64>"
                + "  <appHandleIdSignBase64>" + Util.appHandleIdSignBase64 + "</appHandleIdSignBase64>"
                + "  <password>" + Util.password + "</password>"
                + "</ns1:handleParse>" + " </soap:Body>" + "</soap:Envelope>";

        @SuppressWarnings("deprecation")
        HttpEntity reqEntity = new StringEntity(soapRequestData, HTTP.UTF_8);
        httpPost.setHeader("Content-Type",
                "application/soap+xml; charset=utf-8");
        httpPost.setEntity(reqEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);

        if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {

            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    httpResponse.getEntity().getContent()));
            StringBuffer stb = new StringBuffer();
            String line = null;
            while ((line = buffer.readLine()) != null) {
                stb.append(line);
            }
            buffer.close();
            String xml = stb.toString().replace("&gt;", ">").replace("&lt;", "<");
            int begin = xml.indexOf("<return>");
            int end = xml.indexOf("</return>");
            xml = xml.substring(begin + "<return>".length(), end);

            System.out.println(xml);
            System.out.println(System.currentTimeMillis() - a);
            RecordXmlHandler paseXmlHandler = new RecordXmlHandler();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            parser.parse(is, paseXmlHandler);
            List<Record> records = paseXmlHandler.getRecords();
            for (Record record : records) {
                HandleParam handleParamChild = new HandleParam();

                if ("0".equals(record.getState())) {
                    System.out.println("identifier:" + record.getIdentifier());
                    System.out.println("ErrorMessage:" + record.getErrorMessage());
                    continue;
                }
                System.out.println("identifier:" + record.getIdentifier());
                System.out.println("State:" + record.getState());
                System.out.println("ErrorMessage:" + record.getErrorMessage());
                System.out.println("MetedataStandard:" + record.getMetedataStandard());
                System.out.println("Url:" + record.getUrl());
                List<Metadata> metadatas = record.getMetadatas();
                List<Metadata> metadataList2 = new ArrayList<>();
                if (metadatas != null) {
                    for (Metadata metadata : metadatas) {
                        System.out.println(metadata.getCode() + ":" + metadata.getTitle() + ":" + metadata.getValue());
                        metadataList2.add(metadata);
                    }
                }
                handleParamChild.setHandleId(record.getIdentifier());
                handleParamChild.setUrl(record.getUrl());
                handleParamChild.setMetadataList(metadataList2);
                handleParamList.add(handleParamChild);
                List<Relation> relations = record.getRelations();
                if (relations != null) {
                    for (Relation relation : relations) {
                        System.out.println("********************************************************");
                        HandleParam handleParamRelation = new HandleParam();
                        handleParamRelation.setRelationName(relation.getParentName());
                        Record relationRecord = relation.getRelationRecord();
                        List<Metadata> metadataList = new ArrayList<>();
                        if (relationRecord.getMetadatas() != null) {
                            for (Metadata metadata : relationRecord.getMetadatas()) {
                                metadataList.add(metadata);
                                System.out.println(metadata.getCode() + ":" + metadata.getTitle() + ":" + metadata.getValue());
                            }
                        }
                        handleParamRelation.setMetadataList(metadataList);
                        handleParamRelation.setHandleId(relationRecord.getIdentifier());
                        handleParamRelation.setUrl(relationRecord.getUrl());
                        handleParamList.add(handleParamRelation);
                        System.out.println("identifier:" + relationRecord.getIdentifier());
                        System.out.println("State:" + relationRecord.getState());
                        System.out.println("ErrorMessage:" + relationRecord.getErrorMessage());
                        System.out.println("MetedataStandard:" + relationRecord.getMetedataStandard());
                        System.out.println("Url:" + relationRecord.getUrl());
                        System.out.println("RelationId:" + relation.getRelationId());
                        System.out.println("ParentName:" + relation.getParentName());
                        System.out.println("RelationMasterName:" + relation.getRelationMasterName());
                        System.out.println("RelationName:" + relation.getRelationName());
                        System.out.println("RelationOptionId:" + relation.getRelationOptionId());
                        System.out.println("RelationOptionName:" + relation.getRelationOptionName());
                        System.out.println("RelationOrder:" + relation.getRelationOrder());
                        System.out.println("RelationSlaveName:" + relation.getRelationSlaveName());
//                        handleParamList.add(handleParamChild);
                    }
                }
                System.out.println("...........................................................");

            }

        } else {
            System.err.println(httpResponse);
        }
        return handleParamList;
    }

}
