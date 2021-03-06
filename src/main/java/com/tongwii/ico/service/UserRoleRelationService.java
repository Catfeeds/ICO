package com.tongwii.ico.service;
import com.tongwii.ico.model.Role;
import com.tongwii.ico.model.UserRoleRelation;
import com.tongwii.ico.core.Service;

import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface UserRoleRelationService extends Service<UserRoleRelation> {

    List<Role> getByUserId(Integer id);
}
