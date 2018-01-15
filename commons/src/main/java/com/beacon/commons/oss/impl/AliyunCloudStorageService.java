package com.beacon.commons.oss.impl;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.beacon.commons.oss.AbstractCloudStorage;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

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
    public String upload(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            Random random = new Random();
            String fileName = random.nextInt(10000) + System.currentTimeMillis() + substring;
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            client.putObject(ALIYUN_BUCKETNAME, getPath(ALIYUN_PREFIX), inputStream, objectMetadata);
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败，请检查配置信息", e);
        }

        return ALIYUN_DOMAIN + SLASH + getPath(ALIYUN_PREFIX);
    }

    @Override
    public String upload(byte[] data) {
        return upload(data, getPath(ALIYUN_PREFIX));
    }

    @Override
    public String upload(InputStream inputStream) {
        return upload(inputStream, getPath(ALIYUN_PREFIX));
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param fileExt 文件后缀
     * @return String
     */
    private static String getContentType(String fileExt) {
        if (".bmp".equalsIgnoreCase(fileExt)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExt)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExt) ||
                ".jpg".equalsIgnoreCase(fileExt) ||
                ".png".equalsIgnoreCase(fileExt)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExt)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExt)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExt)) {
            return "application/vnd.visio";
        }
        if (".pptx".equalsIgnoreCase(fileExt) ||
                ".ppt".equalsIgnoreCase(fileExt)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".docx".equalsIgnoreCase(fileExt) ||
                ".doc".equalsIgnoreCase(fileExt)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExt)) {
            return "text/xml";
        }
        return "image/jpeg";
    }
}
