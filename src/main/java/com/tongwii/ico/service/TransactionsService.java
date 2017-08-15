package com.tongwii.ico.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.bitcoinj.core.Coin;

/**
 * 交易事物service
 *
 * @author Zeral
 * @date 2017-08-11
 */
public interface TransactionsService {

    /**
     * 通过请求钱包地址查询钱包余额
     *
     * @param address
     * @return 返回比特币实体
     */
    Coin getBitCoinAddressBalance(String address);

    /**
     * 通过请求钱包地址查询交易列表信息
     * 默认查询前50条逆序数据
     *
     * @param address
     * @return JSONArray 交易列表jsonArray
     */
    JSONArray getBitCoinAddressTransaction(String address);


    /**
     * 根据请求钱包地址查询单笔交易详细信息
     * @param address
     * @return
     */
    JSONObject getBitCoinTransaction(String address);
}
