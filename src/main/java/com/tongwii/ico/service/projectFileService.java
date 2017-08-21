package com.tongwii.ico.service;
import com.tongwii.ico.model.projectFile;
import com.tongwii.ico.core.Service;


/**
 * Created by Zeral on 2017-08-15.
 */
public interface projectFileService extends Service<projectFile> {

    /**
     * 根据项目id与文件类型查询数据
     * @param projectId
     * @param type
     * @return
     */
    projectFile findProjectFileByType(Integer projectId, String type);

}
