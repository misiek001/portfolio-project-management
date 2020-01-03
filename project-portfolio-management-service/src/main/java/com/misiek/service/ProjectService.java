package com.misiek.service;

import com.misiek.dao.IDao;
import com.misiek.dao.ProjectDao;
import com.misiek.domain.Project;
import com.misiek.mapping.ProjectMapper;
import com.misiek.model.creation.ProjectCreationDTO;
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

    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectService(ProjectDao projectDao, IEmployeeService employeeService, IBusinessUnitService businessUnitService, ProjectMapper projectMapper) {
        this.projectDao = projectDao;
        this.employeeService = employeeService;
        this.businessUnitService = businessUnitService;
        this.projectMapper = projectMapper;
    }

    public Optional<Project> save(ProjectCreationDTO projectCreationDTO) {
        Project project = projectMapper.mapProjectCreationDTOtoProject(projectCreationDTO);
        return super.save(project);
    }

    @Override
    public IDao getDao() {
        return projectDao;
    }
}

