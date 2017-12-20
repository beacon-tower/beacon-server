package com.beacon.asch.sdk;

import com.beacon.asch.sdk.impl.AschFactory;

/**
 * Asch接口门面类，对外提供简单的访问模式
 * @author eagle
 *
 */
public final class AschSDK {
    public final static Account Account;
    public final static Delegate Delegate;
    public final static Transaction Transaction;
    public final static Block Block;
    public final static Signature Signature;
    public final static Dapp Dapp;
    public final static UIA UIA;
    public final static Peer Peer;
    public final static Misc Misc;

    public final static AschSDKConfig Config = AschSDKConfig.getInstance();
    public final static AschHelper Helper = new AschHelper();

    static {
        AschFactory factory = AschFactory.getInstance();

        Account = factory.getService(com.beacon.asch.sdk.Account.class);
        Block = factory.getService(com.beacon.asch.sdk.Block.class);
        Delegate = factory.getService(com.beacon.asch.sdk.Delegate.class);
        Transaction = factory.getService(com.beacon.asch.sdk.Transaction.class);
        Signature = factory.getService(com.beacon.asch.sdk.Signature.class);
        Dapp = factory.getService(com.beacon.asch.sdk.Dapp.class);
        UIA = factory.getService(com.beacon.asch.sdk.UIA.class);
        Peer = factory.getService(com.beacon.asch.sdk.Peer.class);
        Misc = factory.getService(com.beacon.asch.sdk.Misc.class);
    }

}
