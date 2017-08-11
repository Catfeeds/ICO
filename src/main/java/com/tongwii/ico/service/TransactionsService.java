package com.tongwii.ico.service;

import org.bitcoinj.core.Coin;

/**
 * 交易事物service
 *
 * @author Zeral
 * @date 2017-08-11
 */
public interface TransactionsService {

    /**
     * @param address
     */
    Coin getBitCoinAddressBalance(String address);
}
