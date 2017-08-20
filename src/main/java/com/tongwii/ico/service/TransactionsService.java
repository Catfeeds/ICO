package com.tongwii.ico.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 交易事物service
 *
 * @author Zeral
 * @date 2017 -08-11
 */
public interface TransactionsService {

    /**
     * 通过比特币请求钱包地址查询钱包余额
     *
     * @param address the address
     * @return 返回比特币实体 bit coin address balance
     */
    String getBitCoinAddressBalance(String address);

    /**
     * 通过比特币请求钱包地址查询交易列表信息
     * 默认查询前50条逆序数据
     *
     * @param address the address
     * @return JSONArray 交易列表jsonArray
     */
    JSONArray getBitCoinAddressTransaction(String address);


    /**
     * 根据比特币请求钱包地址查询单笔交易详细信息
     *
     * @param address the address
     * @return bit coin transaction
     */
    JSONObject getBitCoinTransaction(String address);

    /**
     * 根据以太坊请求钱包地址查询钱包余额
     *
     * @param address the address
     * @return eth address balance
     */
    String getEthAddressBalance(String address);

    /**
     * 比特币转账操作
     *
     * @param fromPrivateEncoderAddress the from private encoder address
     * @param toAddress                 需要转账的base85地址
     * @param amount                    需要转账的比特币，单位btc
     * @return string 转账成功的交易单号
     */
    String sendBitCoin(String fromPrivateEncoderAddress, String toAddress, String amount);

    /**
     * 以太坊转账操作
     *
     * @param fromPrivateEncoderAddress the from private encoder address
     * @param toAddress                 the to address
     * @param amount                    需要转账的以太坊，单位ETHER
     * @return string
     */
    String sendETHCoin(String fromPrivateEncoderAddress, String toAddress, String amount);

}
