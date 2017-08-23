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
     * 根据交易hash单号查询交易详情，返回结果如下
     * <pre>
     *     {
             "hash":"b6f6991d03df0e2e04dafffcd6bc418aac66049e2cd74b80f14ac86db1e3f0da",
             "ver":1,
             "vin_sz":1,
             "vout_sz":2,
             "lock_time":"Unavailable",
             "size":258,
             "relayed_by":"64.179.201.80",
             "block_height, 12200,
             "tx_index":"12563028",
             "inputs":[
             {
             "prev_out":{
             "hash":"a3e2bcc9a5f776112497a32b05f4b9e5b2405ed9",
             "value":"100000000",
             "tx_index":"12554260",
             "n":"2"
             },
             "script":"76a914641ad5051edd97029a003fe9efb29359fcee409d88ac"
             }

             ],
             "out":[

             {
             "value":"98000000",
             "hash":"29d6a3540acfa0a950bef2bfdc75cd51c24390fd",
             "script":"76a914641ad5051edd97029a003fe9efb29359fcee409d88ac"
             },

             {
             "value":"2000000",
             "hash":"17b5038a413f5c5ee288caa64cfab35a0c01914e",
             "script":"76a914641ad5051edd97029a003fe9efb29359fcee409d88ac"
             }

             ]
        }
     * </pre>
     * @param transactionHash hash256单号
     * @return
     */
    JSONObject getBitCoinTransactionDetail(String transactionHash);

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
