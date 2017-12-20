package com.beacon.global.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 *
 * @author luckyhua
 * @version 1.0.0
 * @since 2017/09/26
 */
public class OAuth2Token implements AuthenticationToken {

    private String token;

    public OAuth2Token(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
