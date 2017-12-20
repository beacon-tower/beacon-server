package com.beacon.commons.utils;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * URL处理工具类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/5/26
 */
public class RequestUtils {

    /**
     * URL编码
     */
    public static String encodeURL(String url) {
        return encodeURL(url, "UTF-8");
    }

    /**
     * URL编码
     */
    public static String encodeURL(String url, String charset) {
        try {
            return URLEncoder.encode(url, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String decodeURL(String url) {
        return decodeURL(url, "UTF-8");
    }

    public static String decodeURL(String url, String charset) {
        try {
            return URLDecoder.decode(url, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 追加参数
     *
     * @param url   url
     * @param key   参数名
     * @param value 参数值
     * @return 追加参数的url
     */
    public static String appendParam(String url, String key, String value) {
        return appendParam(url, key + "=" + value);
    }

    /**
     * 追加参数
     *
     * @param url    url
     * @param params 参数
     * @return 追加参数的url
     */
    public static String appendParam(String url, Map<String, Object> params) {
        return appendParam(url, CollectionUtils.mapToString(params));
    }

    /**
     * 追加参数
     *
     * @param url    url
     * @param params 参数
     * @return 追加参数的url
     */
    public static String appendParam(String url, String params) {
        if (StringUtils.isEmpty(params)) {
            return url;
        }

        if (!url.contains("?")) {
            url += "?";
        }

        if (url.endsWith("?")) {
            url += params;
        } else {
            url += "&" + params;
        }
        return url;
    }

    /**
     * 获取请求信息
     *
     * @param request 请求
     * @return 请求信息
     */
    public static String getInfo(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append("URL = " + request.getRequestURL() + ", METHOD = " + request.getMethod());
        builder.append("PARAM = " + getParam(request) + ", BODY = " + getBody(request));
        return builder.toString();
    }

    /**
     * 获取请求参数（?后面的字符串）
     *
     * @param request 请求
     * @return 请求参数
     */
    public static String getParam(HttpServletRequest request) {
        String param = request.getQueryString();
        if (Base64.isBase64(param)) {
            param = new String(Base64.decodeBase64(param), StandardCharsets.UTF_8);
        }
        return param;
    }

    /**
     * 获取请求体
     *
     * @param request 请求
     * @return 请求体
     */
    public static String getBody(HttpServletRequest request) {
        try {
            String body = StreamUtils.loadText(request.getInputStream(), "UTF-8");
            if (Base64.isBase64(body)) {
                return new String(Base64.decodeBase64(body), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取客户端IP地址，此方法用在proxy环境中
     *
     * @param req
     * @return
     */
    public static String getRemoteIp(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (org.apache.commons.lang.StringUtils.isNotBlank(ip)) {
            String[] ips = org.apache.commons.lang.StringUtils.split(ip, ',');
            if (ips != null) {
                for (String tmpip : ips) {
                    if (org.apache.commons.lang.StringUtils.isBlank(tmpip)) {
                        continue;
                    }
                    tmpip = tmpip.trim();
                    if (isIPAddr(tmpip) && !tmpip.startsWith("10.") && !tmpip.startsWith("192.168.") && !"127.0.0.1".equals(tmpip)) {
                        return tmpip.trim();
                    }
                }
            }
        }
        ip = req.getHeader("x-real-ip");
        if (isIPAddr(ip)) {
            return ip;
        }
        ip = req.getRemoteAddr();
        if (ip.indexOf('.') == -1) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * 判断字符串是否是一个IP地址
     *
     * @param addr
     * @return
     */
    public static boolean isIPAddr(String addr) {
        if (org.apache.commons.lang.StringUtils.isEmpty(addr)) {
            return false;
        }
        String[] ips = org.apache.commons.lang.StringUtils.split(addr, '.');
        if (ips.length != 4) {
            return false;
        }
        try {
            int ipa = Integer.parseInt(ips[0]);
            int ipb = Integer.parseInt(ips[1]);
            int ipc = Integer.parseInt(ips[2]);
            int ipd = Integer.parseInt(ips[3]);
            return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0
                    && ipc <= 255 && ipd >= 0 && ipd <= 255;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 获取请求域名
     *
     * @param request
     * @return
     */
    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String requestURI = request.getRequestURI();
        return url.delete(url.length() - requestURI.length(), url.length()).toString();
    }
}
