package com.queue.demo.util;


import cn.cdi.handle.util.RSAEncrypt;
import cn.cdi.handle.util.RSASignature;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

@Configuration
public class Util {

    public static String ip;
    public static int port;
    public static String lhsPrefix;
    public static String LHSDIR;

    public static String sslKeyStorePath; // 密钥库路径
    public static String sslKeyStorePassword; // 密钥库密码
    public static String sslTrustStore; // 信任库路径
    public static String sslTrustStorePassword; // 信任库密码

    //应用
    public static String appHandleId;
    /**
     * 验证密码。开发商上传应用的时候设置，告知应用
     */
    public static String password;

    public static String cerPath;

    //应用证书
    public static String appKey;
    public static String appKeyPwd;
    public static String appHandleIdEncryptBase64;
    public static String appHandleIdSignBase64;


    @Value("${ip}")
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Value("${port}")
    public void setPort(int port) {
        this.port = port;
    }

    @Value("${lhsPrefix}")
    public void setLhsPrefix(String lhsPrefix) {
        this.lhsPrefix = lhsPrefix;
    }

    @Value("${LHSDIR}")
    public void setLHSDIR(String LHSDIR) {
        this.LHSDIR = LHSDIR;
    }

    @Value("${sslKeyStorePath}")
    public void setSslKeyStorePath(String sslKeyStorePath) {
        this.sslKeyStorePath = sslKeyStorePath;
    }

    @Value("${sslKeyStorePassword}")
    public void setSslKeyStorePassword(String sslKeyStorePassword) {
        this.sslKeyStorePassword = sslKeyStorePassword;
    }

    @Value("${sslTrustStore}")
    public void setSslTrustStore(String sslTrustStore) {
        this.sslTrustStore = sslTrustStore;
    }

    @Value("${sslTrustStorePassword}")
    public void setSslTrustStorePassword(String sslTrustStorePassword) {
        this.sslTrustStorePassword = sslTrustStorePassword;
    }

    @Value("${appHandleId}")
    public void setAppHandleId(String appHandleId) {
        this.appHandleId = appHandleId;
    }

    @Value("${password}")
    public void setPassword(String password) {
        this.password = password;
    }

    @Value("${cerPath}")
    public void setCerPath(String cerPath) {
        this.cerPath = cerPath;
    }

    @Value("${appKey}")
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Value("${appKeyPwd}")
    public void setAppKeyPwd(String appKeyPwd) {
        this.appKeyPwd = appKeyPwd;
    }

    @Value("${appHandleIdEncryptBase64}")
    public void setAppHandleIdEncryptBase64(String appHandleIdEncryptBase64) {
        this.appHandleIdEncryptBase64 = appHandleIdEncryptBase64;
    }

    @Value("${appHandleIdSignBase64}")
    public void setAppHandleIdSignBase64(String appHandleIdSignBase64) {
        this.appHandleIdSignBase64 = appHandleIdSignBase64;
    }


    void init() {
        try {
            // 采集公钥加密后的前缀
            byte[] said = RSAEncrypt.encrypt(Util.cerPath, (Util.appHandleId + "87654321").getBytes("UTF-8"));
            // 采集公钥加密，私钥签名后的前缀
            byte[] snaid = RSASignature.sign(Util.appKey, Util.appKeyPwd, said);
            appHandleIdEncryptBase64 = cn.cdi.handle.util.Base64.encode(said);
            appHandleIdSignBase64 = cn.cdi.handle.util.Base64.encode(snaid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpClient geHttpClient() {
        SSLContext sslContext = null;
        try {
            KeyStore kstore = KeyStore.getInstance("jks");
            kstore.load(new FileInputStream(Util.sslKeyStorePath), Util.sslKeyStorePassword.toCharArray());
            KeyManagerFactory keyFactory = KeyManagerFactory.getInstance("sunx509");
            keyFactory.init(kstore, Util.sslKeyStorePassword.toCharArray());
            KeyStore tstore = KeyStore.getInstance("jks");
            tstore.load(new FileInputStream(Util.sslTrustStore), Util.sslTrustStorePassword.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
            tmf.init(tstore);
            TrustManager[] tm = tmf.getTrustManagers();
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(keyFactory.getKeyManagers(), tm, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpClient httpClient = new DefaultHttpClient();
        SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext);
        socketFactory.setHostnameVerifier(new AllowAllHostnameVerifier());
        Scheme sch = new Scheme("https", Util.port, socketFactory);
        httpClient.getConnectionManager().getSchemeRegistry().register(sch);
        return httpClient;
    }

}
