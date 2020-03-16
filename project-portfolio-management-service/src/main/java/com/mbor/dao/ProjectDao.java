package com.mbor.dao;

import com.mbor.domain.Project;
import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;
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

    public ProjectDao() {
        this.clazz = Project.class;
    }

    @Override
    public List<Project> findByMultipleCriteria(String projectName, List<ProjectClass> projectClassList, String businessUnitName, List<ProjectStatus> projectStatusList, LocalDate projectStartDateLaterThat){
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
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

            if(businessUnitName != null){
                predicates.add(criteriaBuilder.like(project.join("businessUnits").get("name"), "%" + businessUnitName + "%"));
            }
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
            TypedQuery<Project> allQuery = entityManager.createQuery(criteriaQuery);
            return allQuery.getResultList();
        }

    @Override
    public List<Project> findResourceManagerProjects(Long resourceManagerId, Long projectId, String projectName, List<ProjectClass> projectClassList, List<ProjectStatus> projectStatusList) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        Root<Project> project = criteriaQuery.from(Project.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(project.get("resourceManager").get("id"), resourceManagerId));
        if(projectId != null){
            predicates.add(criteriaBuilder.like(project.get("id").as(String.class), projectId + "%"));
        }
        if(projectName != null){
            predicates.add(criteriaBuilder.like(project.get("projectName"), "%" + projectName + "%"));
        }
        if(projectClassList != null){
            CriteriaBuilder.In<ProjectClass> projectClassClause = criteriaBuilder.in(project.get("projectClass"));
            for(ProjectClass projectStatus : projectClassList){
                projectClassClause.value(projectStatus);
            }
            predicates.add(projectClassClause);
        }
        if(projectStatusList != null){
            CriteriaBuilder.In<ProjectStatus> projectStatusClause = criteriaBuilder.in(project.get("projectStatus"));
            for(ProjectStatus projectStatus : projectStatusList){
                projectStatusClause.value(projectStatus);
            }
            predicates.add(projectStatusClause);
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Project> allQuery = entityManager.createQuery(criteriaQuery);
        return allQuery.getResultList();
    }
}






