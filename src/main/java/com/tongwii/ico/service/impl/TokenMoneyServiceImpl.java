package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.TokenMoneyMapper;
import com.tongwii.ico.model.TokenMoney;
import com.tongwii.ico.service.TokenMoneyService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class TokenMoneyServiceImpl extends AbstractService<TokenMoney> implements TokenMoneyService {
    @Resource
    private TokenMoneyMapper tokenMoneyMapper;

}
