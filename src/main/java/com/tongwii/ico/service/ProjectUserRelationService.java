package com.tongwii.ico.service;
import com.tongwii.ico.model.Project;
import com.tongwii.ico.model.ProjectUserRelation;
import com.tongwii.ico.core.Service;

import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface ProjectUserRelationService extends Service<ProjectUserRelation> {
    /**
     * 通过用户id查询用户锁定关系
     * @param userId
     * @return
     */
    List<ProjectUserRelation> findByUserId(Integer userId);

    /**
     * 判断用户是否锁定过该项目
     * @param userId
     * @param projectId
     * @return Boolean true - 用户锁定过
     */
    boolean userIsLockedProject(Integer userId,Integer projectId);
}
