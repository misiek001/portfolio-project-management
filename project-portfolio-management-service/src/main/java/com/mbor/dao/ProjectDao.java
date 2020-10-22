package com.mbor.dao;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ProjectRole;
import com.mbor.domain.employeeinproject.SolutionArchitect;
import com.mbor.domain.search.ResourceManagerSearchProject;
import com.mbor.domain.search.SearchProject;
import com.mbor.domain.search.SupervisorSearchProject;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
    public List<Project> findByMultipleCriteria(SearchProject searchProject) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        Root<Project> project = criteriaQuery.from(Project.class);
        List<Predicate> predicates = new ArrayList<>();
        if (searchProject.getProjectName() != null) {
            predicates.add(criteriaBuilder.like(project.get("projectName"), "%" + searchProject.getProjectName() + "%"));
        }
        if (searchProject.getProjectClassList() != null) {
            CriteriaBuilder.In<ProjectClass> projectClassClause = criteriaBuilder.in(project.get("projectClass"));
            for (ProjectClass projectClass : searchProject.getProjectClassList()) {
                projectClassClause.value(projectClass);
            }
            predicates.add(projectClassClause);
        }
        if (searchProject.getBusinessUnitName() != null) {
            predicates.add(criteriaBuilder.like(project.join("businessUnits").get("name"), "%" + searchProject.getBusinessUnitName() + "%"));
        }
        if (searchProject.getProjectStatusList() != null) {
            CriteriaBuilder.In<ProjectStatus> projectStatusClause = criteriaBuilder.in(project.get("projectStatus"));
            for (ProjectStatus projectStatus : searchProject.getProjectStatusList()) {
                projectStatusClause.value(projectStatus);
            }
            predicates.add(projectStatusClause);
        }
        if (searchProject.getProjectStartDateLaterThat() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(project.get("startDate"), searchProject.getProjectStartDateLaterThat()));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Project> allQuery = entityManager.createQuery(criteriaQuery);
        return allQuery.getResultList();
    }

    @Override
    public List<Project> findResourceManagerProjects(Long resourceManagerId, ResourceManagerSearchProject resourceManagerSearchProject) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        Root<Project> projectRoot = criteriaQuery.from(Project.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(projectRoot.get("resourceManager").get("id"), resourceManagerId));
        if (resourceManagerSearchProject.getProjectId() != null) {
            predicates.add(criteriaBuilder.like(projectRoot.get("id").as(String.class), resourceManagerSearchProject.getProjectId() + "%"));
        }
        if (resourceManagerSearchProject.getProjectName() != null) {
            predicates.add(criteriaBuilder.like(projectRoot.get("projectName"), "%" + resourceManagerSearchProject.getProjectName() + "%"));
        }
        if (resourceManagerSearchProject.getProjectClassList() != null) {
            CriteriaBuilder.In<ProjectClass> projectClassClause = criteriaBuilder.in(projectRoot.get("projectClass"));
            for (ProjectClass projectStatus : resourceManagerSearchProject.getProjectClassList()) {
                projectClassClause.value(projectStatus);
            }
            predicates.add(projectClassClause);
        }
        if (resourceManagerSearchProject.getProjectStatusList()  != null) {

            SetJoin<Project, ProjectStatusHistoryLine> join = projectRoot.joinSet("projectStatusHistoryLines");

            Subquery<LocalDateTime> recentDateOfHistoryLinesQuery = criteriaQuery.subquery(LocalDateTime.class);
            Root<ProjectStatusHistoryLine> recentDateOfHistoryLinesRoot = recentDateOfHistoryLinesQuery.from(ProjectStatusHistoryLine.class);

            recentDateOfHistoryLinesQuery.select(criteriaBuilder.greatest(recentDateOfHistoryLinesRoot.<LocalDateTime>get("creationTime")))
                    .where(criteriaBuilder.equal(recentDateOfHistoryLinesRoot.get("project"), projectRoot.get("id")));

            predicates.add(criteriaBuilder.equal(join.get("creationTime"), recentDateOfHistoryLinesQuery));

            CriteriaBuilder.In<ProjectStatus> projectStatusClause = criteriaBuilder.in(join.get("currentStatus"));
            for(ProjectStatus projectStatus : resourceManagerSearchProject.getProjectStatusList()){
                projectStatusClause.value(ProjectStatus.valueOf(projectStatus.name()));
            }

            predicates.add(projectStatusClause);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Project> allQuery = entityManager.createQuery(criteriaQuery);
        return allQuery.getResultList();
    }

    @Override
    public List<Project> findSupervisorProjects(Long supervisorId, SupervisorSearchProject supervisorSearchProject) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        criteriaQuery.distinct(true);
        Root<Project> projectRoot = criteriaQuery.from(Project.class);
        List<Predicate> predicates = new ArrayList<>();
        if (supervisorSearchProject.getProjectId() != null) {
            predicates.add(criteriaBuilder.like(projectRoot.get("id").as(String.class), supervisorSearchProject.getProjectId()  + "%"));
        }
        if (supervisorSearchProject.getProjectName() != null) {
            predicates.add(criteriaBuilder.like(projectRoot.get("projectName"), "%" + supervisorSearchProject.getProjectName()  + "%"));
        }
        if (supervisorSearchProject.getProjectClassList() != null) {
            predicates.add(projectRoot.get("projectClass").in(supervisorSearchProject.getProjectClassList()));
        }
        if (supervisorSearchProject.getProjectStatusList() != null) {

            SetJoin<Project, ProjectStatusHistoryLine> join = projectRoot.joinSet("projectStatusHistoryLines");

            Subquery<LocalDateTime> recentDateOfHistoryLinesQuery = criteriaQuery.subquery(LocalDateTime.class);
            Root<ProjectStatusHistoryLine> recentDateOfHistoryLinesRoot = recentDateOfHistoryLinesQuery.from(ProjectStatusHistoryLine.class);

            recentDateOfHistoryLinesQuery.select(criteriaBuilder.greatest(recentDateOfHistoryLinesRoot.<LocalDateTime>get("creationTime")))
                    .where(criteriaBuilder.equal(recentDateOfHistoryLinesRoot.get("project"), projectRoot.get("id")));

            predicates.add(criteriaBuilder.equal(join.get("creationTime"), recentDateOfHistoryLinesQuery));

            CriteriaBuilder.In<ProjectStatus> projectStatusClause = criteriaBuilder.in(join.get("currentStatus"));
            for(ProjectStatus projectStatus : supervisorSearchProject.getProjectStatusList()){
                projectStatusClause.value(ProjectStatus.valueOf(projectStatus.name()));
            }
            predicates.add(projectStatusClause);
        }
        if (supervisorSearchProject.getProjectManagerIdList()!= null && supervisorSearchProject.getSolutionArchitectIdList() != null) {
            Predicate projectManagerListPredicate = projectRoot.get("projectManager").get("id").in(supervisorSearchProject.getSolutionArchitectIdList());
            Predicate supervisorOfProjectManagerPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "projectManager", supervisorId);
            Predicate projectManagerPredicate = criteriaBuilder.and(projectManagerListPredicate, supervisorOfProjectManagerPredicate);

            Predicate solutionArchitectListPredicate = projectRoot.join("solutionArchitects").get("id").in(supervisorSearchProject.getSolutionArchitectIdList());
            Predicate supervisorOfSolutionArchitectPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "solutionArchitects", supervisorId);
            Predicate solutionArchitectPredicate = criteriaBuilder.and(solutionArchitectListPredicate, supervisorOfSolutionArchitectPredicate);

            predicates.add(criteriaBuilder.or(projectManagerPredicate, solutionArchitectPredicate));
        } else if (supervisorSearchProject.getProjectManagerIdList() != null) {
            Predicate projectManagerListPredicate = projectRoot.get("projectManager").get("id").in(supervisorSearchProject.getProjectManagerIdList());
            Predicate supervisorOfProjectManagerPredicate = prepareSupervisorOfConsultantPredicate(projectRoot, criteriaBuilder, "projectManager", supervisorId);
            predicates.add(criteriaBuilder.and(projectManagerListPredicate, supervisorOfProjectManagerPredicate));
        } else if (supervisorSearchProject.getSolutionArchitectIdList() != null) {
            Predicate solutionArchitectListPredicate = projectRoot.join("solutionArchitects").get("id").in(supervisorSearchProject.getSolutionArchitectIdList());
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
    public List<Project> findConsultantProjects(Long consultantId) {
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






