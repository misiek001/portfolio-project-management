package com.mbor.service;

import com.mbor.configuration.ServiceMockConfiguration;
import com.mbor.configuration.TestConfiguration;
import com.mbor.dao.IDao;
import com.mbor.dao.IProjectDao;
import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.domain.employeeinproject.SolutionArchitect;
import com.mbor.domain.projectaspect.*;
import com.mbor.entityFactory.TestEntityFactory;
import com.mbor.mapper.ProjectMapper;
import com.mbor.model.*;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.model.projectaspect.*;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, ServiceMockConfiguration.class, TestConfiguration.class})
@ActiveProfiles({"test", "project-tests-mock"})
public class ProjectServiceTest extends IServiceTestImpl<Project> {

    private static final int createdEntitiesNumber = 3;

    private static Random random = new Random();

    private static Project firstProject;
    private static Project secondProject;
    private static Project thirdProject;
    private static Supervisor supervisor;
    private static ResourceManager resourceManager;
    private static Consultant firstConsultant;
    private static Consultant secondConsultant;
    private static Consultant thirdConsultant;
    private static ProjectManager firstProjectManager;
    private static ProjectManager secondProjectManager;
    private static SolutionArchitect firstSolutionArchitect;
    private static SolutionArchitect secondSolutionArchitect;
    private static SolutionArchitect thirdSolutionArchitect;
    private static BusinessEmployee businessEmployee;
    private static BusinessRelationManager businessRelationManager;
    private static BusinessUnit firstBusinessUnit;
    private static BusinessUnit secondBusinessUnit;
    private static BusinessLeader businessLeader;
    private static BusinessRelationManagerDTO businessRelationManagerDTO;
    private static ProjectStatusHistoryLineDTO openProjectStatusHistoryLine;
    private static BusinessEmployeeDTO businessEmployeeDTO;
    private static BusinessLeaderDTO businessLeaderDTO;
    private static BusinessUnitDTO primaryBusinessUnitDTO;

    @Autowired
    IProjectService projectService;

    @Autowired
    IEmployeeService employeeService;

    @Autowired
    IProjectRoleService projectRoleService;

    @Autowired
    IBusinessUnitService businessUnitService;

    @Autowired
    IProjectDao projectDao;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    TestEntityFactory testEntityFactory;

    @BeforeAll
    static void init(@Autowired TestEntityFactory testEntityFactory) {
        prepareTestData(testEntityFactory);
    }

    @Test
    void findAllThenSuccess() {

        List<Project> projects = new ArrayList<>();
        for (int i = 0; i < createdEntitiesNumber; i++) {
            projects.add(testEntityFactory.prepareProject());
        }
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        for (int i = 0; i < createdEntitiesNumber; i++) {
            projectDTOs.add(testEntityFactory.prepareProjectDTOFromProject(projects.get(i)));
        }

        when(projectDao.findAll()).thenReturn(projects);
        when(projectMapper.convertToDto(any(Project.class))).thenReturn(projectDTOs.get(0), projectDTOs.get(1), projectDTOs.get(2));

        List<ProjectDTO> result = projectService.findAll();
        verify(projectMapper, times(3)).convertToDto(any());
        assertEquals(createdEntitiesNumber, result.size());
        result.forEach(projectDTO -> {
            assertNotNull(projectDTO.getProjectName());
        });
    }

    @Test
    void findThenSuccess() {
        Project project = testEntityFactory.prepareProject();
        Optional<Project> projectOptional = Optional.of(project);
        ProjectDTO projectDTO = testEntityFactory.prepareProjectDTOFromProject(project);

        when(projectDao.find(anyLong())).thenReturn(projectOptional);
        when(projectMapper.convertToDto(any(Project.class))).thenReturn(projectDTO);

        ProjectDTO result = projectService.find(1L);
        assertEquals(project.getProjectName(), result.getProjectName());
    }

