package com.misiek.dao;

import com.misiek.domain.Project;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDaoImpl extends RawDao<Project> {

    public ProjectDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.clazz = Project.class;
    }
}
