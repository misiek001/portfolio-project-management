package com.mbor.dao;

import com.mbor.domain.Project;
import com.mbor.domain.search.ResourceManagerSearchProject;
import com.mbor.domain.search.SearchProject;
import com.mbor.domain.search.SupervisorSearchProject;

import java.util.List;

public interface IProjectDao extends IDao<Project> {

     List<Project> findByMultipleCriteria(SearchProject searchProject);

     List<Project> findResourceManagerProjects(Long resourceManagerId, ResourceManagerSearchProject resourceManagerSearchProject);

     List<Project> findSupervisorProjects(Long supervisorId, SupervisorSearchProject supervisorSearchProject);

    List<Project> findConsultantProject(Long consultantId);
}
