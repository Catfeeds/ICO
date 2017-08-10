package com.tongwii.ico.service;
import com.tongwii.ico.model.TokenDetail;
import com.tongwii.ico.core.Service;

import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface TokenDetailService extends Service<TokenDetail> {
    /**
     * 根据项目Id查询TokenDetail记录
     * @param projectId
     * @return
     */
    List<TokenDetail> findByProjectId(Integer projectId);
}
