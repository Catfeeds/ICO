package com.tongwii.ico.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tongwii.ico.dao.UserWalletMapper;
import com.tongwii.ico.model.UserWallet;
import com.tongwii.ico.service.TokenMoneyService;
import com.tongwii.ico.service.UserWalletService;
import com.tongwii.ico.core.AbstractService;
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
        List<UserWallet> userWallets = userWalletMapper.select(userWallet);
        // 此处的数据需要封装成为自己需要的数据
        if(CollectionUtils.isEmpty(userWallets)){
            return null;
        }
        List<Object> userWalletList = new ArrayList<Object>();
        for(int i=0;i<userWallets.size();i++){
            JSONObject wallet = new JSONObject();
            wallet.put("tokenMoneyId",userWallets.get(i).getTokenMoneyId());
//            wallet.put("tokenMoneyURL",userWallets.get(i).getTokenMoneyUrl());
            wallet.put("walletState",userWallets.get(i).getState());
            wallet.put("tokenMoneyName",tokenMoneyService.findById(userWallets.get(i).getTokenMoneyId()).getName());
            wallet.put("tokenMoneyEnName",tokenMoneyService.findById(userWallets.get(i).getTokenMoneyId()).getNameEnShort());
            userWalletList.add(wallet);
        }
        return userWalletList;
    }

    @Override
    public List<UserWallet> findWalletByUser(Integer userId) {
        UserWallet userWallet = new UserWallet();
        userWallet.setUserId(userId);
        List<UserWallet> userWallets = userWalletMapper.select(userWallet);
        if(CollectionUtils.isEmpty(userWallets)){
            return null;
        }
        return userWallets;
    }
}
