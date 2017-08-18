package com.tongwii.ico.dao;

import com.tongwii.ico.core.Mapper;
import com.tongwii.ico.model.Role;
import com.tongwii.ico.model.UserRoleRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRelationMapper extends Mapper<UserRoleRelation> {
    List<Role> selectByUserId(Integer id);
}