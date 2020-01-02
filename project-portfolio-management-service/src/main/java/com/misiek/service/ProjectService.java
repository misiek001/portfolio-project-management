package com.misiek.service;

import com.misiek.dao.IDao;
import com.misiek.dao.ProjectDao;
import com.misiek.domain.BusinessRelationManager;
import com.misiek.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService extends RawService<Project> {

    private final ProjectDao projectDao;

    private final EmployeeService employeeService;

    private final BusinessUnitService businessUnitService;

    @Autowired
    public ProjectService(ProjectDao projectDao, EmployeeService employeeService, BusinessUnitService businessUnitService) {
        this.projectDao = projectDao;
        this.employeeService = employeeService;
        this.businessUnitService = businessUnitService;
    }

    @Override
    public Optional<Project> save(Project project) {
        project.setBusinessRelationManager((BusinessRelationManager) employeeService.find(project.getBusinessRelationManager().getId()).get());
        project.setProjectManager(null);
      //  project.getBusinessUnits().forEach(businessUnit  -> project.addBusinessUnit(businessUnitService.find(businessUnit.getId()).get()));
        return super.save(project);
    }

    @Override
    public IDao getDao() {
        return projectDao;
    }
}
