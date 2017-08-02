package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.TokenDetailMapper;
import com.tongwii.ico.model.TokenDetail;
import com.tongwii.ico.service.TokenDetailService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class TokenDetailServiceImpl extends AbstractService<TokenDetail> implements TokenDetailService {
    @Resource
    private TokenDetailMapper tokenDetailMapper;

}
