package com.misiek.service;

import com.misiek.dao.IDao;
import com.misiek.dao.ProjectDao;
import com.misiek.domain.BusinessEmployee;
import com.misiek.domain.BusinessRelationManager;
import com.misiek.domain.BusinessUnit;
import com.misiek.domain.Project;
import com.misiek.domain.employeeinproject.BusinessLeader;
import com.misiek.mapping.ProjectMapper;
import com.misiek.model.creation.ProjectCreatedDTO;
import com.misiek.model.creation.ProjectCreationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ProjectService extends RawService<Project>  implements IProjectService<Project> {

    private final ProjectDao projectDao;

    private final IEmployeeService employeeService;

    private final IBusinessUnitService businessUnitService;

    private final IProjectRoleService projectRoleService;

    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectService(ProjectDao projectDao, IEmployeeService employeeService, IBusinessUnitService businessUnitService, IProjectRoleService projectRoleService, ProjectMapper projectMapper) {
        this.projectDao = projectDao;
        this.employeeService = employeeService;
        this.businessUnitService = businessUnitService;
        this.projectRoleService = projectRoleService;
        this.projectMapper = projectMapper;
    }

    @Override
    public  Optional<ProjectCreatedDTO> save(ProjectCreationDTO projectCreationDTO) {
        Project project = projectMapper.mapProjectCreationDTOtoProject(projectCreationDTO);
        BusinessLeader businessLeader;
        if(project.getBusinessLeader().getId() == null){
            businessLeader = new BusinessLeader();
            BusinessEmployee businessEmployee = (BusinessEmployee) employeeService.find(project.getBusinessLeader().getEmployee().getId()).get();
            businessLeader.setEmployee(businessEmployee);
        } else {
            businessLeader = (BusinessLeader) projectRoleService.find(project.getBusinessLeader().getId()).get();
        }
        project.setBusinessLeader(businessLeader);
        project.setBusinessRelationManager((BusinessRelationManager) employeeService.find(project.getBusinessRelationManager().getId()).get());

        Set<BusinessUnit> businessUnitSet = project.getBusinessUnits();
        project.setBusinessUnits(null);
        businessUnitSet.forEach(businessUnit -> project.addBusinessUnit((BusinessUnit) businessUnitService.find(businessUnit.getId()).get()));
        Optional<Project> savedProject =  super.saveInternal(project);
        ProjectCreatedDTO  projectCreatedDTO = projectMapper.mapCreatedProjectToDTO(savedProject.get());
        return Optional.ofNullable(projectCreatedDTO);
    }

    @Override
    public IDao getDao() {
        return projectDao;
    }
}

