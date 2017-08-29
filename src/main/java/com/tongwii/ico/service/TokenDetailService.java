package com.tongwii.ico.service;
import com.tongwii.ico.model.TokenDetail;
import com.tongwii.ico.core.Service;

import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface TokenDetailService extends Service<TokenDetail> {
    /**
     * 根据项目id与代币详情类型查询数据
     * @param projectId
     * @param type
     * @return
     */
    List<TokenDetail> findByProjectIdAndType(Integer projectId, Integer type);
}
