package com.tongwii.ico.dao;

import com.tongwii.ico.core.Mapper;
import com.tongwii.ico.model.Project;

import java.util.List;

public interface ProjectMapper extends Mapper<Project> {
    List<Project> selectOfficalProject();
}