package com.beacon.service;

import com.beacon.dao.PostsSqlDao;
import com.beacon.pojo.PostsListOutDto;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * 首页服务接口
 *
 * @author Ian.Su
 * @version $Id IndexService.java, v 0.1 2018/1/25 11:27 Ian.Su Exp $
 **/
@Service
public class IndexService {

    @Inject
    private PostsSqlDao postsSqlDao;

    /**
     * 根据文章主题分页查询
     * */
    public List<PostsListOutDto> findPostsByHot(Integer topicId , Integer pageNumber, Integer limit){
        Integer start = (pageNumber*limit-limit);
        return postsSqlDao.findPostsByHot(topicId, start, limit);
    }


    /**
     * 根据文章热度查询分页查询
     * @param startDate 格式如：20171231
     * */
    public List<PostsListOutDto> findPostsByDate(Integer startDate , Integer pageNumber, Integer limit){

        Integer start = (pageNumber*limit-limit);

        return postsSqlDao.findPostsByDate(startDate, start, limit);

    }

}
