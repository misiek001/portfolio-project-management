package com.misiek.dao;

import com.misiek.domain.Project;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDao extends RawDao<Project> {

    public ProjectDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.clazz = Project.class;
    }
}