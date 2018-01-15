package com.beacon.commons.oss.impl;

import com.beacon.commons.oss.AbstractCloudStorage;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.beacon.commons.oss.OssConfig.*;

/**
 * 七牛云存储
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
public class QiniuCloudStorageService extends AbstractCloudStorage {

    private UploadManager uploadManager;
    private String token;

    public QiniuCloudStorageService() {
        uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
        token = Auth.create(QINIU_ACCESSKEY, QINIU_SECRETKEY).uploadToken(QINIU_BUCKETNAME);
    }

    @Override
    public String upload(byte[] data, String path) {
        try {
            Response res = uploadManager.put(data, path, token);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败，请核对七牛配置信息", e);
        }

        return QINIU_DOMAIN + SLASH + path;
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
    public String upload(MultipartFile file) {
        return null;
    }

    @Override
    public String upload(byte[] data) {
        return upload(data, getPath(QINIU_PREFIX));
    }

    @Override
    public String upload(InputStream inputStream) {
        return upload(inputStream, getPath(QINIU_PREFIX));
    }
}
