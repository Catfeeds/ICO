package com.tongwii.ico.service;
import com.tongwii.ico.model.Project;
import com.tongwii.ico.core.Service;
import com.tongwii.ico.model.TokenDetail;
import com.tongwii.ico.model.User;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface ProjectService extends Service<Project> {
    /**
     * Update input token money.
     *
     * @param id          the id
     * @param tokenDetail the token detail
     */
    void updateInputTokenMoney(Integer id, TokenDetail tokenDetail);

    /**
     * Update output token money.
     *
     * @param id          the id
     * @param tokenDetail the token detail
     */
    void updateOutputTokenMoney(Integer id, TokenDetail tokenDetail);

    /**
     * Update create user.
     *
     * @param id   the id
     * @param user the user
     */
    void updateCreateUser(Integer id, User user);
}
