package com.tongwii.ico.dao;

import com.tongwii.ico.core.Mapper;
import com.tongwii.ico.model.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper extends Mapper<Project> {
    List<Project> selectOfficalProject();
}