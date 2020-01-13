package com.mbor.dao;

import com.mbor.domain.employeeinproject.ProjectRole;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRoleDao extends RawDao<ProjectRole> implements IProjectRoleDao {

    public ProjectRoleDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.clazz = ProjectRole.class;
    }
}