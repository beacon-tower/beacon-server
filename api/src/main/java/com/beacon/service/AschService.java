package com.beacon.service;

import com.beacon.asch.sdk.AschResult;
import com.beacon.asch.sdk.AschSDK;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * asch api接口调用
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/12/27
 */
@Component
public class AschService {

    @Value("${asch.root}")
    private String root;

    @PostConstruct
    public void init() {
        AschSDK.Config.setAschServer(root);
    }

    public AschResult newAccounts() {
        return AschSDK.Account.getNewAccounts();
    }

    public AschResult publicKeyLogin(String publicKey) {
        return AschSDK.Account.publicKeyLogin(publicKey);
    }

    public AschResult getPublicKey(String address) {
        return AschSDK.Account.getPublicKey(address);
    }
}
