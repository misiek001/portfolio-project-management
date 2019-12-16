package com.misiek.service;

import com.misiek.dao.AbstractDao;
import com.misiek.dao.ProjectDaoImpl;
import com.misiek.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends RawService<Project> {

    private ProjectDaoImpl projectDao;

    @Autowired
    public ProjectServiceImpl(ProjectDaoImpl projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public AbstractDao getDao() {
        return projectDao;
    }

}
