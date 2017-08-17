package com.tongwii.ico.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tongwii.ico.service.TransactionsService;
import com.tongwii.ico.util.RestTemplateUtil;
import org.bitcoinj.core.Coin;
import org.ethereum.core.Denomination;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-08-11
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionsService {

    private static final String BITCOIN_HOST = "https://chain.api.btc.com/v3";
    private static final String ETH_HOST = "https://api.etherscan.io";

    @Value("${etherscan.apiKeyToken}")
    private String ethApiKey;

    @Override
    public String getBitCoinAddressBalance(String address) {

        Map<String, String> params = Maps.newHashMap();
        params.put("address", address);
        String response = RestTemplateUtil.restTemplate(BITCOIN_HOST+"/address/{address}", null, String.class, params, HttpMethod.GET);
        JSONObject result = JSON.parseObject(response);
        if(result.getIntValue("err_no") == 0) {
            try{
                JSONObject data = result.getJSONObject("data");
                return Coin.valueOf(data.getLong("balance")).toFriendlyString();
            }catch (Exception e){
                return Coin.valueOf(0).toFriendlyString();
            }
        }
        return null;
    }


    @Override
    public JSONArray getBitCoinAddressTransaction(String address) {

        Map<String, String> params = Maps.newHashMap();
        params.put("address", address);
        String response = RestTemplateUtil.restTemplate(BITCOIN_HOST+"/address/{address}/tx", null, String.class, params, HttpMethod.GET);
        JSONObject result = JSON.parseObject(response);
        if(result.getIntValue("err_no") == 0) {
            JSONObject data = result.getJSONObject("data");
            return data.getJSONArray("list");
        }
        return null;
    }

    @Override
    public JSONObject getBitCoinTransaction(String address) {
        Map<String, String> params = Maps.newHashMap();
        params.put("address", address);
        String response = RestTemplateUtil.restTemplate(BITCOIN_HOST+"/tx/{address}", null, String.class, params, HttpMethod.GET);
        JSONObject result = JSON.parseObject(response);
        if(result.getIntValue("err_no") == 0) {
            return result.getJSONObject("data");
        }
        return null;
    }

    @Override
    public String getEthAddressBalance(String address) {
        Map<String, String> params = Maps.newHashMap();
        params.put("address", address);
        String response = RestTemplateUtil.restTemplate(ETH_HOST+"/api?module=account&action=balance&address={address}&tag=latest&apikey="+ethApiKey, null, String.class, params, HttpMethod.GET);
        JSONObject result = JSON.parseObject(response);
        if(result.getIntValue("status") == 1) {
            return Denomination.toFriendlyString(result.getBigInteger("result"));
        }
        return null;
    }


}
