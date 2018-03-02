package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.dao.PostsDao;
import com.beacon.entity.*;
import com.beacon.enums.dict.PostsDict;
import com.beacon.enums.dict.UserLikeDict;
import com.beacon.mapper.CommentMapper;
import com.beacon.mapper.PostsMapper;
import com.beacon.pojo.*;
import com.beacon.utils.BeanUtils;
import com.beacon.utils.ShiroUtils;
import com.beacon.utils.WordsUtils;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.beacon.enums.code.PostsResCode.POSTS_ID_ERROR;
import static com.beacon.enums.code.PostsResCode.POSTS_REPEAT_FAVORITE;
import static com.beacon.enums.code.PostsResCode.POSTS_REPEAT_LIKED;

/**
 * 文章
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/22
 */
@Service
public class PostsService extends BaseService<Posts, Integer> {

    @Inject
    private PostsDao postsDao;

    @Inject
    private PostsMapper postsMapper;

    @Inject
    private TopicService topicService;

    @Inject
    private CommentService commentService;

    @Inject
    private CommentMapper commentMapper;

    @Inject
    private UserLikeService userLikeService;

    @Inject
    private UserFavoriteService userFavoriteService;

    @Inject
    private UserFollowService userFollowService;

    @Override
    public BaseDao<Posts, Integer> getBaseDao() {
        return this.postsDao;
    }

    public Posts findPublishedPostsById(Integer id) {
        return postsDao.findByIdAndState(id, String.valueOf(PostsDict.STATE_PUBLISHED));
    }

    /**
     * 文章保存、编辑
     *
     * @param postInputDto
     * @return
     */
    public PostsOutDto saveOrUpdate(PostsInputDto postInputDto) {
        String content = postInputDto.getContent();
        //更新
        Posts posts;
        if (postInputDto.getId() != null) {
            posts = super.findById(postInputDto.getId());
            AssertUtils.notNull(POSTS_ID_ERROR, posts);
            BeanUtils.copyPropertiesIgnoreNull(postInputDto, posts);
            posts.setWordsCount(WordsUtils.getWordsNum(content));
            posts.setUpdateTime(new Date());
            super.update(posts);
        } else { //保存
            posts = postsMapper.fromDto(postInputDto);
            User user = ShiroUtils.getUser();
            //新建的文章排在该话题的第一个，默认值0，其它后移
            List<Posts> postsList = postsDao.findList(user.getId(), postInputDto.getTopicId());
            super.update(postsList.stream().map(p -> {
                p.setSeqInTopic(p.getSeqInTopic() + 1);
                return p;
            }).collect(Collectors.toList()));
            posts.setUser(user);
            posts.setWordsCount(WordsUtils.getWordsNum(content));
            posts.setState(String.valueOf(PostsDict.STATE_UNPUBLISHED));
            super.save(posts);
        }
        return postsMapper.toDto(posts);
    }

    /**
     * 获取文章内容
     *
     * @param id 文章id
     * @return 内容
     */
    public String findContent(Integer id) {
        Posts posts = super.findById(id);
        if (posts != null) {
            return posts.getContent();
        }
        return null;
    }

    /**
     * 获取话题内的用户文章列表
     *
     * @param userId 用户id
     * @param topicId 话题id
     * @return 文章列表
     */
    public List<PostsOutDto> findAuthorList(Integer userId, Integer topicId) {
        List<Posts> postsList = postsDao.findList(userId, topicId);
        return postsMapper.toDtoList(postsList);
    }

    /**
     * 文章发布
     *
     * @param postsId 文章id
     */
    public void publicize(Integer postsId) {
        Posts posts = super.findById(postsId);
        AssertUtils.notNull(POSTS_ID_ERROR, posts);
        posts.setState(String.valueOf(PostsDict.STATE_PUBLISHED));
        posts.setUpdateTime(new Date());
        super.update(posts);
    }

    /**
     * 文章顺序交换，同一话题内
     *
     * @param postsIdList 交换完后的id顺序
     */
    public void sequence(List<Integer> postsIdList) {
        Map<Integer, Posts> postsMap = super.findList(postsIdList)
                .stream().collect(Collectors.toMap(Posts::getId, posts -> posts));
        List<Posts> postsList = Lists.newLinkedList();
        for (int i = 0; i < postsIdList.size(); i++) {
            Integer id = postsIdList.get(i);
            Posts posts = postsMap.get(id);
            posts.setSeqInTopic(i);
            posts.setUpdateTime(new Date());
            postsList.add(posts);
        }
        super.update(postsList);
    }

