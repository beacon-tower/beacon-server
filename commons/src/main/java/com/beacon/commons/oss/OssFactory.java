package com.beacon.commons.oss;

import com.beacon.commons.oss.impl.AliyunCloudStorageService;
import com.beacon.commons.oss.impl.QcloudCloudStorageService;
import com.beacon.commons.oss.impl.QiniuCloudStorageService;

/**
 * 文件上传，存储到云上
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
public final class OssFactory {

    public static AbstractCloudStorage build(String key){
        switch (key) {
            case OssConfig.QINIU:
                return new QiniuCloudStorageService();
            case OssConfig.ALIYUN:
                return new AliyunCloudStorageService();
            case OssConfig.QCLOUD:
                return new QcloudCloudStorageService();
            default:
                return new AliyunCloudStorageService();
        }
    }
}
