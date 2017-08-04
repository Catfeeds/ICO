package com.tongwii.ico.service;
import com.tongwii.ico.core.Service;
import com.tongwii.ico.model.Role;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface RoleService extends Service<Role> {
    Role findByRoleCode(String roleCode);
}
