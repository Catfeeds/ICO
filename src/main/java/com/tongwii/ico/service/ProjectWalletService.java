package com.tongwii.ico.service;
import com.tongwii.ico.model.ProjectWallet;
import com.tongwii.ico.core.Service;

import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface ProjectWalletService extends Service<ProjectWallet> {
    /**
     * 根据项目id查询项目钱包信息
     * @param projectId
     * @return
     */
    List<ProjectWallet> findWalletByProjectId(Integer projectId);
}
