package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.dao.UserDao;
import com.beacon.entity.User;
import com.beacon.utils.PasswordUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

import static com.beacon.enums.code.UserResCode.MOBILE_EXIST;
import static com.beacon.enums.code.UserResCode.MOBILE_NOT_EXIST;
import static com.beacon.enums.code.UserResCode.PASSWORD_ERROR;

/**
 * app用户
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/20
 */
@Service
public class UserService extends BaseService<User, Integer> {

    @Inject
    private UserDao userDao;

    @Override
    public BaseDao<User, Integer> getBaseDao() {
        return this.userDao;
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public Set<String> findPermsByUser(User user) {
        return null;
    }

    /**
     * 用户注册
     *
     * @param username 登录账号
     * @param password 登录密码
     */
    public void register(String username, String password) {
        User user = this.findByUsername(username);
        AssertUtils.isNull(MOBILE_EXIST, user);

        user = new User();
        user.setUsername(username);
        user.setPassword(PasswordUtils.createHash(password));
        this.save(user);
    }

    /**
     * 用户登录
     *
     * @param username 登录账号
     * @param password 登录密码
     * @return 登录用户id
     */
    public Integer login(String username, String password) {
        User user = this.findByUsername(username);
        AssertUtils.notNull(MOBILE_NOT_EXIST, user);
        AssertUtils.isTrue(PASSWORD_ERROR, PasswordUtils.validatePassword(password, user.getPassword()));
        return user.getId();
    }
}
