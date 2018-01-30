package com.beacon.dao;


import com.beacon.commons.utils.StringUtils;
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

    public List<PostsListOutDto> findPostsByHot(String keyword, Integer userId, Integer topicId, Integer start, Integer limit) {

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
                "   LEFT JOIN  user_topic ut ON ut.user_id = u.id " +
                "	WHERE		" +
                "		p.state = 'published'	" +
                "	AND p.deleted = 0		" +
                (topicId == null ? "" : " AND  p.topic_id = ? ") +
                (userId == null ? "" : " AND  u.user_id = ? ") +
                (StringUtils.isEmpty(keyword) ? "" : " AND  (p.title LIKE %?% OR u.nickname LIKE %?%)") +
                (userId == null ? "" : " AND  u.user_id = ? ") +
                "	ORDER BY		" +
                "		p.create_time desc	" +
                "	LIMIT ?,?		";

        List list = new ArrayList() {{
            if (topicId != null) {
                add(topicId);
            }

            if (userId != null) {
                add(userId);
            }

            if (!StringUtils.isEmpty(keyword)) {
                add(keyword);
            }

            add(start);
            add(limit);
        }};

        return npjt.getJdbcOperations().query(sql, new BeanPropertyRowMapper(PostsListOutDto.class), list.toArray());

    }

    public int countPostsByHot(String keyword, Integer userId, Integer topicId) {

        String sql = "	SELECT		" +
                "		COUNT (p.id),	" +
                "	FROM		" +
                "		posts p	" +
                "	LEFT JOIN user u ON u.id = p.user_id		" +
                "	LEFT JOIN  image i ON i.id = u.avatar_img_id		" +
                "   LEFT JOIN  topic t ON t.id = p.topic_id " +
                "	WHERE		" +
                "		p.state = 'published'	" +
                "	AND p.deleted = 0		" +
                (topicId == null ? "" : " AND  p.topic_id = ? ") +
                (userId == null ? "" : " AND  u.user_id = ? ") +
                (StringUtils.isEmpty(keyword) ? "" : " AND  (p.title LIKE %?% OR u.nickname LIKE %?%)") +
                (userId == null ? "" : " AND  u.user_id = ? ") +
                "	ORDER BY		" +
                "		p.create_time desc	" +
                "	LIMIT ?,?		";

        List list = new ArrayList() {{
            if (topicId != null) {
                add(topicId);
            }

            if (userId != null) {
                add(userId);
            }

            if (!StringUtils.isEmpty(keyword)) {
                add(keyword);
            }
        }};

        return npjt.getJdbcOperations().queryForObject(sql, Integer.class, list.toArray());

    }


    /**
     * 根据文章热度查询分页查询
     * @param startDate 格式如：20171231
     * */
    public List<PostsListOutDto> findPostsByDate(Integer startDate , Integer start, Integer limit){

        String sql = "	SELECT		"+
                "		p.id,	"+
                "		p.comment_count,	"+
                "		p.coin_count,	"+
                "		p.content,	"+
                "		p.create_time,	"+
                "		p.favorite_count,	"+
                "		p.likes_count,	"+
                "		p.read_count,	"+
                "		p.title,	"+
                "		p.topic_id,	"+
                "		p.user_id,	"+
                "		p.words_count,	"+
                "		u.avatar_img_id,	"+
                "		u.nickname,	"+
                "       u.follow_count,"+
                "       i.url, "+
                "       t.name topicName"+
                "	FROM		"+
                "		posts p	"+
                "	LEFT JOIN user u ON u.id = p.user_id		"+
                "	LEFT JOIN  image i ON i.id = u.avatar_img_id		"+
                "   LEFT JOIN  topic t ON t.id = p.topic_id " +
                "	WHERE		"+
                "		p.state = 'published'	"+
                "	AND p.deleted = 0		"+
                (  startDate==null?"":" AND  date_format(p.create_time,'%Y%m%d') >= ? " ) +
                "	ORDER BY		"+
                "		p.comment_count*2 + likes_count*3 + favorite_count * 4  +  read_count desc	"+
                "	LIMIT ?,?		";


        List list = new ArrayList(){{

            if(startDate != null){
                add(startDate);
            }

            add(start);
            add(limit);
        }};

        return npjt.getJdbcOperations().query(sql, new BeanPropertyRowMapper(PostsListOutDto.class) ,list.toArray());


    }





}
