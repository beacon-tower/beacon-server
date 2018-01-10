package com.beacon.commons.oss.impl;


import com.beacon.commons.oss.AbstractCloudStorage;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import static com.beacon.commons.oss.OssConfig.*;

/**
 * 腾讯云存储
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
public class QcloudCloudStorageService extends AbstractCloudStorage {

    private COSClient client;

    public QcloudCloudStorageService() {
        Credentials credentials = new Credentials(QCLOUD_APPID, QCLOUD_SECRETID, QCLOUD_SECRETKEY);
        //初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        //设置bucket所在的区域，华南：gz 华北：tj 华东：sh
        clientConfig.setRegion(QCLOUD_REGION);
        client = new COSClient(clientConfig, credentials);
    }

    @Override
    public String upload(byte[] data, String path) {
        //腾讯云必需要以"/"开头
        if (!path.startsWith(SLASH)) {
            path = SLASH + path;
        }

        //上传到腾讯云
        UploadFileRequest request = new UploadFileRequest(QCLOUD_BUCKETNAME, path, data);
        String response = client.uploadFile(request);

        JSONObject jsonObject = JSONObject.fromObject(response);
        if (jsonObject.getInt("code") != 0) {
            throw new RuntimeException("文件上传失败，" + jsonObject.getString("message"));
        }

        return QCLOUD_DOMAIN + path;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public String upload(byte[] data) {
        return upload(data, getPath(QCLOUD_PREFIX));
    }

    @Override
    public String upload(InputStream inputStream) {
        return upload(inputStream, getPath(QCLOUD_PREFIX));
    }
}
