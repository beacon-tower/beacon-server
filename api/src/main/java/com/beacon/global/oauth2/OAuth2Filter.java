package com.beacon.global.oauth2;

import com.beacon.commons.response.ResData;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.beacon.commons.code.PublicResCode.USER_NO_LOGIN;


/**
 * oauth2过滤器
 *
 * @author luckyhua
 * @version 1.0.0
 * @since 2017/09/26
 */
public class OAuth2Filter extends AuthenticatingFilter {

    private static final Logger log = LoggerFactory.getLogger(OAuth2Filter.class);

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if (StringUtils.isBlank(token)) {
            return null;
        }

        return new OAuth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            String json = new Gson().toJson(new ResData(USER_NO_LOGIN));
            httpResponse.getWriter().print(json);
            return false;
        }

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            log.error("login exception = {}", throwable.getMessage());

            String json = new Gson().toJson(new ResData(USER_NO_LOGIN));
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            log.error("login exception = ", e1);
        }

        return false;
    }

    /**
     * 获取请求的token
     * 先获取请求头中的token，如无则获取请求参数中的token
     *
     * @param httpRequest http请求
     * @return token字符串
     */
    private String getRequestToken(HttpServletRequest httpRequest) throws UnsupportedEncodingException {
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("token");
        }

        if (StringUtils.isNotBlank(token)) {
            token = URLDecoder.decode(token, "UTF-8");
        }
        return token;
    }
}
