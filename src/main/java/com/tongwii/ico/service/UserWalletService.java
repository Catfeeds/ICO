package com.tongwii.ico.service;
import com.tongwii.ico.model.UserWallet;
import com.tongwii.ico.core.Service;

import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface UserWalletService extends Service<UserWallet> {

    List<UserWallet> findByUserId(Integer userId);

    /**
     * 根据用户ID获取指定信息的钱包信息
     * @param userId
     * @return
     */
    List<Object> findWalletByUserId(Integer userId);

    /**
     * 根据用户id查询用户钱包信息
     * @param userId
     * @param type
     * @return
     */
    List<UserWallet> findWalletByUser(Integer userId, Integer type);

    /**
     * 根据币种ID与用户Id查询用户钱包信息
     * @param tokenMoneyId
     * @param userId
     * @return
     */
    UserWallet findWalletByCionId(Integer tokenMoneyId, Integer userId);
}
