package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.dao.UserFollowDao;
import com.beacon.entity.User;
import com.beacon.entity.UserFollow;
import com.beacon.utils.ShiroUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * 用户关注
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@Service
public class UserFollowService extends BaseService<UserFollow, Integer> {

    @Inject
    private UserFollowDao userFollowDao;

    @Inject
    private UserService userService;

    @Override
    public BaseDao<UserFollow, Integer> getBaseDao() {
        return this.userFollowDao;
    }

    /**
     * 关注/取消关注作者
     *
     * @param followUserId 被关注的的作者id
     */
    public void toggleFollowAuthor(Integer followUserId) {
        Integer userId = ShiroUtils.getUserId();

        User followUser = userService.findById(followUserId);
        UserFollow userFollow = userFollowDao.findByUserIdAndFollowUserId(userId, followUserId);
        if (userFollow != null) { //取消关注
            super.delete(userFollow);

            followUser.setFollowCount(followUser.getFollowCount() - 1);
        } else { //关注
            userFollow = new UserFollow();
            userFollow.setFollowUserId(followUserId);
            userFollow.setUserId(userId);
            super.save(userFollow);

            followUser.setFollowCount(followUser.getFollowCount() + 1);
        }
        userService.update(followUser);
    }
}
