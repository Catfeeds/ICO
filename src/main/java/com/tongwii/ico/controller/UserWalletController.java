package com.tongwii.ico.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.TokenMoney;
import com.tongwii.ico.model.UserWallet;
import com.tongwii.ico.service.TokenMoneyService;
import com.tongwii.ico.service.TransactionsService;
import com.tongwii.ico.service.UserWalletService;
import com.tongwii.ico.util.ContextUtils;
import com.tongwii.ico.util.DesEncoder;
import com.tongwii.ico.util.TokenMoneyEnum;
import com.tongwii.ico.util.TokenMoneyUtil;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/user/wallet")
public class UserWalletController {
    @Resource
    private UserWalletService userWalletService;

    @Autowired
    private TokenMoneyService tokenMoneyService;
    @Autowired
    private TokenMoneyUtil tokenMoneyUtil;
    @Autowired
    private TransactionsService transactionsService;

    private static final Integer BTC = 1;
    private static final Integer ETH = 2;

    @PostMapping
    public Result add(@RequestBody UserWallet userWallet) {
        userWalletService.save(userWallet);
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userWalletService.deleteById(id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody UserWallet userWallet) {
        userWalletService.update(userWallet);
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        UserWallet userWallet = userWalletService.findById(id);
        return Result.successResult(userWallet);
    }

    /** 查询用户所有钱包接口 **/
    @GetMapping
    public Result list() {
        List<UserWallet> userWallets = userWalletService.findByUserId(ContextUtils.getUserId());
        for (UserWallet userWallet : userWallets) {
            if(Objects.nonNull(userWallet.getTokenMoneyId())) {
                TokenMoney tokenMoney = tokenMoneyService.findById(userWallet.getTokenMoneyId());
                userWallet.setTokenMoney(tokenMoney);
            }
        }
        return Result.successResult("获取用户钱包成功").add("userWallets", userWallets);
    }

    @GetMapping("/findWalletByUser")
    public Result findWalletByUser() {
        Integer userId = ContextUtils.getUserId();
        try{
            // 获取用户的输入交易钱包
            List<UserWallet> userWallets = userWalletService.findWalletByUser(userId, UserWallet.WalletType.IN_PUT.getValue());
            UserWallet BTCWallet = new UserWallet();
            UserWallet ETHWallet = new UserWallet();

            // 根据用户钱包地址获取用户钱包余额
            // 首先获取钱包的类型
            for(int i=0;i<userWallets.size();i++){
                TokenMoney tokenMoney = tokenMoneyService.findById(userWallets.get(i).getTokenMoneyId());
                userWallets.get(i).setTokenMoney(tokenMoney);
                String walletName = tokenMoneyService.findById(userWallets.get(i).getTokenMoneyId()).getNameEnShort();
                // 获取比特币的信息
                if(walletName.equals(TokenMoneyEnum.BTC.name())){
                    // 获取钱包余额
                    String BTCWalletBalance = transactionsService.getBitCoinAddressBalance(userWallets.get(i).getTokenMoneyUrl());
                    userWallets.get(i).setUserWalletBalance(BTCWalletBalance);
                    BTCWallet = userWallets.get(i);
                }
                // 获取以太坊的信息
                if(walletName.equals(TokenMoneyEnum.ETH.name())){
                    // 获取钱包余额
                    String EHTWalletBalance = transactionsService.getEthAddressBalance(userWallets.get(i).getTokenMoneyUrl());
                    userWallets.get(i).setUserWalletBalance(EHTWalletBalance);
                    ETHWallet = userWallets.get(i);
                }
            }
            return Result.successResult().add("BTCWallet",BTCWallet).add("ETHWallet", ETHWallet);
        }catch (Exception e){
            return Result.errorResult("用户钱包获取失败!");
        }
    }

    // 添加用户代币提现地址
    @PostMapping("/addUserWallet")
    @ResponseBody
    public Result addUserWallet(@RequestBody UserWallet userWallet) {
        Integer userId = ContextUtils.getUserId();
        // 验证提现地址是否有效
        boolean islegal = false;
        // 获取地址的类型
        String walletName = tokenMoneyService.findById(userWallet.getTokenMoneyId()).getNameEnShort();
        if(walletName.equals(TokenMoneyEnum.ETH.name())){
            islegal = tokenMoneyUtil.testETHAddr(userWallet.getTokenMoneyUrl());
        }
        if(walletName.equals(TokenMoneyEnum.BTC.name())){
            islegal = tokenMoneyUtil.testBitCoinAddr(userWallet.getTokenMoneyUrl());
        }

        if(islegal){
            // 根据用户id获取所有提现钱包url，判断其是否包含此钱包，如果没有则添加，输出钱包
            // TODO 这里的2要用枚举数据替代
            List<UserWallet> userWallets = userWalletService.findWalletByUser(userId, UserWallet.WalletType.OUT_PUT.getValue());
            if(!CollectionUtils.isEmpty(userWallets)){
                for(int i=0; i<userWallets.size();i++){
                    if(userWallets.get(i).getTokenMoneyUrl().equals(userWallet.getTokenMoneyUrl())){
                        return Result.errorResult("此地址信息已存在!");
                    }
                }
            }
            userWallet.setUserId(userId);
            userWallet.setState(1);
            userWallet.setType(2);
            userWalletService.save(userWallet);
            return Result.successResult("用户钱包添加成功!");
        }else{
            return Result.errorResult("钱包地址是非法的!");
        }
    }

    @GetMapping("/findPaymentWallet")
    public Result findPaymentWallet(){
        Integer userId = ContextUtils.getUserId();
        // 获取用户的提现地址信息
        List<UserWallet> userWallets = userWalletService.findWalletByUser(userId, UserWallet.WalletType.OUT_PUT.getValue());
        if(CollectionUtils.isEmpty(userWallets)){
            return Result.errorResult("用户未创建提现地址!");
        }else{
            JSONArray paymentWalletList = new JSONArray();
            for(int i=0;i<userWallets.size();i++){
                String addressType = tokenMoneyService.findById(userWallets.get(i).getTokenMoneyId()).getNameEnShort();
                JSONObject paymentWallet = new JSONObject();
                paymentWallet.put("addressid", userWallets.get(i).getId());
                paymentWallet.put("addressDesc", userWallets.get(i).getDes());
                paymentWallet.put("addressUrl", userWallets.get(i).getTokenMoneyUrl());
                paymentWallet.put("addressType", addressType);
                paymentWalletList.add(paymentWallet);
            }
            return Result.successResult().add("paymentWallet",paymentWalletList);
        }

    }

    @GetMapping("/test")
    public Result test(){
        List<UserWallet> userWallets = userWalletService.findWalletByUser(2, 2);
       return Result.successResult().add("list",userWallets);
    }
}
