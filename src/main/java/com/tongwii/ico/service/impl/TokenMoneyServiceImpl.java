package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.TokenMoneyMapper;
import com.tongwii.ico.model.TokenMoney;
import com.tongwii.ico.service.TokenMoneyService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class TokenMoneyServiceImpl extends AbstractService<TokenMoney> implements TokenMoneyService {
    @Resource
    private TokenMoneyMapper tokenMoneyMapper;

    @Override
    public TokenMoney findByName(String ICOname) {
        TokenMoney tokenMoney = new TokenMoney();
        tokenMoney.setName(ICOname);
        List<TokenMoney> tokenMoneys = tokenMoneyMapper.select(tokenMoney);
        if(CollectionUtils.isEmpty(tokenMoneys)){
            return null;
        }
        return tokenMoneys.get(0);
    }
}
