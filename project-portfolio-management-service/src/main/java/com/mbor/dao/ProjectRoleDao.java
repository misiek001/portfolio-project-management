package com.mbor.dao;

import com.mbor.domain.employeeinproject.ProjectRole;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRoleDao extends RawDao<ProjectRole> implements IProjectRoleDao {

    public ProjectRoleDao() {
        this.clazz = ProjectRole.class;
    }
}
