package com.beacon.dao;


import com.beacon.pojo.PostsListOutDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Ian.Su
 * @version $ Id PostsSqlDao.java, v 0.1 2018/1/26 10:31 Ian.Su Exp $
 **/
@Repository
public class PostsSqlDao {

    @Inject
    private NamedParameterJdbcTemplate npjt;

    public List<PostsListOutDto> findPostsByHot(Integer topicId, Integer start, Integer limit) {

        String sql = "	SELECT		" +
                "		p.id,	" +
                "		p.comment_count,	" +
                "		p.coin_count,	" +
                "		p.content,	" +
                "		p.create_time,	" +
                "		p.favorite_count,	" +
                "		p.likes_count,	" +
                "		p.read_count,	" +
                "		p.title,	" +
                "		p.topic_id,	" +
                "		p.user_id,	" +
                "		p.words_count,	" +
                "		u.avatar_img_id,	" +
                "		u.nickname,	" +
                "       u.follow_count," +
                "       i.url, " +
                "       t.name topicName" +
                "	FROM		" +
                "		posts p	" +
                "	LEFT JOIN user u ON u.id = p.user_id		" +
                "	LEFT JOIN  image i ON i.id = u.avatar_img_id		" +
                "   LEFT JOIN  topic t ON t.id = p.topic_id " +
                "	WHERE		" +
                "		p.state = 'published'	" +
                "	AND p.deleted = 0		" +
                (topicId == null ? "" : " AND  p.topic_id = ? ") +
                "	ORDER BY		" +
                "		p.create_time desc	" +
                "	LIMIT ?,?		";

        List list = new ArrayList() {{
            if (topicId != null) {
                add(topicId);
            }

            add(start);
            add(limit);
        }};

        return npjt.getJdbcOperations().query(sql, new BeanPropertyRowMapper(PostsListOutDto.class), list.toArray());

    }

}
