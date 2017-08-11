package com.tongwii.ico.service.impl;

import com.google.common.collect.Maps;
import com.tongwii.ico.service.TransactionsService;
import com.tongwii.ico.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.Coin;
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

    private static final String host = "https://blockexplorer.com";

    /**
     * 通过请求钱包地址查询钱包余额
     *
     * @param address
     * @return 返回比特币实体
     */
    @Override
    public Coin getBitCoinAddressBalance(String address) {
        Map<String, String> params = Maps.newHashMap();
        params.put("address", address);
        String response = RestTemplateUtil.restTemplate(host+"/api/addr/{address}/balance", null, String.class, params, HttpMethod.GET);
        if(StringUtils.isNotEmpty(response)) {
            return Coin.valueOf(Long.parseLong(response));
        }
        return null;
    }
}
