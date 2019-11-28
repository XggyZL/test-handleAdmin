package com.queue.demo.controller;

import com.queue.demo.util.Util;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 上传数据
 * 
 */
@Component
public class HttpClientReg {
	
	//String prefix = "86.1000.1002";
//	static String filePath="D:\\handle\\zip\\2.zip";
	private static String index = "300";

	public String post(String filePath) throws Exception {
		File file =new File(filePath);
		String httpUrl = "https://"+Util.ip+":"+ Util.port+"/handleSystem/handleRegister/upload.action";
		HttpClient httpClient =  Util.geHttpClient();
		HttpPost httpPost = new HttpPost(httpUrl);
 		MultipartEntity reqEntity = new MultipartEntity(); 
		/** 时间的毫秒值 */
		String operationTime=System.currentTimeMillis()+"";
		reqEntity.addPart("file", new FileBody(file)); 
		reqEntity.addPart("handleId", new StringBody(Util.lhsPrefix));
		reqEntity.addPart("index", new StringBody(index));
	 	reqEntity.addPart("fileFileName", new StringBody(file.getName()));
		reqEntity.addPart("appHandleIdEncryptBase64", new StringBody(Util.appHandleIdEncryptBase64));
		reqEntity.addPart("appHandleIdSignBase64", new StringBody(Util.appHandleIdSignBase64));
		reqEntity.addPart("applicationHandleId", new StringBody(Util.appHandleId));
		reqEntity.addPart("password", new StringBody(Util.password));
		reqEntity.addPart("operationTime", new StringBody(operationTime));
 		httpPost.setEntity(reqEntity);
 		System.out.println("lhsPrefix："+Util.lhsPrefix);
		System.out.println("appHandleIdEncryptBase64："+Util.appHandleIdEncryptBase64);
		System.out.println("appHandleIdSignBase64："+Util.appHandleIdSignBase64);
		System.out.println("appHandleId："+Util.appHandleId);
		System.out.println("password："+Util.password);

		HttpResponse httpResponse = httpClient.execute(httpPost);
		if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			StringBuffer stb = new StringBuffer();
			String line = null;
			while ((line = buffer.readLine()) != null) {
				stb.append(line);
			}
			System.out.println(stb.toString());
			buffer.close();
			return stb.toString();
		} else {
			System.err.println(httpResponse.toString());
 			return httpResponse.toString();
		}
		
	}
 
//	public static void main(String[] args)  {
//
//		long a = System.currentTimeMillis();
//
//		try {
//			String xml = new HttpClientReg().post();
//			List<String> key = new ArrayList<String>();
//			key.add("serialNumber");
//			key.add("type");
//			key.add("message");
//			key.add("errorFileUrl");
//			Map<String, String> map = PaseXml.getXmlValue(xml.toString(), key);
//			if(map.size()!=0){
//				String type = map.get("type").toString();
//				String errorFileUrl = map.get("errorFileUrl");
//				if(type.equals("300")&&(errorFileUrl==null||"".equals(errorFileUrl))){
//					System.out.println("注册成功");
//				}else{
//					System.out.println(map.toString());
//				//	System.out.println(map.get("errorFileUrl"));
//				}
//			}else{
//				System.out.println(xml);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(System.currentTimeMillis()-a);
// 	}

}
