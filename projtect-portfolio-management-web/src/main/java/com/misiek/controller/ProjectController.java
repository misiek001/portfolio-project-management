package com.misiek.controller;

import com.misiek.domain.Project;
import com.misiek.mapping.Mapper;
import com.misiek.mapping.ProjectMapper;
import com.misiek.model.BusinessEmployeeDTO;
import com.misiek.model.BusinessLeaderDTO;
import com.misiek.model.BusinessRelationManagerDTO;
import com.misiek.model.BusinessUnitDTO;
import com.misiek.model.creation.ProjectCreatedDTO;
import com.misiek.model.creation.ProjectCreationDTO;
import com.misiek.service.EmployeeService;
import com.misiek.service.IService;
import com.misiek.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/projects")
public class ProjectController extends RawController<Project> {

   private final ProjectService projectService;

   private final ProjectMapper projectMapper;

   private final EmployeeService employeeService;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper, EmployeeService employeeService) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.employeeService = employeeService;
    }

    private static Random random = new Random();

    private static ProjectCreationDTO projectCreationDTO;
    private static BusinessRelationManagerDTO businessRelationManagerDTO;
    private static BusinessEmployeeDTO businessEmployeeDTO;
    private static BusinessLeaderDTO businessLeaderDTO;
    private static BusinessUnitDTO businessUnitDTOFirst;
    private static BusinessUnitDTO businessUnitDTOSecond;

    @Override
    public IService getService() {
        return projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectCreatedDTO> save(@RequestBody ProjectCreationDTO projectCreationDTO){
        Project projectToSave = projectMapper.mapProjectCreationDTOtoProject(projectCreationDTO);
        Project savedProject = save(projectToSave);
        ProjectCreatedDTO projectCreatedDTO = projectMapper.mapCreatedProjectToDTO(savedProject);
        return new ResponseEntity<>(projectCreatedDTO, HttpStatus.CREATED);
    }

    @PostMapping("/test")
    public ResponseEntity<ProjectCreatedDTO> saveTest(@RequestBody ProjectCreationDTO projectCreationDTO){
        Project projectToSave = projectMapper.mapProjectCreationDTOtoProject(projectCreationDTO);
        Project saveProject = save(projectToSave);
        ProjectCreatedDTO projectCreatedDTO = projectMapper.mapCreatedProjectToDTO(saveProject);
        return new ResponseEntity<>(projectCreatedDTO, HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<ProjectCreationDTO> test(){
        projectCreationDTO = new ProjectCreationDTO();
        projectCreationDTO.setProjectName("Project Name");

        businessRelationManagerDTO = new BusinessRelationManagerDTO();
        businessRelationManagerDTO.setId(random.nextLong());

        projectCreationDTO.setBusinessRelationManager(businessRelationManagerDTO);

        businessLeaderDTO = new BusinessLeaderDTO();
        businessEmployeeDTO = new BusinessEmployeeDTO();
        businessEmployeeDTO.setId(random.nextLong());

        businessLeaderDTO.setEmployee(businessEmployeeDTO);
        projectCreationDTO.setBusinessLeader(businessLeaderDTO);

        businessUnitDTOFirst = new BusinessUnitDTO();
        businessUnitDTOFirst.setId(random.nextLong());

        projectCreationDTO.addBusinessUnit(businessUnitDTOFirst);

        businessUnitDTOSecond = new BusinessUnitDTO();
        businessUnitDTOSecond.setId(random.nextLong());

        projectCreationDTO.addBusinessUnit(businessUnitDTOSecond);

        return new ResponseEntity<>(projectCreationDTO, HttpStatus.OK);
    }

    @Override
    public Mapper getMapper() {
        return projectMapper;
    }
}
