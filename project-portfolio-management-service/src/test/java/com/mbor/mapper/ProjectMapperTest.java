package com.mbor.mapper;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.model.*;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
class ProjectMapperTest {

    private static final long PROJECT_ID = 1L;
    private static final String PROJECT_NAME = "Project Name";
    private static final ProjectClassDTO DTO_PROJECT_CLASS = ProjectClassDTO.I;
    private static final ProjectClass PROJECT_CLASS = ProjectClass.I;
    private static final long IT_BUSINESS_UNIT_ID = 1L;
    private static final long FIRST_BUSINESS_UNIT_ID = 2L;
    private static final long SECOND_BUSINESS_UNIT_ID = 3L;
    private static final String IT_BUSINESS_UNIT_NAME = "IT Business Unit";
    private static final String FIRST_BUSINESS_UNIT_NAME = "First Business Unit";
    private static final String SECOND_BUSINESS_UNIT_NAME = "Second Business Unit";
    private static final Long DIRECTOR_ID = 1L;
    private static final Long BRM_ID = 2L;
    private static final Long SUPERVISOR_ID = 3L;
    private static final Long CONSULTANT_ID = 4L;
    private static final Long BUSINESS_EMPLOYEE_ID = 5L;
    private static final Long RESOURCE_MANAGER_ID = 1L;
    private static final Long PROJECT_MANAGER_ID = 2L;
    private static final Long BUSINESS_LEADER_ID = 3L;

    private static final String DIRECTOR_USER_NAME = "Director Username";
    private static final String BRM_USER_NAME = "BRM Username";
    private static final String SUPERVISOR_USER_NAME = "Supervisor Username";
    private static final String CONSULTANT_USER_NAME = "Consultant Username";
    private static final String BUSINESS_EMPLOYEE_USERNAME = "BU Username";
    private static final String FIRST_REASON = "First Reason";
    private static final String SECOND_REASON = "Second Reason";
    private static Random random = new Random();
    private static ProjectCreationDTO projectCreationDTO;
    private static DirectorDTO directorDTO;
    private static BusinessRelationManagerDTO businessRelationManagerDTO;
    private static BusinessEmployeeDTO businessEmployeeDTO;
    private static BusinessLeaderDTO businessLeaderDTO;
    private static BusinessUnitDTO businessUnitDTOFirst;
    private static BusinessUnitDTO businessUnitDTOSecond;

    private static Project expectedProject;
    private static BusinessUnit ITBusinessUnit;
    private static Director director;
    private static BusinessRelationManager businessRelationManager;
    private static Supervisor supervisor;
    private static ResourceManager resourceManager;
    private static Consultant firstConsultant;
    private static ProjectManager projectManager;
    private static BusinessEmployee businessEmployee;
    private static BusinessLeader businessLeader;
    private static BusinessUnit businessUnitFirst;
    private static BusinessUnit businessUnitSecond;
    private static RealEndDate firstRealEndDate;
    private static RealEndDate secondRealEndDate;

    private static ProjectCreatedDTO expectedProjectCreatedDTO;

    @Autowired
    ProjectMapper projectMapper;

    @Mock
    private BusinessLeaderMapper businessLeaderMapper;
    @Mock
    private BusinessEmployeeMapper businessEmployeeMapper;
    @Mock
    private BusinessRelationManagerMapper businessRelationManagerMapper;

