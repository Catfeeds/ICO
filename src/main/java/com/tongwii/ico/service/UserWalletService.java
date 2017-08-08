package com.tongwii.ico.service;
import com.tongwii.ico.model.UserWallet;
import com.tongwii.ico.core.Service;

import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface UserWalletService extends Service<UserWallet> {

    List<UserWallet> findByUserId(Integer userId);
}
