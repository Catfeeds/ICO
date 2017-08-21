package com.tongwii.ico.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tongwii.ico.dao.UserWalletMapper;
import com.tongwii.ico.model.UserWallet;
import com.tongwii.ico.service.TokenMoneyService;
import com.tongwii.ico.service.TransactionsService;
import com.tongwii.ico.service.UserWalletService;
import com.tongwii.ico.core.AbstractService;
import com.tongwii.ico.util.TokenMoneyEnum;
import org.omg.IOP.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class UserWalletServiceImpl extends AbstractService<UserWallet> implements UserWalletService {
    @Resource
    private UserWalletMapper userWalletMapper;
    @Autowired
    private TokenMoneyService tokenMoneyService;
    @Autowired
    private TransactionsService transactionService;

    @Override
    public List<UserWallet> findByUserId(Integer userId) {
        UserWallet userWallet = new UserWallet();
        userWallet.setUserId(userId);
        return userWalletMapper.select(userWallet);
    }

    @Override
    public List<Object> findWalletByUserId(Integer userId) {
        UserWallet userWallet = new UserWallet();
        userWallet.setUserId(userId);
        userWallet.setType(1);
        List<UserWallet> userWallets = userWalletMapper.select(userWallet);
        // 此处的数据需要封装成为自己需要的数据
        if(CollectionUtils.isEmpty(userWallets)){
            return null;
        }
        List<Object> userWalletList = new ArrayList<Object>();
        for(int i=0;i<userWallets.size();i++){
            JSONObject wallet = new JSONObject();

            // 根据用户钱包地址获取用户钱包余额
            // 首先获取钱包的类型
            String walletName = tokenMoneyService.findById(userWallets.get(i).getTokenMoneyId()).getNameEnShort();
            String balance = "";
            if(walletName.equals(TokenMoneyEnum.ETH.name())){
                // 获取以太币钱包余额
                balance = transactionService.getEthAddressBalance(userWallets.get(i).getTokenMoneyUrl());
            }else{
                // 获取比特币钱包余额
                balance = transactionService.getBitCoinAddressBalance(userWallets.get(i).getTokenMoneyUrl());
            }
            wallet.put("tokenMoneyId",userWallets.get(i).getTokenMoneyId());
//            wallet.put("tokenMoneyURL",userWallets.get(i).getTokenMoneyUrl());
            wallet.put("walletState",userWallets.get(i).getState());
            wallet.put("walletBalance", balance);
            wallet.put("tokenMoneyName",tokenMoneyService.findById(userWallets.get(i).getTokenMoneyId()).getName());
            wallet.put("tokenMoneyEnName",tokenMoneyService.findById(userWallets.get(i).getTokenMoneyId()).getNameEnShort());
            userWalletList.add(wallet);
        }
        return userWalletList;
    }

    @Override
    public List<UserWallet> findWalletByUser(Integer userId, Integer type) {
        UserWallet userWallet = new UserWallet();
        userWallet.setUserId(userId);
        userWallet.setType(type);
        List<UserWallet> userWallets = userWalletMapper.selectOfficalUserWallet(userWallet);
        if(CollectionUtils.isEmpty(userWallets)){
            return null;
        }
        return userWallets;
    }

    @Override
    public UserWallet findWalletByCionId(Integer tokenMoneyId, Integer userId) {
        UserWallet userWallet = new UserWallet();
        userWallet.setTokenMoneyId(tokenMoneyId);
        userWallet.setUserId(userId);
        UserWallet userWallet1 = userWalletMapper.selectWalletByCoinId(userWallet);
        return userWallet1;
    }
}
