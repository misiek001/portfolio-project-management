package com.mbor.dao;

import com.mbor.domain.Project;
import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

public interface IProjectDao extends IDao<Project> {

     List<Project> findByMultipleCriteria(String projectName, List<ProjectClass> projectClassList, String businessUnitName, List<ProjectStatus> projectStatusList, LocalDate projectStartDate);

     List<Project> findResourceManagerProjects(Long resourceManagerId, Long projectId, String projectName, List<ProjectClass> projectClass, List<ProjectStatus> projectStatusList);

     List<Project> findSupervisorProjects(Long supervisorId, Long projectId, String projectName, List<ProjectClass> projectClass, List<ProjectStatus> projectStatusList, List<Long> projectManagerIdList, List<Long> solutionArchitectsIdList);

    List<Project> findConsultantProject(Long consultantId);
}
