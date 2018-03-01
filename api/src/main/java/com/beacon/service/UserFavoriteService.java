package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.commons.specification.SimpleSpecificationBuilder;
import com.beacon.commons.specification.SpecificationOperator;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.commons.utils.CollectionUtils;
import com.beacon.commons.utils.StringUtils;
import com.beacon.dao.UserFavoriteDao;
import com.beacon.entity.Posts;
import com.beacon.entity.UserFavorite;
import com.beacon.mapper.PostsMapper;
import com.beacon.pojo.PostsFavoriteDto;
import com.beacon.utils.ShiroUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import static com.beacon.enums.code.PostsResCode.POSTS_ID_ERROR;

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

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private PostsMapper postsMapper;

    @Inject
    private PostsService postsService;

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

    /**
     * 我的收藏（文章）
     *
     * @param keyword 搜索关键字
     * @param pageNumber 页码
     * @param pageSize 页数
     * @return 分页后的数据
     */
    public Page<PostsFavoriteDto> myFavorite(String keyword, Integer pageNumber, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        String querySql = "select p.* ";
        String countSql = "select count(p.id) ";
        String sql = "from user_favorite uf left join posts p on p.id = uf.posts_id left join user u on p.user_id = u.id"
                + "where p.state = 'published' and u.id = " + ShiroUtils.getUserId();
        if (StringUtils.isNotEmpty(keyword)) {
            sql += " and (u.nickname like '%" + keyword + "%' or p.title like '%" + keyword + "%')";
        }
        sql += " order by uf.id desc limit " + pageNumber*pageSize + ", " + pageSize;
        List<Posts> postsList = jdbcTemplate.queryForList(querySql + sql, Posts.class);
        Integer count = jdbcTemplate.queryForObject(countSql + sql, Integer.class);
        List<PostsFavoriteDto> postsFavoriteDtoList = postsMapper.toFavoriteDtoList(postsList);
        return !CollectionUtils.isEmpty(postsList) ? new PageImpl<>(postsFavoriteDtoList, pageable, count) : null;
    }

    /**
     * 取消收藏
     *
     * @param postsId 文章id
     */
    public void removeFavorite(Integer postsId) {
        SimpleSpecificationBuilder<UserFavorite> spec = new SimpleSpecificationBuilder<>();
        spec.add("userId", SpecificationOperator.Operator.eq, ShiroUtils.getUserId());
        spec.add("postsId", SpecificationOperator.Operator.eq, postsId);
        List<UserFavorite> userFavoriteList = super.findAll(spec.generateSpecification());
        super.delete(userFavoriteList);

        Posts posts = postsService.findById(postsId);
        AssertUtils.notNull(POSTS_ID_ERROR, posts);
        posts.setFavoriteCount(posts.getFavoriteCount() - userFavoriteList.size());
        posts.setUpdateTime(new Date());
        postsService.update(posts);
    }
}
