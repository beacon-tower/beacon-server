package com.beacon.global.oauth2;

import com.beacon.commons.utils.ExceptionUtils;
import com.beacon.entity.User;
import com.beacon.global.session.TokenManager;
import com.beacon.global.session.TokenModel;
import com.beacon.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Set;

import static com.beacon.commons.code.PublicResCode.USER_NO_LOGIN;

/**
 * 认证
 *
 * @author luckyhua
 * @version 1.0.0
 * @since 2017/08/23
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Inject
    private UserService userService;

    @Inject
    private TokenManager tokenManager;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        User user = (User) principals.getPrimaryPrincipal();

        //用户权限列表
        Set<String> permsSet = userService.findPermsByUser(user);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        Integer userId = tokenManager.getUserIdByToken(TokenModel.TYPE_API, accessToken);
        //token失效
        if (userId == null) {
            ExceptionUtils.throwResponseException(USER_NO_LOGIN);
        }

        //查询用户信息
        User user = userService.findById(userId);
        return new SimpleAuthenticationInfo(user, accessToken, getName());
    }
}
