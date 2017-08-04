package com.tongwii.ico.service;
import com.tongwii.ico.core.Service;
import com.tongwii.ico.model.User;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface UserService extends Service<User> {

    User findByUsername(String username);

    boolean emailAccountExist(String emailAccount);

    void register(User user);

    void userUploadAvator(Integer userId, String url);
}
