package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.UserMapper;
import com.tongwii.ico.model.User;
import com.tongwii.ico.service.UserService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        User user = new User();
        user.setEmailAccount(username);
        return userMapper.selectOne(user);
    }

    @Override
    public void register(User user) {

    }
}
