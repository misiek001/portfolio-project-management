package com.mbor.controller;

import com.mbor.model.BusinessEmployeeDTO;
import com.mbor.model.BusinessLeaderDTO;
import com.mbor.model.BusinessRelationManagerDTO;
import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.creation.*;
import com.mbor.service.IEmployeeService;
import com.mbor.service.IProjectService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/test/")
@Profile("!prod")
public class TestController {

    private static Random random = new Random();

    private final IProjectService projectService;

    private final IEmployeeService employeeService;

    public TestController(IProjectService projectService, IEmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @PostMapping("/saveProject")
    public ResponseEntity<ProjectCreatedDTO> saveTest(@RequestBody ProjectCreationDTO projectCreationDTO){
        ProjectCreatedDTO projectCreatedDTO =  projectService.save(projectCreationDTO);
        return new ResponseEntity<>(projectCreatedDTO, HttpStatus.OK);
    }

    @GetMapping(params = {"creationDto=project"})
    public ResponseEntity<ProjectCreationDTO> test(){
        ProjectCreationDTO projectCreationDTO = new ProjectCreationDTO();
        projectCreationDTO.setProjectName("Project Name");

        BusinessRelationManagerDTO businessRelationManagerDTO = new BusinessRelationManagerDTO();
        businessRelationManagerDTO.setId(random.nextLong());

        projectCreationDTO.setBusinessRelationManager(businessRelationManagerDTO);

        BusinessLeaderDTO businessLeaderDTO = new BusinessLeaderDTO();
        BusinessEmployeeDTO businessEmployeeDTO = new BusinessEmployeeDTO();
        businessEmployeeDTO.setId(random.nextLong());

        businessLeaderDTO.setEmployee(businessEmployeeDTO);
        projectCreationDTO.setBusinessLeader(businessLeaderDTO);

        BusinessUnitDTO businessUnitDTOFirst = new BusinessUnitDTO();
        businessUnitDTOFirst.setId(random.nextLong());

        projectCreationDTO.addBusinessUnit(businessUnitDTOFirst);

        BusinessUnitDTO businessUnitDTOSecond = new BusinessUnitDTO();
        businessUnitDTOSecond.setId(random.nextLong());

        projectCreationDTO.addBusinessUnit(businessUnitDTOSecond);

        return new ResponseEntity<>(projectCreationDTO, HttpStatus.OK);
    }

    @GetMapping(params = {"creationDto=employee"})
    public ResponseEntity<EmployeeCreationDTO> getEmployee(){
        EmployeeCreationDTO employeeCreationDTO = new EmployeeCreationDTO();
        employeeCreationDTO.setFirstName("First Name");
        employeeCreationDTO.setLastName("Last Name");
        employeeCreationDTO.setEmployeeType(EmployeeType.BusinessEmployee);
        employeeCreationDTO.setBusinessUnitId(1l);

        return new ResponseEntity<>(employeeCreationDTO, HttpStatus.OK);

    }

    @GetMapping(params = {"creationDto=businessUnit"})
    public ResponseEntity<BusinessUnitCreationDTO> getBusinessUnit(){
        BusinessUnitCreationDTO businessUnitCreationDTO = new BusinessUnitCreationDTO();
        businessUnitCreationDTO.setName("BusinessUnitName");

        return new ResponseEntity<>(businessUnitCreationDTO, HttpStatus.OK);

    }

}
