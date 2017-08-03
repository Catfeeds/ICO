package com.tongwii.ico.dao;

import com.tongwii.ico.core.Mapper;
import com.tongwii.ico.model.Role;
import com.tongwii.ico.model.UserRoleRelation;

import java.util.List;

public interface UserRoleRelationMapper extends Mapper<UserRoleRelation> {
    List<Role> getByUserId(Integer id);
}