package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.Posts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/22
 */
@Repository
public interface PostsDao extends BaseDao<Posts, Integer> {

    @Query(value = "select p from Posts p where p.user.id = ?1 and p.topicId = ?2 order by p.seqInTopic asc")
    List<Posts> findList(Integer userId, Integer topicId);

    Posts findByIdAndState(Integer id, String state);
}
