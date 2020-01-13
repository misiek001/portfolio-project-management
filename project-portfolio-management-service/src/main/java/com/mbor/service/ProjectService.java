package com.mbor.service;

import com.mbor.dao.IDao;
import com.mbor.dao.ProjectDao;
import com.mbor.domain.BusinessEmployee;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.Project;
import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.mapping.ProjectMapper;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    public  ProjectCreatedDTO save(ProjectCreationDTO projectCreationDTO) {
        Project project = projectMapper.convertCreationDtoToEntity(projectCreationDTO);
        BusinessLeader businessLeader;
        if(project.getBusinessLeader().getId() == null){
            businessLeader = new BusinessLeader();
            BusinessEmployee businessEmployee = (BusinessEmployee) employeeService.find(project.getBusinessLeader().getEmployee().getId());
            businessLeader.setEmployee(businessEmployee);
            projectRoleService.saveInternal(businessLeader);
        } else {
            businessLeader = (BusinessLeader) projectRoleService.find(project.getBusinessLeader().getId());
        }
        project.setBusinessLeader(businessLeader);
        project.setBusinessRelationManager((BusinessRelationManager) employeeService.find(project.getBusinessRelationManager().getId()));
        project.getBusinessRelationManager().getProjects().forEach(project1 -> System.out.println(project1.getProjectName()));

        Set<BusinessUnit> businessUnitSet = new HashSet<>();
        businessUnitSet.forEach(businessUnit -> project.addBusinessUnit((BusinessUnit) businessUnitService.find(businessUnit.getId())));
        Project savedProject =  super.saveInternal(project);
        return projectMapper.convertEntityToCreatedDto(savedProject);
    }

    @Override
    public IDao getDao() {
        return projectDao;
    }
}
