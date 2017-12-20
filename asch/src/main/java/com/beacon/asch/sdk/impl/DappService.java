package com.beacon.asch.sdk.impl;

import com.beacon.asch.sdk.AschResult;
import com.beacon.asch.sdk.Dapp;
import com.beacon.asch.sdk.dbc.Argument;
import com.beacon.asch.sdk.dbc.ContractException;
import com.beacon.asch.sdk.dto.query.DappQureyParameters;
import com.beacon.asch.sdk.transaction.TransactionInfo;

import java.text.MessageFormat;

public class DappService extends AschRESTService implements Dapp {
    @Override
    public AschResult getBlocksHeight(String dappId) {
        try {
            Argument.notNull(dappId, "dappId is null");
            return get(MessageFormat.format(AschServiceUrls.Dapp.GET_BLOCKS_HEIGHT, dappId));
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    @Override
    public AschResult getBlocks(String dappId, DappQureyParameters parameters) {
        try {
            Argument.notNull(dappId, "dappId is null");
            ParameterMap query = parametersFromObject(parameters);
            return get(MessageFormat.format(AschServiceUrls.Dapp.GET_BLOCKS, dappId),  query);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult getAccount(String dappId,String address) {
        try {
            Argument.notNull(dappId, "dappId is null");
            return get(MessageFormat.format(AschServiceUrls.Dapp.GET_ACCOUNT,dappId,address));
        } catch (ContractException ex) {
            return fail(ex);
        }
    }

    @Override
    public AschResult dappDeposit(String dappId, String currency, long amount, String secret, String secondSecret) {
        try {
            Argument.notNull(dappId, "dappId is null");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid second secret");
            TransactionInfo transaction = getTransactionBuilder()
                    .buildInTransfer(dappId, currency,amount, secret, secondSecret);
            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

}
