package com.mbor.dao;

import com.mbor.domain.Project;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDao extends RawDao<Project> implements IProjectDao {

    public ProjectDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.clazz = Project.class;
    }

}
