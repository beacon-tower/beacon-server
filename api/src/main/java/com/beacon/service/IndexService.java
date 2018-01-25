package com.beacon.service;

import com.beacon.dao.PostsDao;
import com.beacon.entity.Posts;
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
    private PostsDao postsDao;

    List<Posts> findPostsNoLogin(){
        return null;
    }

}
