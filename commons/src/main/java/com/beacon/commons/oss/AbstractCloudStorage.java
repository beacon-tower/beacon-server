package com.beacon.commons.oss;

import com.beacon.commons.utils.DateUtils;
import com.beacon.commons.utils.StringUtils;

import java.io.InputStream;
import java.util.Date;

/**
 * 云存储(支持七牛、阿里云、腾讯云)
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
public abstract class AbstractCloudStorage {

    public static final String SLASH = "/";

    /**
     * 文件路径
     *
     * @param prefix 前缀
     * @return 返回上传路径
     */
    public String getPath(String prefix) {
        //文件路径
        String path = DateUtils.format(new Date(), "yyyyMMdd") + SLASH + StringUtils.getUUID();
        if (StringUtils.isNotEmpty(prefix)) {
            path = prefix + SLASH + path;
        }
        return path;
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @return 返回http地址
     */
    public abstract String upload(byte[] data);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @return 返回http地址
     */
    public abstract String upload(InputStream inputStream);

}
