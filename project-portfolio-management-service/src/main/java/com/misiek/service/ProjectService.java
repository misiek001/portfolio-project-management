package com.misiek.service;

import com.misiek.dao.IDao;
import com.misiek.dao.ProjectDao;
import com.misiek.domain.BusinessRelationManager;
import com.misiek.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ProjectService extends RawService<Project> implements IProjectService<Project> {

    private final ProjectDao projectDao;

    private final IEmployeeService employeeService;

    private final IBusinessUnitService businessUnitService;

    @Autowired
    public ProjectService(ProjectDao projectDao, IEmployeeService employeeService, IBusinessUnitService businessUnitService) {
        this.projectDao = projectDao;
        this.employeeService = employeeService;
        this.businessUnitService = businessUnitService;
    }

    @Override
    public Optional<Project> save(Project project) {
        project.setBusinessRelationManager((BusinessRelationManager) employeeService.find(project.getBusinessRelationManager().getId()).get());
        project.setProjectManager(null);
        //project.getBusinessUnits().forEach(businessUnit  -> project.addBusinessUnit((BusinessUnit) businessUnitService.find(businessUnit.getId()).get()));
        return super.save(project);
    }

    @Override
    public IDao getDao() {
        return projectDao;
    }
}
