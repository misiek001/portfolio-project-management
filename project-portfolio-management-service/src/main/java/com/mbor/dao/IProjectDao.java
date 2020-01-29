package com.mbor.dao;

import com.mbor.domain.Project;
import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

public interface IProjectDao extends IDao<Project> {

     List<Project> findByMultipleCriteria(String projectName, List<ProjectClass> projectClassList, String businessUnitName, List<ProjectStatus> projectStatusList, LocalDate projectStartDate);

}