    @Test
    void saveThenSuccess() {
        ProjectCreationDTO projectCreationDTO = prepareProjectCreationDto();
        Project projectFromCreationDTO = prepareProjectFromProjectCreationDTO(projectCreationDTO);
        projectFromCreationDTO.setId(1L);
        Optional<Project> optionalProject = Optional.of(projectFromCreationDTO);

        ProjectCreatedDTO projectCreatedDTO = prepareProjectCreatedDTO(projectCreationDTO, projectFromCreationDTO);

        when(projectMapper.convertCreationDtoToEntity(projectCreationDTO)).thenReturn(projectFromCreationDTO);
        when(getDao().save(any(Project.class))).thenReturn(optionalProject);
        when(employeeService.findInternal(projectCreationDTO.getBusinessRelationManagerId())).thenReturn(businessRelationManager);
        when(projectRoleService.findInternal(projectCreationDTO.getBusinessLeaderId())).thenReturn(businessLeader);
        when(businessUnitService.findInternal(projectCreationDTO.getPrimaryBusinessUnitId())).thenReturn(firstBusinessUnit);
        when(projectMapper.convertEntityToCreatedDto(any(Project.class))).thenReturn(projectCreatedDTO);

        ProjectCreatedDTO result = projectService.save(projectCreationDTO);
        assertNotNull(result);
        assertEquals(result.getId(), projectCreatedDTO.getId());
        assertEquals(result.getProjectStatusHistoryLines().size(), projectCreatedDTO.getProjectStatusHistoryLines().size());
        assertEquals(result.getBusinessRelationManager().getId(), projectCreatedDTO.getBusinessRelationManager().getId());
        assertEquals(result.getBusinessLeader().getId(), projectCreatedDTO.getBusinessLeader().getId());
        assertEquals(result.getBusinessLeader().getEmployee(), projectCreatedDTO.getBusinessLeader().getEmployee());
        assertEquals(result.getPrimaryBusinessUnit().getId(), projectCreatedDTO.getPrimaryBusinessUnit().getId());
    }

    private ProjectCreatedDTO prepareProjectCreatedDTO(ProjectCreationDTO projectCreationDTO, Project projectFromCreationDTO) {
        ProjectCreatedDTO projectCreatedDTO = new ProjectCreatedDTO();
        projectCreatedDTO.setId(projectFromCreationDTO.getId());
        projectCreatedDTO.setProjectName(projectCreationDTO.getProjectName());
        List<ProjectStatusHistoryLineDTO> projectStatusHistoryLines = new ArrayList<>();
        projectStatusHistoryLines.add(openProjectStatusHistoryLine);
        projectCreatedDTO.setProjectStatusHistoryLines(projectStatusHistoryLines);
        projectCreatedDTO.setBusinessRelationManager(businessRelationManagerDTO);
        projectCreatedDTO.setBusinessLeader(businessLeaderDTO);
        projectCreatedDTO.setPrimaryBusinessUnit(primaryBusinessUnitDTO);
        return projectCreatedDTO;
    }

    @Test
    void updateProjectAspectThenSuccess() {
        ProjectAspectLineDTO projectAspectLineDTO = prepareProjectAspectLineDTO();
        projectService.updateProjectAspects(firstProject.getId(), projectAspectLineDTO, firstProjectManager.getId());
        assertEquals(1, projectService.find(firstProject.getId()).getProjectAspectLines().size());
    }

    @Test
    void addRealEndDateThenSuccess() {
        RealEndDateDTO realEndDateDTO = prepareRealEndDateDTO(10, "First Reason");
        projectService.addProjectEndDate(firstProject.getId(), realEndDateDTO, firstProjectManager.getId());
        assertEquals(1, projectService.find(firstProject.getId()).getRealEndDates().size());
    }

    private ProjectCreationDTO prepareProjectCreationDto() {

        ProjectCreationDTO projectCreationDTO = new ProjectCreationDTO();
        projectCreationDTO.setProjectName("ProjectName" + random.nextInt());

        projectCreationDTO.setProjectClass(ProjectClassDTO.I);

        projectCreationDTO.setBusinessRelationManagerId(businessRelationManager.getId());
        projectCreationDTO.setBusinessLeaderId(businessLeader.getId());
        projectCreationDTO.setPrimaryBusinessUnitId(firstBusinessUnit.getId());
        List<Long> secondaryBusinessUnitIds = new ArrayList<>();
        projectCreationDTO.setSecondaryBusinessUnitIds(secondaryBusinessUnitIds);

        return projectCreationDTO;
    }

