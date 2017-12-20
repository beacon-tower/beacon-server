package com.beacon.utils;

import com.beacon.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * shiro工具类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/26
 */
public class ShiroUtils {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static User getUser() {
        return (User) getSubject().getPrincipal();
    }

    public static Integer getUserId() {
        return getUser().getId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return getSubject().getPrincipal() != null;
    }

    public static void logout() {
        getSubject().logout();
    }
}
