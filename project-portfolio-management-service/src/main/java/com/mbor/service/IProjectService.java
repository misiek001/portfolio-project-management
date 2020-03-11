package com.mbor.service;

import com.mbor.model.ProjectDTO;
import com.mbor.model.assignment.EmployeeAssignDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.model.search.SearchProjectDTO;

import java.util.List;

public interface IProjectService<T> extends IService<T> {

     ProjectCreatedDTO save(ProjectCreationDTO projectCreationDTO);

     List<ProjectDTO> findByMultipleCriteria(SearchProjectDTO searchProjectDTO);

     ProjectDTO assignEmployee(EmployeeAssignDTO employeeAssignDTO);
}
