package com.tongwii.ico.dao;

import com.tongwii.ico.core.Mapper;
import com.tongwii.ico.model.UserWallet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWalletMapper extends Mapper<UserWallet> {

    List<UserWallet> selectOfficalUserWallet(UserWallet userWallet);

    UserWallet selectWalletByCoinId(UserWallet userWallet);
}