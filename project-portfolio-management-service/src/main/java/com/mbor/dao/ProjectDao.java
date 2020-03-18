package com.mbor.dao;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.ProjectRole;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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

    @Override
    public List<Project> findSupervisorProjects(Long supervisorId, Long projectId, String projectName, List<ProjectClass> projectClassList, List<ProjectStatus> projectStatusList, List<Long> projectManagerIdList, List<Long> solutionArchitectsIdList) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        criteriaQuery.distinct(true);
        Root<Project> projectRoot = criteriaQuery.from(Project.class);
        List<Predicate> predicates = new ArrayList<>();
        if(projectId != null){
            predicates.add(criteriaBuilder.like(projectRoot.get("id").as(String.class), projectId + "%"));
        }
        if(projectName != null){
            predicates.add(criteriaBuilder.like(projectRoot.get("projectName"), "%" + projectName + "%"));
        }
        if (projectClassList != null) {
            predicates.add(projectRoot.get("projectClass").in(projectClassList));
        }
        if (projectStatusList != null) {
            predicates.add(projectRoot.get("projectStatus").in(projectStatusList));
        }
        if (projectManagerIdList != null && solutionArchitectsIdList != null){
            Predicate projectManagerListPredicate = projectRoot.get("projectManager").get("id").in(projectManagerIdList);
            Predicate supervisorOfProjectManagerPredicate =  prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "projectManager", supervisorId);
            Predicate projectManagerPredicate = criteriaBuilder.and(projectManagerListPredicate, supervisorOfProjectManagerPredicate);

            Predicate solutionArchitectListPredicate = projectRoot.join("solutionArchitects").get("id").in(solutionArchitectsIdList);
            Predicate supervisorOfSolutionArchitectPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "solutionArchitects", supervisorId);
            Predicate solutionArchitectPredicate = criteriaBuilder.and(solutionArchitectListPredicate,supervisorOfSolutionArchitectPredicate);

            predicates.add(criteriaBuilder.or(projectManagerPredicate, solutionArchitectPredicate));
        } else if (projectManagerIdList != null){
            Predicate projectManagerListPredicate = projectRoot.get("projectManager").get("id").in(projectManagerIdList);
            Predicate supervisorOfProjectManagerPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "projectManager", supervisorId);
            predicates.add(criteriaBuilder.and(projectManagerListPredicate, supervisorOfProjectManagerPredicate));
        } else if (solutionArchitectsIdList != null){
            Predicate solutionArchitectListPredicate = projectRoot.join("solutionArchitects").get("id").in(solutionArchitectsIdList);
            Predicate supervisorOfSolutionArchitectPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "solutionArchitects", supervisorId);
            predicates.add(criteriaBuilder.and(solutionArchitectListPredicate,supervisorOfSolutionArchitectPredicate));
        } else {
            Predicate supervisorOfSolutionArchitectPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "projectManager", supervisorId);
            Predicate supervisorOfProjectManagerPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "solutionArchitects", supervisorId);
            predicates.add(criteriaBuilder.or(supervisorOfProjectManagerPredicate, supervisorOfSolutionArchitectPredicate));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Project> allQuery = entityManager.createQuery(criteriaQuery);
        return allQuery.getResultList();
    }

    private Predicate prepareSupervisorOfConsultantPredicate(Root<Project> projectRoot, CriteriaBuilder criteriaBuilder, String projectRoleName, Long supervisorId){
        Join<Project, ProjectRole> projectRoleJoin =  projectRoot.join(projectRoleName);
        Join<ProjectRole, Employee>  employeeJoin = projectRoleJoin.join("employee");
        Join<ProjectRole, Consultant> consultantJoin = criteriaBuilder.treat(employeeJoin, Consultant.class);
        return criteriaBuilder.equal(consultantJoin.get("supervisor").get("id"), supervisorId);
    }
}






