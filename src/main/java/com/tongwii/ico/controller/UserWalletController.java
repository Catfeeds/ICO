package com.tongwii.ico.controller;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.TokenMoney;
import com.tongwii.ico.model.UserWallet;
import com.tongwii.ico.service.TokenMoneyService;
import com.tongwii.ico.service.UserWalletService;
import com.tongwii.ico.util.ContextUtils;
import com.tongwii.ico.util.DesEncoder;
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
            // 获取用户的交易钱包
            // TODO 这里的1要用枚举数据替代
            List<UserWallet> userWallets = userWalletService.findWalletByUser(userId,1);
            UserWallet BTCWallet = new UserWallet();
            UserWallet ETHWallet = new UserWallet();
            for(int i=0;i<userWallets.size();i++){
                // 获取比特币的信息
                if(userWallets.get(i).getTokenMoneyId() == BTC){
                    BTCWallet = userWallets.get(i);
                }
                if(userWallets.get(i).getTokenMoneyId() == ETH){
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
        if(true){
            // 根据用户id获取所有提现钱包url，判断其是否包含此钱包，如果没有则添加
            // TODO 这里的2要用枚举数据替代
            List<UserWallet> userWallets = userWalletService.findWalletByUser(userId, 2);
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
}
