package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.commons.utils.CollectionUtils;
import com.beacon.dao.PostsSqlDao;
import com.beacon.dao.UserFollowDao;
import com.beacon.entity.User;
import com.beacon.entity.UserFollow;
import com.beacon.pojo.PostsListOutDto;
import com.beacon.utils.ShiroUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

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

    @Inject
    private PostsSqlDao postsSqlDao;

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

    /**
     * 用户是否关注过作者
     *
     * @param authorUserId 作者id
     * @return 是否关注
     */
    public boolean hasFollowedAuthor(Integer authorUserId) {
        return userFollowDao.countByUserIdAndFollowUserId(ShiroUtils.getUserId(), authorUserId) > 0;
    }

    /**
     * 我的关注/推荐关注
     *
     * @param userId 用户id
     * @param topicId 话题id
     * @param keyword 搜索关键字
     * @param pageNumber 页码
     * @param pageSize 每页数
     * @return 分页数据
     */
    public Page<?> myFollow(Integer userId, Integer topicId, String keyword, Integer pageNumber, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        Integer start = (pageNumber*pageSize-pageSize);
        int countPostsByHot = postsSqlDao.countPostsByHot(keyword, userId, topicId);
        List<PostsListOutDto> postsListOutDtos = postsSqlDao.findPostsByHot(keyword, userId, topicId, start, pageSize);
        return !CollectionUtils.isEmpty(postsListOutDtos) ? new PageImpl<>(postsListOutDtos, pageable, countPostsByHot) : null;
    }
}
