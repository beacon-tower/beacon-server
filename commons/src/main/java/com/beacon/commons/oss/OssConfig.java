package com.beacon.commons.oss;

/**
 * 云存储配置文件
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
public class OssConfig {

    public static final String QINIU = "qiniu";
    public static final String ALIYUN = "aliyun";
    public static final String QCLOUD = "qcloud";

    /**
     * ================= 七牛配置 =================
     **/
    //七牛绑定的域名
    public static final String QINIU_DOMAIN = "";
    //七牛路径前缀
    public static final String QINIU_PREFIX = "";
    //七牛ACCESS_KEY
    public static final String QINIU_ACCESSKEY = "";
    //七牛SECRET_KEY
    public static final String QINIU_SECRETKEY = "";
    //七牛存储空间名
    public static final String QINIU_BUCKETNAME = "";

    /**
     * ================= 阿里配置 =================
     **/
    //阿里云绑定的域名
    public static final String ALIYUN_DOMAIN = "http://sofly-pic.oss-cn-beijing.aliyuncs.com";
    //阿里云路径前缀
    public static final String ALIYUN_PREFIX = "beacon";
    //阿里云EndPoint
    public static final String ALIYUN_ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    //阿里云AccessKeyId
    public static final String ALIYUN_ACCESSKEYID = "LTAIxBPusbcrJJN8";
    //阿里云AccessKeySecret
    public static final String ALIYUN_ACCESSKEYSECRET = "gdpde73JVbaJOibmY7uTWFrFrGXLqc";
    //阿里云BucketName
    public static final String ALIYUN_BUCKETNAME = "sofly-pic";

    /**
     * ================= 腾讯配置 =================
     **/
    //腾讯云绑定的域名
    public static final String QCLOUD_DOMAIN = "";
    //腾讯云路径前缀
    public static final String QCLOUD_PREFIX = "";
    //腾讯云AppId
    public static final Integer QCLOUD_APPID = 0;
    //腾讯云SecretId
    public static final String QCLOUD_SECRETID = "";
    //腾讯云SecretKey
    public static final String QCLOUD_SECRETKEY = "";
    //腾讯云BucketName
    public static final String QCLOUD_BUCKETNAME = "";
    //腾讯云COS所属地区
    public static final String QCLOUD_REGION = "";
}
