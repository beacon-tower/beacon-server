package com.beacon.commons.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Http请求操作帮助类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/5/26
 */
public class HttpUtils {

    /**
     * 发送post请求
     *
     * @param url  目标地址
     * @param data 传输数据
     * @return 响应结果
     */
    public static String doPost(String url, String data) {
        return doPost(url, data, false);
    }

    /**
     * 发送post请求
     *
     * @param url  目标地址
     * @param data 传输数据
     * @return 响应结果
     */
    public static String doPost(String url, String data, boolean isByte) {
        return doPost(url, data, isByte, null);
    }

    /**
     * 发送post请求
     *
     * @param url  目标地址
     * @param data 传输数据
     * @return 响应结果
     */
    public static String doPost(String url, String data, SSLConnectionSocketFactory socketFactory) {
        return doPost(url, data, false, socketFactory);
    }

    /**
     * 发送post请求
     *
     * @param url  目标地址
     * @param data 传输数据
     * @return 响应结果
     */
    public static String doPost(String url, String data, boolean isByte, SSLConnectionSocketFactory socketFactory) {
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(data, "UTF-8"));

        return send(httpPost, isByte, socketFactory);
    }

    /**
     * 发送get请求
     *
     * @param url    发送请求的URL
     * @param params 请求参数
     * @return 响应结果
     */
    public static String doGet(String url, String params) {
        return doGet(url, params, false);
    }

    /**
     * 发送get请求
     *
     * @param url    发送请求的URL
     * @param params 请求参数
     * @return 响应结果
     */
    public static String doGet(String url, String params, boolean isByte) {
        return send(new HttpGet(RequestUtils.appendParam(url, params)), isByte, null);
    }

    private static String send(HttpRequestBase httpRequest, boolean isByte, SSLConnectionSocketFactory socketFactory) {
        //根据默认超时限制初始化requestConfig
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(30000).setConnectTimeout(30000).build();
        httpRequest.setConfig(requestConfig);

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if (socketFactory != null) {
            httpClientBuilder.setSSLSocketFactory(socketFactory);
        }

        try {
            CloseableHttpClient httpClient = httpClientBuilder.build();
            try {
                HttpEntity entity = httpClient.execute(httpRequest).getEntity();
                if (isByte) {
                    return EncryptUtils.base64Encode(EntityUtils.toByteArray(entity));
                }
                return EntityUtils.toString(entity, "UTF-8");
            } finally {
                httpRequest.abort();
                httpRequest.releaseConnection();
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void download(String url, String localPath) {
        try {
            // 打开连接
            URLConnection connection = new URL(url).openConnection();

            connection.setConnectTimeout(30 * 1000);
            connection.setReadTimeout(30 * 1000);

            // 输入流
            InputStream in = connection.getInputStream();

            // 输出的文件流
            OutputStream out = new FileOutputStream(localPath);

            try {
                // 1K的数据缓冲
                byte[] bs = new byte[1024];

                // 开始读取
                int len;
                while ((len = in.read(bs)) != -1) {
                    out.write(bs, 0, len);
                }
            } finally {
                // 完毕，关闭所有链接
                out.close();
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
