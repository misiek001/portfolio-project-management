package com.misiek.service;

import com.misiek.dao.IDao;
import com.misiek.dao.IProjectRoleDao;
import com.misiek.domain.employeeinproject.ProjectRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ProjectRoleService extends RawService<ProjectRole> implements IProjectRoleService<ProjectRole> {

    private final IProjectRoleDao projectRoleDao;

    @Autowired
    public ProjectRoleService(IProjectRoleDao projectRoleDao) {
        this.projectRoleDao = projectRoleDao;
    }

    @Override
    public IDao getDao() {
        return projectRoleDao;
    }

}
