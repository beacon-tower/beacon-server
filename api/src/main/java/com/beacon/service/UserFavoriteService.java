package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.commons.specification.SimpleSpecificationBuilder;
import com.beacon.commons.specification.SpecificationOperator;
import com.beacon.commons.utils.CollectionUtils;
import com.beacon.dao.UserFavoriteDao;
import com.beacon.entity.UserFavorite;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * 用户收藏
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@Service
public class UserFavoriteService extends BaseService<UserFavorite, Integer> {

    @Inject
    private UserFavoriteDao userFavoriteDao;

    @Override
    public BaseDao<UserFavorite, Integer> getBaseDao() {
        return this.userFavoriteDao;
    }

    /**
     * 用户是否收藏过
     *
     * @param userId 用户id
     * @return 是否收藏过
     */
    public boolean hasFavorite(Integer userId, Integer postsId) {
        SimpleSpecificationBuilder<UserFavorite> spec = new SimpleSpecificationBuilder<>();
        spec.add("userId", SpecificationOperator.Operator.eq, userId);
        spec.add("postsId", SpecificationOperator.Operator.eq, postsId);
        List<UserFavorite> userFavoriteList = super.findAll(spec.generateSpecification());
        return !CollectionUtils.isEmpty(userFavoriteList);
    }
}
