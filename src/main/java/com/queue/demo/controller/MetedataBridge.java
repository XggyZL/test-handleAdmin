package com.queue.demo.controller;

import com.queue.demo.util.Util;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * 批量获得handleid
 * 
 */
public class MetedataBridge {
 
	// 客户端信任的证书
	String httpUrl = "https://"+Util.ip+":"+Util.port+"/handleSystem/metedataBridge/metedataBridgejk.action";
	  
	@Test
	public void post() throws ClientProtocolException, IOException {

		HttpClient httpClient = Util.geHttpClient(); 
		HttpPost httpPost = new HttpPost(httpUrl);
		MultipartEntity reqEntity = new MultipartEntity();  
		String beginTime = "2014-02-01";
		String endTime = "2018-08-28";
		String metedataStandard = "470";
		String offset = "20";
		String length = "100";
		reqEntity.addPart("beginTime", new StringBody(beginTime));
		reqEntity.addPart("endTime", new StringBody(endTime));
		reqEntity.addPart("metedataStandard", new StringBody(metedataStandard));
		reqEntity.addPart("offset", new StringBody(offset));
		reqEntity.addPart("length", new StringBody(length));
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
			System.out.println(stb.toString());
			String result = stb.toString();
 			try {
				Document doc = DocumentHelper.parseText(result);
				Element root = doc.getRootElement();
				int type = Integer.parseInt(root.element("messageId")
						.getTextTrim());
				String message = root.element("message").getTextTrim();
				if (type == 1) {// 1成功
					System.out.println(message);
				} else if (type == 0) {// 0失败
					System.out.println(message);
				} else {
					System.out.println("获取数据失败");
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("File upload error!"); // 閫氫俊閿欒
		}
	}

}
