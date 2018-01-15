package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.commons.oss.OssFactory;
import com.beacon.commons.utils.StringUtils;
import com.beacon.dao.ImageDao;
import com.beacon.entity.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

/**
 * 文件处理，上传到云储存
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
@Service
public class OssService extends BaseService<Image, Integer> {

    @Inject
    private ImageDao imageDao;

    @Value("${oss.config}")
    private String key;

    @Override
    public BaseDao<Image, Integer> getBaseDao() {
        return this.imageDao;
    }

    public Image upload(MultipartFile file) {
        String url = null;
        url = OssFactory.build(key).upload(file);
        if (StringUtils.isNotEmpty(url)) {
            Image image = new Image();
            image.setUrl(url);
            imageDao.save(image);
            return image;
        }
        return null;
    }
}