    @BeforeAll
    static void setUp() {
        //ProjectCreationDTO
        projectCreationDTO = new ProjectCreationDTO();
        projectCreationDTO.setProjectName(PROJECT_NAME);
        projectCreationDTO.setProjectClass(DTO_PROJECT_CLASS);

        //Expected Project from mapping CreationDTO

        expectedProject = new Project();
        expectedProject.setId(PROJECT_ID);
        expectedProject.setProjectName(projectCreationDTO.getProjectName());
        expectedProject.setProjectClass(PROJECT_CLASS);
        expectedProject.addProjectStatusHistoryLine(MapperUtils.prepareProjectStatusHistoryLine());

        ITBusinessUnit = new BusinessUnit();
        ITBusinessUnit.setId(IT_BUSINESS_UNIT_ID);
        ITBusinessUnit.setName(IT_BUSINESS_UNIT_NAME);

        director = new Director();
        director.setId(DIRECTOR_ID);
        director.setUserName(DIRECTOR_USER_NAME);
        director.setBusinessUnit(ITBusinessUnit);
        ITBusinessUnit.getEmployees().add(director);

        businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setId(BRM_ID);
        businessRelationManager.setUserName(BRM_USER_NAME);
        businessRelationManager.setBusinessUnit(ITBusinessUnit);
        director.getBusinessRelationManagers().add(businessRelationManager);
        businessRelationManager.setDirector(director);
        ITBusinessUnit.getEmployees().add(businessRelationManager);

        expectedProject.setBusinessRelationManager(businessRelationManager);

        supervisor = new Supervisor();
        supervisor.setId(SUPERVISOR_ID);
        supervisor.setUserName(SUPERVISOR_USER_NAME);
        supervisor.setBusinessUnit(ITBusinessUnit);
        supervisor.setDirector(director);
        director.getSupervisors().add(supervisor);
        ITBusinessUnit.getEmployees().add(supervisor);
        resourceManager = new ResourceManager();
        resourceManager.setId(RESOURCE_MANAGER_ID);
        resourceManager.setEmployee(supervisor);
        supervisor.addProjectRole(resourceManager);
        expectedProject.setResourceManager(resourceManager);

        firstConsultant = new Consultant();
        firstConsultant.setId(CONSULTANT_ID);
        firstConsultant.setUserName(CONSULTANT_USER_NAME);
        firstConsultant.setSupervisor(supervisor);
        firstConsultant.setBusinessUnit(ITBusinessUnit);
        ITBusinessUnit.getEmployees().add(firstConsultant);

        projectManager = new ProjectManager();
        projectManager.setId(PROJECT_MANAGER_ID);
        projectManager.setEmployee(firstConsultant);
        firstConsultant.addProjectRole(projectManager);
        projectManager.getProjects().add(expectedProject);
        expectedProject.setProjectManager(projectManager);

        businessEmployee = new BusinessEmployee();
        businessEmployee.setId(BUSINESS_EMPLOYEE_ID);
        businessEmployee.setUserName(BUSINESS_EMPLOYEE_USERNAME);

        businessLeader = new BusinessLeader();
        businessLeader.setId(BUSINESS_LEADER_ID);
        businessLeader.setEmployee(businessEmployee);
        businessEmployee.addProjectRole(businessLeader);
        expectedProject.setBusinessLeader(businessLeader);

        businessUnitFirst = new BusinessUnit();
        businessUnitFirst.setId(FIRST_BUSINESS_UNIT_ID);
        businessUnitFirst.setName(FIRST_BUSINESS_UNIT_NAME);
        businessUnitFirst.getEmployees().add(businessEmployee);
        businessEmployee.addProjectRole(businessLeader);

        businessUnitSecond = new BusinessUnit();
        businessUnitSecond.setId(SECOND_BUSINESS_UNIT_ID);
        businessUnitSecond.setName(SECOND_BUSINESS_UNIT_NAME);

        expectedProject.setPrimaryBusinessUnit(businessUnitFirst);
        expectedProject.addSecondaryBusinessUnit(businessUnitSecond);

        firstRealEndDate = new RealEndDate();
        firstRealEndDate.setEndDate(LocalDateTime.now().plusDays(10));
        firstRealEndDate.setReason(FIRST_REASON);

        secondRealEndDate = new RealEndDate();
        secondRealEndDate.setEndDate(LocalDateTime.now().plusDays(20));
        secondRealEndDate.setReason(SECOND_REASON);

        expectedProject.addRealEndDate(firstRealEndDate);
        expectedProject.addRealEndDate(secondRealEndDate);

        //Creating expected ProjectCreatedDTO
        expectedProjectCreatedDTO = new ProjectCreatedDTO();
        expectedProjectCreatedDTO.setId(expectedProject.getId());
        expectedProjectCreatedDTO.setProjectName(expectedProject.getProjectName());
        expectedProjectCreatedDTO.setBusinessLeader(businessLeaderDTO);
        expectedProjectCreatedDTO.setBusinessRelationManager(businessRelationManagerDTO);
        expectedProjectCreatedDTO.setPrimaryBusinessUnit(businessUnitDTOFirst);
    }

