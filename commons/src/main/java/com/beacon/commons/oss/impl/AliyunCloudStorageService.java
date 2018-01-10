package com.beacon.commons.oss.impl;


import com.aliyun.oss.OSSClient;
import com.beacon.commons.oss.AbstractCloudStorage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.beacon.commons.oss.OssConfig.*;

/**
 * 阿里云存储
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
public class AliyunCloudStorageService extends AbstractCloudStorage {

    private OSSClient client;

    public AliyunCloudStorageService() {
        client = new OSSClient(ALIYUN_ENDPOINT, ALIYUN_ACCESSKEYID, ALIYUN_ACCESSKEYSECRET);
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(ALIYUN_BUCKETNAME, path, inputStream);
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败，请检查配置信息", e);
        }

        return ALIYUN_DOMAIN + SLASH + path;
    }

    @Override
    public String upload(byte[] data) {
        return upload(data, getPath(ALIYUN_PREFIX));
    }

    @Override
    public String upload(InputStream inputStream) {
        return upload(inputStream, getPath(ALIYUN_PREFIX));
    }
}
