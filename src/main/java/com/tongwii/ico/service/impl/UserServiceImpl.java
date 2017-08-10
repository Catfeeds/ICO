package com.tongwii.ico.service.impl;

import com.tongwii.ico.controller.UserController;
import com.tongwii.ico.core.AbstractService;
import com.tongwii.ico.dao.UserMapper;
import com.tongwii.ico.exception.EmailExistException;
import com.tongwii.ico.model.Role;
import com.tongwii.ico.model.User;
import com.tongwii.ico.model.UserRoleRelation;
import com.tongwii.ico.service.FileService;
import com.tongwii.ico.service.RoleService;
import com.tongwii.ico.service.UserRoleRelationService;
import com.tongwii.ico.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleRelationService userRoleRelationService;

    @Override
    public User findByIdCard(String idCard) {
        User user = new User();
        user.setIdCard(idCard);
        List<User> users = userMapper.select(user);
        if(CollectionUtils.isEmpty(users)){
            return null;
        }
        return users.get(0);
    }

    @Override
    public User findByUsername(String username) {
        User user = new User();
        user.setEmailAccount(username);
        return userMapper.selectOne(user);
    }

    @Override
    public void register(User user) {
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置用户昵称
        user.setNickName(user.getEmailAccount().substring(0, user.getEmailAccount().indexOf("@")));
        // 默认激活状态
        user.setEnabled(true);
        // 保存用户
        save(user);
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        userRoleRelation.setRoleId(roleService.findByRoleCode(Role.RoleCode.USER.getCode()).getId());
        userRoleRelation.setUserId(user.getId());
        // 保存用户关系
        userRoleRelationService.save(userRoleRelation);
    }

    @Override
    public void userUploadAvator(Integer userId, String url) {
        User user = findById(userId);
        user.setAvatorUrl(url);
        update(user);
    }

    public boolean emailAccountExist(String emailAccount) {
        if(Objects.nonNull(findByUsername(emailAccount))) {
            return true;
        }
        return false;
    }
}
