package com.mbor.service;

import com.mbor.dao.IDao;
import com.mbor.dao.IProjectRoleDao;
import com.mbor.domain.employeeinproject.ProjectRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<ProjectRole> findAllRoleOfEmployee(Long id){
        return projectRoleDao.findAllRoleOfEmployee(id);
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Object find(Long id) {
        return null;
    }

    @Override
    public Object save(Object o) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
