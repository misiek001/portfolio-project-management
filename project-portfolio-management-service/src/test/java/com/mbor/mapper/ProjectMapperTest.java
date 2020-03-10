package com.mbor.mapper;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.model.*;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.service.IBusinessUnitService;
import com.mbor.service.IEmployeeService;
import com.mbor.service.IProjectRoleService;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
@Transactional
class ProjectMapperTest {
    private static Random random = new Random();

    private static ProjectCreationDTO projectCreationDTO;
    private static BusinessRelationManagerDTO businessRelationManagerDTO;
    private static BusinessEmployeeDTO businessEmployeeDTO;
    private static BusinessLeaderDTO businessLeaderDTO;
    private static BusinessUnitDTO businessUnitDTOFirst;
    private static BusinessUnitDTO businessUnitDTOSecond;

    private static Project expectedProject;
    private static BusinessRelationManager businessRelationManager;
    private static BusinessEmployee businessEmployee;
    private static BusinessLeader businessLeader;
    private static BusinessUnit businessUnitFirst;
    private static BusinessUnit businessUnitSecond;

    private static ProjectCreatedDTO expectedProjectCreatedDTO;

    @InjectMocks
    ProjectMapper projectMapper;

    @Mock
    private BusinessLeaderMapper businessLeaderMapper;
    @Mock
    private BusinessEmployeeMapper businessEmployeeMapper;
    @Mock
    private BusinessRelationManagerMapper businessRelationManagerMapper;
    @Mock
    private  IEmployeeService employeeService;
    @Mock
    private  IBusinessUnitService businessUnitService;
    @Mock
    private  IProjectRoleService projectRoleService;

    @Spy
    private  ModelMapper modelMapper;


    @BeforeAll
    static void setUp() {
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
        businessUnitDTOFirst.setName("First BU");

        projectCreationDTO.addBusinessUnit(businessUnitDTOFirst);

        businessUnitDTOSecond = new BusinessUnitDTO();
        businessUnitDTOSecond.setId(random.nextLong());
        businessUnitDTOSecond.setName("Second BU");

        projectCreationDTO.addBusinessUnit(businessUnitDTOSecond);

        //Creating Expected Project

        expectedProject = new Project();
        expectedProject.setProjectName("Project Name");
        expectedProject.setStatus(ProjectStatus.ANALYSIS);

        businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setId(businessRelationManagerDTO.getId());

        expectedProject.setBusinessRelationManager(businessRelationManager);

        businessLeader = new BusinessLeader();
        businessEmployee = new BusinessEmployee();
        businessEmployee.setId(businessEmployeeDTO.getId());

        businessLeader.setEmployee(businessEmployee);
        expectedProject.setBusinessLeader(businessLeader);

        businessUnitFirst = new BusinessUnit();
        businessUnitFirst.setId(businessUnitDTOFirst.getId());
        businessUnitFirst.setName(businessUnitDTOFirst.getName());

        businessUnitSecond = new BusinessUnit();
        businessUnitSecond.setId(businessUnitDTOSecond.getId());
        businessUnitSecond.setName(businessUnitDTOSecond.getName());

        expectedProject.addBusinessUnit(businessUnitFirst);
        expectedProject.addBusinessUnit(businessUnitSecond);

        //Creating expected ProjectCreatedDTO
        expectedProjectCreatedDTO = new ProjectCreatedDTO();
        expectedProjectCreatedDTO.setId(expectedProject.getId());
        expectedProjectCreatedDTO.setProjectName(expectedProject.getProjectName());
        expectedProjectCreatedDTO.setBusinessLeader(businessLeaderDTO);
        expectedProjectCreatedDTO.setBusinessRelationManager(businessRelationManagerDTO);
        expectedProjectCreatedDTO.getBusinessUnits().add(businessUnitDTOFirst);
        expectedProjectCreatedDTO.getBusinessUnits().add(businessUnitDTOSecond);
        expectedProjectCreatedDTO.setProjectStatus(ProjectStatusDTO.ANALYSIS);
    }

    @Test
    void mapProjectCreationDTOtoProject() {
        doReturn(businessRelationManager, businessEmployee).when(employeeService).find(anyLong());
        if (projectCreationDTO.getBusinessLeader().getId() != null) {
            doReturn(businessLeader).when(projectRoleService).find(anyLong());
        }
        doReturn(businessUnitFirst, businessUnitSecond).when(businessUnitService).find(anyLong());

        Project mappedProject = projectMapper.convertCreationDtoToEntity(projectCreationDTO);

        assertEquals(mappedProject.getProjectName(), expectedProject.getProjectName());
        assertEquals(mappedProject.getBusinessRelationManager().getId(), expectedProject.getBusinessRelationManager().getId());
        assertEquals(mappedProject.getBusinessRelationManager().getFirstName(), expectedProject.getBusinessRelationManager().getFirstName());
        assertEquals(mappedProject.getBusinessRelationManager().getLastName(), expectedProject.getBusinessRelationManager().getLastName());
        assertEquals(mappedProject.getBusinessLeader().getId(), expectedProject.getBusinessLeader().getId());
        assertEquals(mappedProject.getBusinessLeader().getEmployee().getId(), expectedProject.getBusinessLeader().getEmployee().getId());
        assertEquals(mappedProject.getBusinessLeader().getEmployee().getFirstName(), expectedProject.getBusinessLeader().getEmployee().getFirstName());
        assertEquals(mappedProject.getBusinessLeader().getEmployee().getLastName(), expectedProject.getBusinessLeader().getEmployee().getLastName());
        assertEquals(mappedProject.getStatus().name(), expectedProject.getStatus().name());
    }

    @Test
    void mapCreatedProjectToDTO() {
        doReturn(businessLeaderDTO).when(businessLeaderMapper).convertToDto(any(BusinessLeader.class));

        ProjectCreatedDTO projectCreatedDTO = projectMapper.convertEntityToCreatedDto(expectedProject);
        assertEquals(projectCreatedDTO.getId(), expectedProjectCreatedDTO.getId());
        assertEquals(projectCreatedDTO.getProjectName(), expectedProjectCreatedDTO.getProjectName());
        assertEquals(projectCreatedDTO.getBusinessRelationManager().getId(), expectedProjectCreatedDTO.getBusinessRelationManager().getId());
        assertEquals(projectCreatedDTO.getBusinessLeader().getEmployee().getId(), expectedProjectCreatedDTO.getBusinessLeader().getEmployee().getId());
        assertEquals(projectCreatedDTO.getProjectStatus().name(), expectedProjectCreatedDTO.getProjectStatus().name());
    }

    @Test
    void convertToDto() {
    }

    @Test
    void convertToEntity() {
    }
}