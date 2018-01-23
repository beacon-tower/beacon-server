package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.commons.specification.SimpleSpecificationBuilder;
import com.beacon.commons.specification.SpecificationOperator;
import com.beacon.commons.utils.CollectionUtils;
import com.beacon.dao.UserLikeDao;
import com.beacon.entity.UserLike;
import com.beacon.utils.ShiroUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * 用户点赞
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@Service
public class UserLikeService extends BaseService<UserLike, Integer> {

    @Inject
    private UserLikeDao userLikeDao;

    @Override
    public BaseDao<UserLike, Integer> getBaseDao() {
        return this.userLikeDao;
    }

    /**
     * 用户是否点赞过
     *
     * @param targetType 目标类型
     * @param targetValue 目标值
     * @return 是否点过
     */
    public boolean hasLike(String targetType, Integer targetValue) {
        SimpleSpecificationBuilder<UserLike> spec = new SimpleSpecificationBuilder<>();
        spec.add("userId", SpecificationOperator.Operator.eq, ShiroUtils.getUserId());
        spec.add("targetType", SpecificationOperator.Operator.eq, targetType);
        spec.add("targetValue", SpecificationOperator.Operator.eq, targetValue);
        List<UserLike> userLikeList = super.findAll(spec.generateSpecification());
        return !CollectionUtils.isEmpty(userLikeList);
    }
}
