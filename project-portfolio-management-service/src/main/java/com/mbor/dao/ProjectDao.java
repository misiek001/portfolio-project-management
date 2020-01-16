package com.mbor.dao;

import com.mbor.domain.Project;
import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ProjectDao extends RawDao<Project> implements IProjectDao {

    public ProjectDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.clazz = Project.class;
    }

    @Override
    public List<Project> findByMultipleCriteria(String projectName, List<ProjectClass> projectClassList, String businessUnitName, List<ProjectStatus> projectStatusList, LocalDate projectStartDate){
        return null;
    }

}
