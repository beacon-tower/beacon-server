package com.beacon.asch.sdk.impl;

import com.beacon.asch.sdk.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.beacon.asch.sdk.AschResult;
import com.beacon.asch.sdk.AschSDK;
import com.beacon.asch.sdk.dto.query.BlockQueryParameters;

/**
 * @author fisher
 * @version $Id: BlockServiceTest.java, v 0.1 2017/8/1 18:49 fisher Exp $
 */
public class BlockServiceTest {

    @Test
    public void testGetBlockById() throws Exception {
        AschResult result= AschSDK.Block.getBlockById(TestData.blockId,false);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetBlockByHeight()throws Exception{
        AschResult result= AschSDK.Block.getBlockByHeight(TestData.blockHeight,false);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetBlockByHash()throws Exception{
        AschResult result= AschSDK.Block.getBlockByHash(TestData.blockHash,false);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testQueryBlocks()throws Exception{
        BlockQueryParameters parameters = new BlockQueryParameters();
        //parameters.setGeneratorPublicKey(TestData.publicKey());
        //parameters.setHeight(6890);
        parameters.setLimit(2);
        //parameters.setOffset(0);
        AschResult result= AschSDK.Block.queryBlocks(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetHeight()throws Exception{
        AschResult result= AschSDK.Block.getHeight();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetFree()throws Exception{
        AschResult result= AschSDK.Block.getFree();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetMilestone()throws Exception{
        AschResult result= AschSDK.Block.getMilestone();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetReward()throws Exception{
        AschResult result= AschSDK.Block.getReward();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetSupply()throws Exception{
        AschResult result= AschSDK.Block.getSupply();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetStauts()throws Exception{
        AschResult result= AschSDK.Block.getStauts();
        Assert.assertTrue(result.isSuccessful());
    }
}