    private Project prepareProjectFromProjectCreationDTO(ProjectCreationDTO projectCreationDTO) {
        Project project = new Project();
        project.setProjectName(projectCreationDTO.getProjectName());
        project.setProjectClass(ProjectClass.valueOf(projectCreationDTO.getProjectClass().name()));
        return project;
    }

    private ProjectAspectLine prepareProjectAspectLine() {
        ProjectAspectLine projectAspectLine = new ProjectAspectLine();

        BudgetAspect budgetAspect = new BudgetAspect();
        budgetAspect.setAspectStatus(AspectStatus.GREEN);
        budgetAspect.setDescription("Budget Description");
        projectAspectLine.setBudgetAspect(budgetAspect);

        ResourcesAspect resourcesAspect = new ResourcesAspect();
        resourcesAspect.setAspectStatus(AspectStatus.RED);
        resourcesAspect.setDescription("Resources Description");
        projectAspectLine.setResourcesAspect(resourcesAspect);

        ScopeAspect scopeAspect = new ScopeAspect();
        scopeAspect.setAspectStatus(AspectStatus.YELLOW);
        scopeAspect.setDescription("Scope Description");
        projectAspectLine.setScopeAspect(scopeAspect);

        DeadlineAspect deadlineAspect = new DeadlineAspect();
        deadlineAspect.setAspectStatus(AspectStatus.GREEN);
        deadlineAspect.setDescription("Deadline Description");
        projectAspectLine.setDeadlineAspect(deadlineAspect);

        return projectAspectLine;
    }

    private ProjectAspectLineDTO prepareProjectAspectLineDTO() {
        ProjectAspectLineDTO projectAspectLineDTO = new ProjectAspectLineDTO();

        BudgetAspectDTO budgetAspectDTO = new BudgetAspectDTO();
        budgetAspectDTO.setAspectStatus(AspectStatusDTO.GREEN);
        budgetAspectDTO.setDescription("Budget Description");
        projectAspectLineDTO.setBudgetAspect(budgetAspectDTO);

        ResourcesAspectDTO resourcesAspectDTO = new ResourcesAspectDTO();
        resourcesAspectDTO.setAspectStatus(AspectStatusDTO.YELLOW);
        resourcesAspectDTO.setDescription("Resources Description");
        projectAspectLineDTO.setResourcesAspect(resourcesAspectDTO);

        ScopeAspectDTO ScopeAspectDTO = new ScopeAspectDTO();
        ScopeAspectDTO.setAspectStatus(AspectStatusDTO.RED);
        ScopeAspectDTO.setDescription("Scope Aspect");
        projectAspectLineDTO.setScopeAspect(ScopeAspectDTO);

        DeadlineAspectDTO deadlineAspectDTO = new DeadlineAspectDTO();
        deadlineAspectDTO.setAspectStatus(AspectStatusDTO.GREEN);
        deadlineAspectDTO.setDescription("Deadline Description");
        projectAspectLineDTO.setDeadlineAspect(deadlineAspectDTO);

        return projectAspectLineDTO;
    }

    private RealEndDateDTO prepareRealEndDateDTO(int offset, String reason) {
        RealEndDateDTO realEndDateDTO = new RealEndDateDTO();
        realEndDateDTO.setEndDate(LocalDateTime.now().plusDays(offset));
        realEndDateDTO.setReason(reason);
        return realEndDateDTO;
    }