    @Test
    void mapProjectCreationDTOtoProject() {
        Project mappedProject = projectMapper.convertCreationDtoToEntity(projectCreationDTO);
        assertEquals(mappedProject.getProjectName(), expectedProject.getProjectName());
        assertEquals(mappedProject.getProjectClass(), expectedProject.getProjectClass());
    }

    @Test
    void mapCreatedProjectToDTO() {
        ProjectCreatedDTO projectCreatedDTO = projectMapper.convertEntityToCreatedDto(expectedProject);

        assertEquals(expectedProject.getId(), projectCreatedDTO.getId());
        assertEquals(expectedProject.getProjectName(), projectCreatedDTO.getProjectName());
        assertEquals(expectedProject.getBusinessRelationManager().getId(), projectCreatedDTO.getBusinessRelationManager().getId());
        assertEquals(expectedProject.getBusinessLeader().getEmployee().getId(), projectCreatedDTO.getBusinessLeader().getEmployee().getId());
        assertEquals(expectedProject.getProjectStatusHistoryLines().size(), projectCreatedDTO.getProjectStatusHistoryLines().size());
    }

    @Test
    void convertToDto() {
        ProjectDTO result = projectMapper.convertToDto(expectedProject);

        assertEquals(expectedProject.getId(), result.getId());
        assertEquals(expectedProject.getProjectName(), result.getProjectName());
        assertEquals(expectedProject.getProjectClass().name(), result.getProjectClass().name());
        assertEquals(expectedProject.getResourceManager().getId(), result.getResourceManager().getId());
        assertEquals(expectedProject.getResourceManager().getEmployee().getId(), result.getResourceManager().getEmployee().getId());
        assertEquals(expectedProject.getResourceManager().getEmployee().getUserName(), result.getResourceManager().getEmployee().getUserName());
        assertEquals(0, result.getResourceManager().getEmployee().getBusinessUnit().getEmployees().size());
        assertEquals(expectedProject.getProjectManager().getId(), result.getProjectManager().getId());
        assertEquals(expectedProject.getProjectManager().getEmployee().getId(), result.getProjectManager().getEmployee().getId());
        assertEquals(expectedProject.getProjectManager().getEmployee().getUserName(), result.getProjectManager().getEmployee().getUserName());
        assertEquals(expectedProject.getBusinessRelationManager().getId(), result.getBusinessRelationManager().getId());
        assertEquals(expectedProject.getBusinessRelationManager().getUserName(), result.getBusinessRelationManager().getUserName());
        assertEquals(expectedProject.getProjectStatusHistoryLines().size(), result.getProjectStatusHistoryLines().size());
        assertEquals(expectedProject.getPrimaryBusinessUnit().getId(), result.getPrimaryBusinessUnit().getId());
        assertEquals(expectedProject.getPrimaryBusinessUnit().getName(), result.getPrimaryBusinessUnit().getName());
        assertEquals(0, result.getPrimaryBusinessUnit().getPrimaryProjects().size());
        assertEquals(0, result.getPrimaryBusinessUnit().getSecondaryProjects().size());

    }

    @Test
    void convertToEntity() {
    }


}