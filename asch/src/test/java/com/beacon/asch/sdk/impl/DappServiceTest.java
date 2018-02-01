package com.beacon.asch.sdk.impl;

import com.beacon.asch.sdk.TestData;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.beacon.asch.sdk.AschResult;
import com.beacon.asch.sdk.AschSDK;
import com.beacon.asch.sdk.dto.query.DappQureyParameters;

/**
 * @author fisher
 * @version $Id: DappServiceTest.java, v 0.1 2017/12/1 18:19 fisher Exp $
 */
public class DappServiceTest {
    @BeforeSuite
    public void SetUp(){
        AschSDK.Config.setAschServer(TestData.root);
    }

    @Test
    public void testGetBlocksHeight() throws Exception {
        AschResult result= AschSDK.Dapp.getBlocksHeight(TestData.dappId);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetBlocks() throws Exception {
        DappQureyParameters parameters = new DappQureyParameters();
        parameters.setLimit(10);
        AschResult result= AschSDK.Dapp.getBlocks(TestData.dappId,parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetAccount() throws Exception {
        AschResult result= AschSDK.Dapp.getAccount(TestData.dappId,TestData.dappAddress);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testDappDeposit() throws Exception {
        AschResult result= AschSDK.Dapp.dappDeposit(TestData.dappId,"FHT",20*100000000,TestData.secret,null);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testDappWithDraw() throws Exception {
        AschResult result= AschSDK.Dapp.dappWithDraw(TestData.dappId,"FHChain.FHT",2*100000000,TestData.secret);
        Assert.assertTrue(result.isSuccessful());
    }

}
