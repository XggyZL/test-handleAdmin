package com.queue.demo.controller;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.KeyStore;

/**
 * 
 * 删除
 * 
 */
public class DelHandles {

	// String dir = "D:/handle/7000keys/";
	String dir = "D:/handle/7000keys/";
	private String sslKeyStorePath = dir + "local.keystore"; // 密钥库路径
	private String sslKeyStorePassword = "changeit"; // 密钥库密码
	// 客户端信任的证书
	private String sslTrustStore = dir + "localTrust.keystore"; // 信任库路径
	private String sslTrustStorePassword = "changeit"; // 信任库密码
	// 客户端信任的证书
	String httpUrl = "https://192.168.6.231:8443//handleSystem/handleSearch/delData.action";
	String portStr = "8443";
	@Test
	public void post() throws ClientProtocolException, IOException {
		SSLContext sslContext = null;
		try {
			KeyStore kstore = KeyStore.getInstance("jks");
			kstore.load(new FileInputStream(sslKeyStorePath),
					sslKeyStorePassword.toCharArray());
			KeyManagerFactory keyFactory = KeyManagerFactory
					.getInstance("sunx509");
			keyFactory.init(kstore, sslKeyStorePassword.toCharArray());
			KeyStore tstore = KeyStore.getInstance("jks");
			tstore.load(new FileInputStream(sslTrustStore),
					sslTrustStorePassword.toCharArray());
			TrustManager[] tm;
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance("sunx509");
			tmf.init(tstore);
			tm = tmf.getTrustManagers();
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(keyFactory.getKeyManagers(), tm, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int port = Integer.parseInt(portStr);
		HttpClient httpClient = new DefaultHttpClient();
		SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext);
		Scheme sch = new Scheme("https", port, socketFactory);
		httpClient.getConnectionManager().getSchemeRegistry().register(sch);
		HttpPost httpPost = new HttpPost(httpUrl);
		MultipartEntity reqEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE, null,
				Charset.forName("UTF-8"));
 		String adminIdAndIndex = "0.NA/86.5000.10|300";
		String checkBox = "86.5000.10/c1";
		String identityNum = "chanpinpici";

		reqEntity.addPart("adminIdAndIndex", new StringBody(adminIdAndIndex,
				Charset.forName("UTF-8")));
		reqEntity.addPart("checkBox",
				new StringBody(checkBox, Charset.forName("UTF-8")));
		reqEntity.addPart("identityNum",
				new StringBody(identityNum, Charset.forName("UTF-8")));
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
		} else {
			System.err.println("File upload error!"); 
		}

	}

}
