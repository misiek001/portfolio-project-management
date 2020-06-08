package com.mbor.dao;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ProjectRole;
import com.mbor.domain.employeeinproject.SolutionArchitect;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProjectDao extends RawDao<Project> implements IProjectDao {

    public ProjectDao() {
        this.clazz = Project.class;
    }

    @Override
    public List<Project> findByMultipleCriteria(String projectName, List<ProjectClass> projectClassList, String businessUnitName, List<ProjectStatus> projectStatusList, LocalDate projectStartDateLaterThat) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        Root<Project> project = criteriaQuery.from(Project.class);
        List<Predicate> predicates = new ArrayList<>();
        if (projectName != null) {
            predicates.add(criteriaBuilder.like(project.get("projectName"), "%" + projectName + "%"));
        }
        if (projectClassList != null) {
            CriteriaBuilder.In<ProjectClass> projectClassClause = criteriaBuilder.in(project.get("projectClass"));
            for (ProjectClass projectClass : projectClassList) {
                projectClassClause.value(projectClass);
            }
            predicates.add(projectClassClause);
        }
        if (businessUnitName != null) {
            predicates.add(criteriaBuilder.like(project.join("businessUnits").get("name"), "%" + businessUnitName + "%"));
        }
        if (projectStatusList != null) {
            CriteriaBuilder.In<ProjectStatus> projectStatusClause = criteriaBuilder.in(project.get("projectStatus"));
            for (ProjectStatus projectStatus : projectStatusList) {
                projectStatusClause.value(projectStatus);
            }
            predicates.add(projectStatusClause);
        }
        if (projectStartDateLaterThat != null) {
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
        Root<Project> projectRoot = criteriaQuery.from(Project.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(projectRoot.get("resourceManager").get("id"), resourceManagerId));
        if (projectId != null) {
            predicates.add(criteriaBuilder.like(projectRoot.get("id").as(String.class), projectId + "%"));
        }
        if (projectName != null) {
            predicates.add(criteriaBuilder.like(projectRoot.get("projectName"), "%" + projectName + "%"));
        }
        if (projectClassList != null) {
            CriteriaBuilder.In<ProjectClass> projectClassClause = criteriaBuilder.in(projectRoot.get("projectClass"));
            for (ProjectClass projectStatus : projectClassList) {
                projectClassClause.value(projectStatus);
            }
            predicates.add(projectClassClause);
        }
        if (projectStatusList != null) {

            SetJoin<Project, ProjectStatusHistoryLine> join = projectRoot.joinSet("projectStatusHistoryLines");

            Subquery<LocalDateTime> recentDateOfHistoryLinesQuery = criteriaQuery.subquery(LocalDateTime.class);
            Root<ProjectStatusHistoryLine> recentDateOfHistoryLinesRoot = recentDateOfHistoryLinesQuery.from(ProjectStatusHistoryLine.class);

            recentDateOfHistoryLinesQuery.select(criteriaBuilder.greatest(recentDateOfHistoryLinesRoot.<LocalDateTime>get("creationTime")))
                    .where(criteriaBuilder.equal(recentDateOfHistoryLinesRoot.get("project"), projectRoot.get("id")));

            predicates.add(criteriaBuilder.equal(join.get("creationTime"), recentDateOfHistoryLinesQuery));

            CriteriaBuilder.In<ProjectStatus> projectStatusClause = criteriaBuilder.in(join.get("currentStatus"));
            for(ProjectStatus projectStatus : projectStatusList){
                projectStatusClause.value(ProjectStatus.valueOf(projectStatus.name()));
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
        if (projectId != null) {
            predicates.add(criteriaBuilder.like(projectRoot.get("id").as(String.class), projectId + "%"));
        }
        if (projectName != null) {
            predicates.add(criteriaBuilder.like(projectRoot.get("projectName"), "%" + projectName + "%"));
        }
        if (projectClassList != null) {
            predicates.add(projectRoot.get("projectClass").in(projectClassList));
        }
        if (projectStatusList != null) {

            SetJoin<Project, ProjectStatusHistoryLine> join = projectRoot.joinSet("projectStatusHistoryLines");

            Subquery<LocalDateTime> recentDateOfHistoryLinesQuery = criteriaQuery.subquery(LocalDateTime.class);
            Root<ProjectStatusHistoryLine> recentDateOfHistoryLinesRoot = recentDateOfHistoryLinesQuery.from(ProjectStatusHistoryLine.class);

            recentDateOfHistoryLinesQuery.select(criteriaBuilder.greatest(recentDateOfHistoryLinesRoot.<LocalDateTime>get("creationTime")))
                    .where(criteriaBuilder.equal(recentDateOfHistoryLinesRoot.get("project"), projectRoot.get("id")));

            predicates.add(criteriaBuilder.equal(join.get("creationTime"), recentDateOfHistoryLinesQuery));

            CriteriaBuilder.In<ProjectStatus> projectStatusClause = criteriaBuilder.in(join.get("currentStatus"));
            for(ProjectStatus projectStatus : projectStatusList){
                projectStatusClause.value(ProjectStatus.valueOf(projectStatus.name()));
            }
            predicates.add(projectStatusClause);
        }
        if (projectManagerIdList != null && solutionArchitectsIdList != null) {
            Predicate projectManagerListPredicate = projectRoot.get("projectManager").get("id").in(projectManagerIdList);
            Predicate supervisorOfProjectManagerPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "projectManager", supervisorId);
            Predicate projectManagerPredicate = criteriaBuilder.and(projectManagerListPredicate, supervisorOfProjectManagerPredicate);

            Predicate solutionArchitectListPredicate = projectRoot.join("solutionArchitects").get("id").in(solutionArchitectsIdList);
            Predicate supervisorOfSolutionArchitectPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "solutionArchitects", supervisorId);
            Predicate solutionArchitectPredicate = criteriaBuilder.and(solutionArchitectListPredicate, supervisorOfSolutionArchitectPredicate);

            predicates.add(criteriaBuilder.or(projectManagerPredicate, solutionArchitectPredicate));
        } else if (projectManagerIdList != null) {
            Predicate projectManagerListPredicate = projectRoot.get("projectManager").get("id").in(projectManagerIdList);
            Predicate supervisorOfProjectManagerPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "projectManager", supervisorId);
            predicates.add(criteriaBuilder.and(projectManagerListPredicate, supervisorOfProjectManagerPredicate));
        } else if (solutionArchitectsIdList != null) {
            Predicate solutionArchitectListPredicate = projectRoot.join("solutionArchitects").get("id").in(solutionArchitectsIdList);
            Predicate supervisorOfSolutionArchitectPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "solutionArchitects", supervisorId);
            predicates.add(criteriaBuilder.and(solutionArchitectListPredicate, supervisorOfSolutionArchitectPredicate));
        } else {
            Predicate supervisorOfSolutionArchitectPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "projectManager", supervisorId);
            Predicate supervisorOfProjectManagerPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "solutionArchitects", supervisorId);
            predicates.add(criteriaBuilder.or(supervisorOfProjectManagerPredicate, supervisorOfSolutionArchitectPredicate));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Project> allQuery = entityManager.createQuery(criteriaQuery);
        return allQuery.getResultList();
    }

    @Override
    public List<Project> findConsultantProject(Long consultantId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);

        Root<ProjectManager> projectManagerRoot = criteriaQuery.from(ProjectManager.class);

        SetJoin<ProjectManager, Project> projectManagerProjectSetJoin = projectManagerRoot.joinSet("projects");

        criteriaQuery.select(projectManagerProjectSetJoin).where(criteriaBuilder.equal(projectManagerRoot.get("employee").get("id"), consultantId));
        List<Project> projectManagerProjects = entityManager.createQuery(criteriaQuery).getResultList();
        criteriaQuery = criteriaBuilder.createQuery(Project.class);
        Root<SolutionArchitect> solutionArchitectRoot = criteriaQuery.from(SolutionArchitect.class);
        SetJoin<SolutionArchitect, Project> solutionArchitectProjectSetJoin = solutionArchitectRoot.joinSet("projects");
        criteriaQuery.select(solutionArchitectProjectSetJoin).where(criteriaBuilder.equal(solutionArchitectRoot.get("employee").get("id"), consultantId));

        List<Project> solutionArchitectProjects = entityManager.createQuery(criteriaQuery).getResultList();
        List<Project> finalList = new ArrayList<>();

        finalList.addAll(projectManagerProjects);
        finalList.addAll(solutionArchitectProjects);

        finalList = finalList.stream().distinct().collect(Collectors.toList());
        finalList.forEach(project -> System.out.println(project.getId()));
        return finalList;
    }

    private Predicate prepareSupervisorOfConsultantPredicate(Root<Project> projectRoot, CriteriaBuilder criteriaBuilder, String projectRoleName, Long supervisorId) {
        Join<Project, ProjectRole> projectRoleJoin = projectRoot.join(projectRoleName);
        Join<ProjectRole, Employee> employeeJoin = projectRoleJoin.join("employee");
        Join<ProjectRole, Consultant> consultantJoin = criteriaBuilder.treat(employeeJoin, Consultant.class);
        return criteriaBuilder.equal(consultantJoin.get("supervisor").get("id"), supervisorId);
    }
}






