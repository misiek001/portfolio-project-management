package com.mbor.controller;

import com.mbor.model.*;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.service.IEmployeeService;
import com.mbor.service.IProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/projects")
public class ProjectController extends RawController {

    private final IProjectService projectService;

    private final IEmployeeService employeeService;

    public ProjectController(IProjectService projectService, IEmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    private static Random random = new Random();

    private static ProjectCreationDTO projectCreationDTO;
    private static BusinessRelationManagerDTO businessRelationManagerDTO;
    private static BusinessEmployeeDTO businessEmployeeDTO;
    private static BusinessLeaderDTO businessLeaderDTO;
    private static BusinessUnitDTO businessUnitDTOFirst;
    private static BusinessUnitDTO businessUnitDTOSecond;

    @PostMapping
    public ResponseEntity<ProjectCreatedDTO> save(@RequestBody ProjectCreationDTO projectCreationDTO){
        ProjectCreatedDTO projectCreatedDTO = (ProjectCreatedDTO) projectService.save(projectCreationDTO).get();
        return new ResponseEntity<>(projectCreatedDTO, HttpStatus.OK);
    }

    public  ResponseEntity<List<ProjectDTO>> findAll() {
        return super.findAll();
    }

    public ResponseEntity<ProjectDTO> find(Long id) {
        return super.find(id);
    }

    public ResponseEntity<Void> delete(Long id) {
        return super.delete(id);
    }

    @PostMapping("/test")
    public ResponseEntity<ProjectCreatedDTO> saveTest(@RequestBody ProjectCreationDTO projectCreationDTO){
        ProjectCreatedDTO projectCreatedDTO = (ProjectCreatedDTO) projectService.save(projectCreationDTO).get();
        return new ResponseEntity<>(projectCreatedDTO, HttpStatus.OK);
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
    public IProjectService getService() {
        return projectService;
    }

}
