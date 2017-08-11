package com.tongwii.ico.service;
import com.tongwii.ico.model.TokenMoney;
import com.tongwii.ico.core.Service;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface TokenMoneyService extends Service<TokenMoney> {
    /**
     * 根据代币名称查询代币信息
     * @param ICOname
     * @return
     */
    TokenMoney findByName(String ICOname);
}
