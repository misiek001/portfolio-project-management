package com.mbor.controller;

import com.mbor.model.*;
import com.mbor.model.assignment.EmployeeAssignDTO;
import com.mbor.model.creation.*;
import com.mbor.model.search.SearchProjectDTO;
import com.mbor.service.IEmployeeService;
import com.mbor.service.IProjectService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/test")
@Profile("!prod")
public class TestController {

    private static Random random = new Random();

    private final IProjectService projectService;

    private final IEmployeeService employeeService;

    public TestController(IProjectService projectService, IEmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String helloWorld(){
        return "Hello, World!";
    }

    @PostMapping("/saveProject")
    public ResponseEntity<ProjectCreatedDTO> saveTest(@RequestBody ProjectCreationDTO projectCreationDTO){
        ProjectCreatedDTO projectCreatedDTO =  projectService.save(projectCreationDTO);
        return new ResponseEntity<>(projectCreatedDTO, HttpStatus.OK);
    }

    @GetMapping(params = {"creationDto=project"})
    public ResponseEntity<ProjectCreationDTO> test(HttpServletResponse response) throws IOException {
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

    @GetMapping(params = {"prepareDto=searchProject"})
    public ResponseEntity<SearchProjectDTO> getSearchProjectDto(){
        SearchProjectDTO searchProjectDTO = new SearchProjectDTO();
        searchProjectDTO.setBusinessUnitName("Business");
        searchProjectDTO.setProjectClassDTOList(Arrays.asList(ProjectClassDTO.I, ProjectClassDTO.II));
        searchProjectDTO.setProjectName("Project");
        searchProjectDTO.setProjectStatusDTOList(Arrays.asList(ProjectStatusDTO.ANALYSIS, ProjectStatusDTO.IN_PROGRESS));
        return new ResponseEntity<>(searchProjectDTO, HttpStatus.OK);
    }

    @GetMapping(params = {"prepareDto=employeeAssign"})
    public ResponseEntity<EmployeeAssignDTO> getEmployeeAssignDto(){
        EmployeeAssignDTO employeeAssignDTO = new EmployeeAssignDTO();

        ConsultantDTO consultantDTO = new ConsultantDTO();
        consultantDTO.setId(4l);
        ProjectManagerDTO projectManagerDTO = new ProjectManagerDTO();
        projectManagerDTO.setEmployee(consultantDTO);

        employeeAssignDTO.setProjectManagerDTO(projectManagerDTO);

        BusinessRelationManagerDTO businessRelationManagerDTO = new BusinessRelationManagerDTO();
        businessRelationManagerDTO.setId(3l);
        employeeAssignDTO.setBusinessRelationManagerDTO(businessRelationManagerDTO);

        SupervisorDTO supervisorDTO = new SupervisorDTO();
        supervisorDTO.setId(2l);
        ResourceManagerDTO resourceManagerDTO = new ResourceManagerDTO();
        resourceManagerDTO.setEmployee(supervisorDTO);
        employeeAssignDTO.setResourceManagerDTO(resourceManagerDTO);

        Set<SolutionArchitectDTO> solutionArchitectDTOS = new HashSet<>();

        SolutionArchitectDTO firstSolutionArchitect = new SolutionArchitectDTO();
        firstSolutionArchitect.setEmployee(consultantDTO);
        solutionArchitectDTOS.add(firstSolutionArchitect);
        SolutionArchitectDTO secondSolutionArchitect  = new SolutionArchitectDTO();
        secondSolutionArchitect.setEmployee(supervisorDTO);
        solutionArchitectDTOS.add(secondSolutionArchitect);
        employeeAssignDTO.setSolutionArchitectDTOS(solutionArchitectDTOS);

        BusinessLeaderDTO businessLeaderDTO = new BusinessLeaderDTO();
        BusinessEmployeeDTO businessEmployeeDTO = new BusinessEmployeeDTO();
        businessEmployeeDTO.setId(5l);

        businessLeaderDTO.setEmployee(businessEmployeeDTO);
        employeeAssignDTO.setBusinessLeaderDTO(businessLeaderDTO);

        return new ResponseEntity<>(employeeAssignDTO, HttpStatus.OK);


    }

}
