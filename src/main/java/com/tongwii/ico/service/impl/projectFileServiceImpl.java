package com.tongwii.ico.service.impl;

import com.tongwii.ico.core.AbstractService;
import com.tongwii.ico.dao.projectFileMapper;
import com.tongwii.ico.model.projectFile;
import com.tongwii.ico.service.projectFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Zeral on 2017-08-15.
 */
@Service
@Transactional
public class projectFileServiceImpl extends AbstractService<projectFile> implements projectFileService {
    @Autowired
    private projectFileMapper fileMapper;

    @Override
    public projectFile findProjectFileByProjectId(Integer projectId) {
        projectFile p = new projectFile();
        p.setProjectId(projectId);
        projectFile projectFile = fileMapper.selectOne(p);
        if(projectFile == null){
            return null;
        }
        return projectFile;
    }
}
