package com.mbor.dao;

import com.mbor.domain.BusinessUnit;
import com.mbor.domain.Project;
import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectDao extends RawDao<Project> implements IProjectDao {

    public ProjectDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.clazz = Project.class;
    }

    @Override
    public List<Project> findByMultipleCriteria(String projectName, List<ProjectClass> projectClassList, String businessUnitName, List<ProjectStatus> projectStatusList, LocalDate projectStartDateLaterThat){
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
            Root<Project> project = criteriaQuery.from(Project.class);
            List<Predicate> predicates = new ArrayList<>();
            if(projectName != null){
                predicates.add(criteriaBuilder.like(project.get("projectName"), "%" + projectName + "%"));
            }
            if(projectClassList != null){
                CriteriaBuilder.In<ProjectClass> projectClassClause = criteriaBuilder.in(project.get("projectClass"));
                for(ProjectClass projectClass : projectClassList){
                    projectClassClause.value(projectClass);
                }
                predicates.add(projectClassClause);
            }
            //Todo Getting project from businessUnit in ManyToMany
//            if(businessUnitName != null){
//                predicates.add(criteriaBuilder.like(project.join("businessUnits").get("name"), "%" + businessUnitName + "%"));
//            }
            if(projectStatusList != null){
                CriteriaBuilder.In<ProjectStatus> projectStatusClause = criteriaBuilder.in(project.get("projectStatus"));
                for(ProjectStatus projectStatus : projectStatusList){
                    projectStatusClause.value(projectStatus);
                }
                predicates.add(projectStatusClause);
            }
            if(projectStartDateLaterThat != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(project.get("startDate"), projectStartDateLaterThat));
            }
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            TypedQuery<Project> allQuery = session.createQuery(criteriaQuery);
            return allQuery.getResultList();
        }
    }

    @Override
    public Project testSaveProject(Project project) {
        try(Session session = sessionFactory.openSession()) {
            BusinessUnit businessUnit = new BusinessUnit();
            businessUnit.setName("asfasdfasd");
            project.addBusinessUnit(businessUnit);
            session.save(project);
            return project;
        }
    }
}
