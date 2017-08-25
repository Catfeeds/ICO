package com.tongwii.ico.service;
import com.tongwii.ico.model.Project;
import com.tongwii.ico.core.Service;
import com.tongwii.ico.model.TokenDetail;
import com.tongwii.ico.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface ProjectService extends Service<Project> {

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

    /**
     * 查询所有状态非-1的project信息
     * @return
     */
    List<Project> findOfficalProject();

    /**
     * 根据项目状态查询项目
     * @param state
     * @return
     */
    List<Project> findProjectByState(Integer state);

    List<Project> test();
  
}
