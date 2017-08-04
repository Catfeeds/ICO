package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.RoleMapper;
import com.tongwii.ico.model.Role;
import com.tongwii.ico.service.RoleService;
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
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public Role findByRoleCode(String roleCode) {
        Role role = new Role();
        role.setRoleNameCode(roleCode);
        return roleMapper.selectOne(role);
    }
}
