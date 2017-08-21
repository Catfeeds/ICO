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
     * 通过以太坊地址查询交易列表信息
     * 默认逆序排序
     *
     * 返回结果示例：
     * <pre>
     *  {
         "status": "1",
         "message": "OK",
         "result": [
             {
             "blockNumber": "4184123",
             "blockHash": "0x0e33d8347f7e39089d7b89dc5a1130980f401c97789f7dc37a9eb47c02166a52",
             "timeStamp": "1503283015",
             "hash": "0x03b152859bd97f0e23f11a70185e0f9fc1cd2faf362428996a6417abca7bdcb6",
             "nonce": "6067",
             "transactionIndex": "95",
             "from": "0x38570825d2c35db1b5fc603b1215dcfe1607bfb4",
             "to": "0xc50580b6bd9d917855fb822f90c40981f6540c0b",
             "value": "5481900000000000000",
             "gas": "21001",
             "gasPrice": "8000000000",
             "input": "0x",
             "contractAddress": "",
             "cumulativeGasUsed": "3032452",
             "gasUsed": "21000",
             "confirmations": "24",
             "isError": "0"
             }
                ]
          }
     * </pre>
     *
     * @param address   以太坊Hash地址
     * @return
     */
    JSONArray getETHAddressTransaction(String address);


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
