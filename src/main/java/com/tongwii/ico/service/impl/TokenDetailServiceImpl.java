package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.TokenDetailMapper;
import com.tongwii.ico.model.TokenDetail;
import com.tongwii.ico.service.TokenDetailService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class TokenDetailServiceImpl extends AbstractService<TokenDetail> implements TokenDetailService {
    @Resource
    private TokenDetailMapper tokenDetailMapper;

    @Override
    public List<TokenDetail> findByProjectId(Integer projectId) {
        TokenDetail tokenDetail = new TokenDetail();
        tokenDetail.setInputTokenMoneyProjectId(projectId);
        List<TokenDetail> tokenDetails = tokenDetailMapper.select(tokenDetail);
        if(CollectionUtils.isEmpty(tokenDetails)){
            return null;
        }
        return tokenDetails;
    }

    @Override
    public List<TokenDetail> findByProjectIdAndType(Integer projectId, Integer type) {
        TokenDetail tokenDetail = new TokenDetail();
        tokenDetail.setInputTokenMoneyProjectId(projectId);
        tokenDetail.setType(type);
        List<TokenDetail> tokenDetails = tokenDetailMapper.select(tokenDetail);
        if(CollectionUtils.isEmpty(tokenDetails)){
            return null;
        }
        return tokenDetails;
    }
}
