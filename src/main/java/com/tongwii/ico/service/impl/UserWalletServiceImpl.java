package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.UserWalletMapper;
import com.tongwii.ico.model.UserWallet;
import com.tongwii.ico.service.UserWalletService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class UserWalletServiceImpl extends AbstractService<UserWallet> implements UserWalletService {
    @Resource
    private UserWalletMapper userWalletMapper;

}
