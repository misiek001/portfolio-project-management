package com.mbor.mapper;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.model.ProjectDTO;
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

import static com.mbor.mapper.MapperUtils.*;
import static com.sun.corba.se.impl.util.Version.PROJECT_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
class ProjectMapperTest {



    private static ProjectCreationDTO projectCreationDTO;

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


        businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setId(BRM_ID);
        businessRelationManager.setUserName(BRM_USER_NAME);
        businessRelationManager.setBusinessUnit(ITBusinessUnit);
        businessRelationManager.setDirector(director);

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