package com.beacon.service;

import com.alibaba.fastjson.JSONObject;
import com.beacon.asch.sdk.AschResult;
import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.commons.redis.RedisHelper;
import com.beacon.commons.response.ResData;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.commons.utils.StringUtils;
import com.beacon.dao.UserDao;
import com.beacon.entity.User;
import com.beacon.global.session.TokenManager;
import com.beacon.global.session.TokenModel;
import com.beacon.pojo.UserInfoDto;
import com.beacon.utils.BeanUtils;
import com.beacon.utils.ShiroUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

import static com.beacon.commons.code.PublicResCode.ASCH_CALL_FAIL;
import static com.beacon.enums.code.UserResCode.*;
import static com.beacon.global.constant.Constant.REGISTER_MOBILE;

/**
 * app用户
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/20
 */
@Service
public class UserService extends BaseService<User, Integer> {

    public static final Long MOBILE_EXPIRE = 24 * 3600 * 1000L;

    @Inject
    private UserDao userDao;

    @Inject
    private RedisHelper redisHelper;

    @Inject
    private AschService aschService;

    @Inject
    private TokenManager tokenManager;

    @Override
    public BaseDao<User, Integer> getBaseDao() {
        return this.userDao;
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findByMobile(String mobile) {
        return userDao.findByMobile(mobile);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }


    public List<User> findUsers(Integer pageNumber, Integer limit) {

        return userDao.findUsers(pageNumber * limit - limit, limit);

    }

    public List<User> findUsersNotFollow(Integer userId, Integer pageNumber, Integer limit) {

        return userDao.findUsersNotFollow(userId, pageNumber * limit - limit, limit);

    }


    public Set<String> findPermsByUser(User user) {
        return null;
    }

    /**
     * 用户注册, 第一步，保存注册的手机号
     * 临时存到缓存中，一旦注册成功立即移除,
     * 如果一天内没有注册，自动移除
     *
     * @param mobile 手机号
     */
    public boolean registerFirstStep(String mobile) {
        return redisHelper.setExpire(REGISTER_MOBILE + mobile, mobile, MOBILE_EXPIRE);
    }

    /**
     * 用户注册, 第二步，校验注册手机号
     * 调用asch sdk新建账户,
     * 手机号和密钥绑定，存入缓存，用于校验，刷新缓存时间
     *
     * @param mobile 手机号
     * @return 账户信息
     */
    public ResData<Map<String, Object>> registerSecondStep(String mobile) {
        AssertUtils.isTrue(MOBILE_NOT_EXIST, redisHelper.exists(REGISTER_MOBILE + mobile));
        AschResult aschResult = aschService.newAccounts();
        if (aschResult.isSuccessful()) {
            Map<String, Object> parseMap = aschResult.parseMap();
            String secret = (String) parseMap.get("secret");
            String address = (String) parseMap.get("address");
            String publicKey = (String) parseMap.get("publicKey");
            redisHelper.setExpire(REGISTER_MOBILE + mobile, publicKey, MOBILE_EXPIRE);
            Map<String, Object> map = new HashMap<>(2);
            map.put("secret", secret);
            map.put("address", address);
            return ResData.success(map);
        }
        return ResData.error(ASCH_CALL_FAIL, aschResult.getError());
    }

    /**
     * 用户注册, 第三步
     * 校验注册手机号、校验密钥
     * 保存数据到数据库
     *
     * @param mobile    手机号
     * @param nickname  昵称
     * @param publicKey 钱包公钥需要根据用户提供的密码在在本地用程序生成
     * @return token信息
     */
    public ResData<String> registerThirdStep(String mobile, String nickname, String publicKey) {
        AssertUtils.isTrue(MOBILE_NOT_EXIST, redisHelper.exists(REGISTER_MOBILE + mobile));
        //再次校验手机号不存在库中
        User user = this.findByMobile(mobile);
        AssertUtils.isNull(MOBILE_EXIST, user);
        String cachePublicKey = (String) redisHelper.get(REGISTER_MOBILE + mobile);
        AssertUtils.isTrue(MOBILE_EXIST, publicKey.equals(cachePublicKey));
        AschResult aschResult = aschService.publicKeyLogin(publicKey);
        if (aschResult.isSuccessful()) {
            Map<String, Object> parseMap = aschResult.parseMap();
            String account = parseMap.get("account").toString();
            String address = (String) JSONObject.parseObject(account).get("address");
            //保存数据
            user = new User();
            user.setMobile(mobile);
            user.setNickname(nickname);
            user.setWalletAddress(address);
            super.save(user);
            //清空缓存数据
            redisHelper.remove(REGISTER_MOBILE + mobile);
            //创建token
            String token = tokenManager.createToken(user.getId(), TokenModel.TYPE_API);
            return ResData.success(token);
        }
        return ResData.error(ASCH_CALL_FAIL, aschResult.getError());
    }

    /**
     * 用户登录
     *
     * @param username  登录账号
     * @param publicKey 钱包公钥需要根据用户提供的密码在在本地用程序生成
     * @return 登录用户id
     */
    public ResData<String> login(String username, String publicKey) {
        User user = this.findByUsername(username);
        AssertUtils.notNull(USERNAME_ERROR, user);
//        String walletAddress = user.getWalletAddress();
//        AschResult publicKeyResult = aschService.getPublicKey(walletAddress);
//        if (publicKeyResult.isSuccessful()) {
//            Map<String, Object> publicKeyMap = publicKeyResult.parseMap();
//            String publicKeyInMap = publicKeyMap.get("publicKey").toString();
//            AssertUtils.isTrue(PUBLIC_KEY_ERROR, publicKey.equals(publicKeyInMap));
            AschResult aschResult = aschService.publicKeyLogin(publicKey);
            if (aschResult.isSuccessful()) {
                Map<String, Object> parseMap = aschResult.parseMap();
                String account = parseMap.get("account").toString();
                String address = (String) JSONObject.parseObject(account).get("address");
                AssertUtils.isTrue(ADDRESS_ERROR, user.getWalletAddress().equals(address));
                //创建token
                String token = tokenManager.createToken(user.getId(), TokenModel.TYPE_API);
                return ResData.success(token);
            }
            return ResData.error(ASCH_CALL_FAIL, aschResult.getError());
//        }
//        return ResData.error(ASCH_CALL_FAIL, publicKeyResult.getError());
    }

    /**
     * 修改用户信息
     *
     * @param userInfoDto 用户信息dto
     */
    public void editInfo(UserInfoDto userInfoDto) {
        User currentUser = ShiroUtils.getUser();
        User user;
        if (StringUtils.isNotEmpty(userInfoDto.getMobile()) && !currentUser.getMobile().equals(userInfoDto.getMobile())) {
            user = this.findByMobile(userInfoDto.getMobile());
            AssertUtils.isNull(MOBILE_EXIST, user);
        } else if (StringUtils.isNotEmpty(userInfoDto.getEmail())) {
            user = this.findByEmail(userInfoDto.getEmail());
            AssertUtils.isNull(EMAIL_EXIST, user);
        }
        BeanUtils.copyPropertiesIgnoreNull(userInfoDto, currentUser, "id");
        currentUser.setUpdateTime(new Date());
        super.update(currentUser);
    }
}