    private static void prepareTestData(TestEntityFactory testEntityFactory) {
        firstProject = testEntityFactory.prepareProject();
        secondProject = testEntityFactory.prepareProject();
        thirdProject = testEntityFactory.prepareProject();

        firstProject.setProjectClass(ProjectClass.I);
        secondProject.setProjectClass(ProjectClass.I);
        thirdProject.setProjectClass(ProjectClass.II);

        firstBusinessUnit = new BusinessUnit();
        firstBusinessUnit.setName("BusinessUnitName" + random.nextLong());
        firstBusinessUnit.setId(1L);

        secondBusinessUnit = new BusinessUnit();
        secondBusinessUnit.setName("BusinessUnitName" + random.nextLong());
        secondBusinessUnit.setId(2L);

        supervisor = new Supervisor();
        secondProject.setId(1L);
        supervisor.setUserName("Supervisor");

        firstConsultant = new Consultant();
        firstConsultant.setId(2L);
        firstConsultant.setUserName("FirstConsultant");
        firstConsultant.setSupervisor(supervisor);

        secondConsultant = new Consultant();
        secondConsultant.setId(3L);
        secondConsultant.setUserName("SecondConsultant");
        secondConsultant.setSupervisor(supervisor);

        thirdConsultant = new Consultant();
        thirdConsultant.setId(4L);
        thirdConsultant.setUserName("ThirdConsultant");

        businessEmployee = new BusinessEmployee();
        businessEmployee.setId(5L);
        businessEmployee.setUserName("UserName" + random.nextLong());

        resourceManager = new ResourceManager();
        resourceManager.setEmployee(supervisor);
        resourceManager.setId(1L);

        businessLeader = new BusinessLeader();
        businessLeader.setId(7L);
        businessLeader.setEmployee(businessEmployee);

        businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setId(6L);
        businessRelationManager.setUserName("BusinessRelationManager" + random.nextLong());

        firstProjectManager = new ProjectManager();
        firstProjectManager.setId(2L);
        firstProjectManager.setEmployee(firstConsultant);
        firstSolutionArchitect = new SolutionArchitect();
        firstSolutionArchitect.setId(3L);
        firstSolutionArchitect.setEmployee(firstConsultant);

        secondProjectManager = new ProjectManager();
        secondProjectManager.setId(4L);
        secondProjectManager.setEmployee(secondConsultant);
        secondSolutionArchitect = new SolutionArchitect();
        secondSolutionArchitect.setId(5L);
        secondSolutionArchitect.setEmployee(secondConsultant);

        thirdSolutionArchitect = new SolutionArchitect();
        thirdSolutionArchitect.setId(6L);
        thirdSolutionArchitect.setEmployee(thirdConsultant);

        firstProject.setResourceManager(resourceManager);
        firstProject.setProjectManager(firstProjectManager);
        firstProject.addSolutionArchitect(firstSolutionArchitect);
        firstProject.addSolutionArchitect(secondSolutionArchitect);

        secondProject.addSolutionArchitect(thirdSolutionArchitect);
        secondProject.setResourceManager(resourceManager);
        secondProject.setProjectManager(secondProjectManager);
        secondProject.addSolutionArchitect(secondSolutionArchitect);

        thirdProject.setProjectManager(firstProjectManager);
        thirdProject.addSolutionArchitect(thirdSolutionArchitect);
        thirdProject.setResourceManager(resourceManager);

         businessRelationManagerDTO = new BusinessRelationManagerDTO();
        businessRelationManagerDTO.setId(businessRelationManager.getId());
        businessRelationManagerDTO.setUserName(businessRelationManager.getUserName());

        openProjectStatusHistoryLine = new ProjectStatusHistoryLineDTO();
        openProjectStatusHistoryLine.setPreviousStatus(ProjectStatusDTO.ANALYSIS);
        openProjectStatusHistoryLine.setCurrentStatus(ProjectStatusDTO.ANALYSIS);
        openProjectStatusHistoryLine.setDescription("Project opened");

         businessEmployeeDTO = new BusinessEmployeeDTO();
        businessEmployeeDTO.setId(businessEmployee.getId());
        businessEmployeeDTO.setUserName(businessEmployee.getUserName());

         businessLeaderDTO = new BusinessLeaderDTO();
        businessLeaderDTO.setId(businessLeader.getId());
        businessLeaderDTO.setEmployee(businessEmployeeDTO);

         primaryBusinessUnitDTO = new BusinessUnitDTO();
        primaryBusinessUnitDTO.setId(firstProject.getId());
        primaryBusinessUnitDTO.setName(firstBusinessUnit.getName());

    }

    @Override
    protected Project createNewEntity() {
        Project project = new Project();
        project.setProjectName("ProjectName" + random.nextLong());
        return project;
    }

    @Override
    protected IDao getDao() {
        return projectDao;
    }

    @Override
    protected IProjectService getService() {
        return projectService;
    }
}
