package com.misiek.service;

import com.misiek.dao.IDao;
import com.misiek.dao.ProjectDao;
import com.misiek.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends RawService<Project> {

    private ProjectDao projectDao;

    @Autowired
    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public IDao getDao() {
        return projectDao;
    }

}
