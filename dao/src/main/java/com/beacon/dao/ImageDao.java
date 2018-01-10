package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.Image;
import org.springframework.stereotype.Repository;

/**
 * 图片处理
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
@Repository
public interface ImageDao extends BaseDao<Image, Integer> {
}