    /**
     * 文章移动到别的话题中
     *
     * @param postsId 文章id
     * @param topicId 话题id
     */
    public void move(Integer postsId, Integer topicId) {
        Posts posts = super.findById(postsId);
        AssertUtils.notNull(POSTS_ID_ERROR, posts);
        posts.setTopicId(topicId);
        posts.setSeqInTopic(0);
        posts.setUpdateTime(new Date());
        List<Posts> postsList = postsDao.findList(ShiroUtils.getUserId(), topicId);
        postsList = postsList.stream().map(p -> {
            p.setSeqInTopic(p.getSeqInTopic() + 1);
            return p;
        }).collect(Collectors.toList());
        postsList.add(posts);
        super.update(postsList);
    }

    /**
     * 删除文章
     *
     * @param postsId 文章id
     */
    public void destroy(Integer postsId) {
        super.delete(postsId);
    }

    /**
     * 文章评论
     *
     * @param postsId 文章id
     * @param commentInputDto 评论内容
     * @return 评论后返回
     */
    public CommentOutDto addComment(Integer postsId, CommentInputDto commentInputDto) {
        Posts posts = this.findPublishedPostsById(postsId);
        AssertUtils.notNull(POSTS_ID_ERROR, posts);
        posts.setCommentCount(posts.getCommentCount() + 1);
        posts.setUpdateTime(new Date());
        super.update(posts);

        Comment comment = new Comment();
        comment.setUser(ShiroUtils.getUser());
        comment.setPostsId(postsId);
        comment.setParentId(commentInputDto.getParentId());
        comment.setContent(commentInputDto.getContent());
        if (commentInputDto.getParentId() == null) {
            comment.setFloor(commentService.getMaxFloor(postsId));
        } else {
            //更新父评论的子评论数
            Comment commentParent = commentService.findById(comment.getParentId());
            commentParent.setChildrenCount(commentParent.getChildrenCount() + 1);
            commentService.save(commentParent);
        }
        commentService.save(comment);

        if (commentInputDto.getParentId() == null) {
            return commentService.toParentOutDto(comment);
        } else {
            return commentMapper.toOutDto(comment);
        }
    }

    /**
     * 文章点赞
     * @param postsId 文章id
     */
    public void like(Integer postsId) {
        Integer userId = ShiroUtils.getUserId();
        boolean hasLike = userLikeService.hasLike(String.valueOf(UserLikeDict.TARGET_TYPE_POSTS), postsId);
        AssertUtils.isTrue(POSTS_REPEAT_LIKED, !hasLike);

        Posts posts = this.findPublishedPostsById(postsId);
        AssertUtils.notNull(POSTS_ID_ERROR, posts);
        posts.setLikesCount(posts.getLikesCount() + 1);
        posts.setUpdateTime(new Date());
        super.update(posts);

        UserLike userLike = new UserLike();
        userLike.setUserId(userId);
        userLike.setTargetType(String.valueOf(UserLikeDict.TARGET_TYPE_POSTS));
        userLike.setTargetValue(postsId);
        userLikeService.save(userLike);
    }

    /**
     * 文章收藏
     * @param postsId 文章id
     */
    public void addFavorite(Integer postsId) {
        Integer userId = ShiroUtils.getUserId();
        boolean hasFavorite = userFavoriteService.hasFavorite(userId, postsId);
        AssertUtils.isTrue(POSTS_REPEAT_FAVORITE, !hasFavorite);

        Posts posts = this.findPublishedPostsById(postsId);
        AssertUtils.notNull(POSTS_ID_ERROR, posts);
        posts.setFavoriteCount(posts.getFavoriteCount() + 1);
        posts.setUpdateTime(new Date());
        super.update(posts);

        UserFavorite userFavorite = new UserFavorite();
        userFavorite.setUserId(userId);
        userFavorite.setPostsId(postsId);
        userFavoriteService.save(userFavorite);
    }

    /**
     * 文章详情
     *
     * @param postsId 文章id
     */
    public PostsDetailDto detail(Integer postsId) {
        Posts posts = this.findPublishedPostsById(postsId);
        AssertUtils.notNull(POSTS_ID_ERROR, posts);
        posts.setReadCount(posts.getReadCount() + 1);
        super.update(posts);

        PostsDetailDto postsDetailDto = postsMapper.toDetailDto(posts);
        postsDetailDto.setFollowed(userFollowService.hasFollowedAuthor(posts.getUser().getId()));
        return postsDetailDto;
    }

    /**
     * 评论列表
     *
     * @param postsId 文章id
     * @param pageNumber 页码
     * @param pageSize 每页多少条
     * @return 分页列表
     */
    public Page<CommentParentOutDto> commentList(Integer postsId, Integer pageNumber, Integer pageSize) {
        Posts posts = this.findPublishedPostsById(postsId);
        AssertUtils.notNull(POSTS_ID_ERROR, posts);

        return commentService.commentList(postsId, pageNumber, pageSize);
    }
}
