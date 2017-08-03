package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.UserRoleRelationMapper;
import com.tongwii.ico.model.Role;
import com.tongwii.ico.model.UserRoleRelation;
import com.tongwii.ico.service.UserRoleRelationService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class UserRoleRelationServiceImpl extends AbstractService<UserRoleRelation> implements UserRoleRelationService {
    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public List<Role> getByUserId ( Integer userId ) {
       List<Role> roles = userRoleRelationMapper.selectByUserId(userId);
       return roles;
    }
}
