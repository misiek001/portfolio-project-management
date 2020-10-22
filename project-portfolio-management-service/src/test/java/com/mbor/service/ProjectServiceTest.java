package com.mbor.service;

import com.mbor.configuration.ServiceMockConfiguration;
import com.mbor.configuration.TestConfiguration;
import com.mbor.dao.IProjectDao;
import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.domain.employeeinproject.SolutionArchitect;
import com.mbor.domain.projectaspect.*;
import com.mbor.domain.search.ResourceManagerSearchProject;
import com.mbor.domain.search.SearchProject;
import com.mbor.domain.search.SupervisorSearchProject;
import com.mbor.entityFactory.TestObjectFactory;
import com.mbor.mapper.project.ProjectAspectLineMapper;
import com.mbor.mapper.project.ProjectMapper;
import com.mbor.mapper.project.RealEndDateMapper;
import com.mbor.mapper.search.ResourceManagerSearchProjectsMapper;
import com.mbor.mapper.search.SearchProjectsMapper;
import com.mbor.mapper.search.SupervisorSearchProjectMapper;
import com.mbor.model.*;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.model.projectaspect.*;
import com.mbor.model.search.ResourceManagerSearchProjectDTO;
import com.mbor.model.search.SearchProjectDTO;
import com.mbor.model.search.SupervisorSearchProjectDTO;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

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
    ProjectAspectLineMapper projectAspectLineMapper;

    @Autowired
    SearchProjectsMapper searchProjectsMapper;

    @Autowired
    ResourceManagerSearchProjectsMapper resourceManagerSearchProjectsMapper;

    @Autowired
    SupervisorSearchProjectMapper supervisorSearchProjectMapper;

    @Autowired
    RealEndDateMapper realEndDateMapper;

    @Autowired
    TestObjectFactory testObjectsFactory;

    @BeforeAll
    static void init(@Autowired TestObjectFactory testObjectsFactory) {
        prepareTestData(testObjectsFactory);
        entityIdList.add(1L);
        entityIdList.add(2L);
        entityIdList.add(3L);
    }

    @AfterAll
    static void clear() {
        entityIdList.clear();
    }

    private static void prepareTestData(TestObjectFactory testObjectsFactory) {
        firstProject = testObjectsFactory.prepareProject();
        firstProject.setId(1L);
        secondProject = testObjectsFactory.prepareProject();
        secondProject.setId(2L);
        thirdProject = testObjectsFactory.prepareProject();
        thirdProject.setId(3L);
        firstProject.setProjectClass(ProjectClass.I);
        secondProject.setProjectClass(ProjectClass.I);
        thirdProject.setProjectClass(ProjectClass.II);

        firstBusinessUnit = testObjectsFactory.prepareBusinessUnit();
        firstBusinessUnit.setId(1L);

        secondBusinessUnit = testObjectsFactory.prepareBusinessUnit();
        secondBusinessUnit.setId(2L);

        supervisor = testObjectsFactory.prepareSupervisor();
        supervisor.setId(1L);

        firstConsultant = testObjectsFactory.prepareConsultant();
        firstConsultant.setId(2L);
        firstConsultant.setSupervisor(supervisor);

        secondConsultant = testObjectsFactory.prepareConsultant();
        secondConsultant.setId(3L);
        secondConsultant.setSupervisor(supervisor);

        thirdConsultant = testObjectsFactory.prepareConsultant();
        thirdConsultant.setId(4L);
        thirdConsultant.setSupervisor(supervisor);

        businessEmployee = testObjectsFactory.prepareBusinessEmployee();
        businessEmployee.setId(5L);

        resourceManager = new ResourceManager();
        resourceManager.setEmployee(supervisor);
        resourceManager.setId(1L);

        businessLeader = new BusinessLeader();
        businessLeader.setId(7L);
        businessLeader.setEmployee(businessEmployee);

        businessRelationManager = testObjectsFactory.prepareBusinessRelationManager();
        businessRelationManager.setId(6L);

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

        businessRelationManagerDTO = testObjectsFactory.prepareBusinessRelationManagerDTOFromEntity(businessRelationManager);
        openProjectStatusHistoryLine = testObjectsFactory.prepareOpenProjectStatusHistoryLine();

        businessEmployeeDTO = testObjectsFactory.prepareBusinessEmployeeDTOFromEntity(businessEmployee);

        businessLeaderDTO = testObjectsFactory.prepareBusinessLeaderDTOFromEntity(businessLeader);
        businessLeaderDTO.setEmployee(businessEmployeeDTO);

        primaryBusinessUnitDTO = testObjectsFactory.prepareBusinessUnitDTOFromEntity(firstBusinessUnit);
    }

    @AfterEach
    void resetMock() {
        reset(employeeService);
        reset(projectRoleService);
        reset(businessUnitService);
        reset(projectDao);
        reset(projectMapper);
        reset(projectAspectLineMapper);
    }

    void saveThenSuccess() {
        List<Long> secondaryBusinessUnitsId = new ArrayList<>();
        ProjectCreationDTO projectCreationDTO = testObjectsFactory.prepareProjectCreationDTO(firstProject.getProjectName(), ProjectClassDTO.I, businessRelationManager.getId(), businessLeader.getId(), firstBusinessUnit.getId(), secondaryBusinessUnitsId);
        Project projectFromCreationDTO = testObjectsFactory.prepareProjectFromProjectCreationDTO(projectCreationDTO);
        projectFromCreationDTO.setId(1L);
        Optional<Project> optionalProject = Optional.of(projectFromCreationDTO);

        ProjectCreatedDTO projectCreatedDTO = testObjectsFactory.prepareProjectCreatedDTO(projectCreationDTO, projectFromCreationDTO, businessRelationManagerDTO, businessLeaderDTO, primaryBusinessUnitDTO);

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

    @Test
    void findAllThenSuccess() {
        List<Project> projects = new ArrayList<>();
        for (int i = 0; i < createdEntitiesNumber; i++) {
            projects.add(testObjectsFactory.prepareProject());
        }
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        for (int i = 0; i < createdEntitiesNumber; i++) {
            projectDTOs.add(testObjectsFactory.prepareProjectDTOFromEntity(projects.get(i)));
        }

        when(projectDao.findAll()).thenReturn(projects);
        when(projectMapper.convertEntityToDto(any(Project.class))).thenReturn(projectDTOs.get(0), projectDTOs.get(1), projectDTOs.get(2));

        List<ProjectDTO> result = projectService.findAll();
        verify(projectMapper, times(3)).convertEntityToDto(any());
        assertEquals(createdEntitiesNumber, result.size());
        result.forEach(projectDTO -> {
            assertNotNull(projectDTO.getProjectName());
        });
    }

    @Test
    void findThenSuccess() {
        Project project = testObjectsFactory.prepareProject();
        Optional<Project> projectOptional = Optional.of(project);
        ProjectDTO projectDTO = testObjectsFactory.prepareProjectDTOFromEntity(project);

        when(projectDao.find(anyLong())).thenReturn(projectOptional);
        when(projectMapper.convertEntityToDto(any(Project.class))).thenReturn(projectDTO);

        ProjectDTO result = projectService.find(1L);
        assertEquals(project.getProjectName(), result.getProjectName());
    }

    @Test
    void findByMultipleCriteriaThenSuccess() {
        SearchProjectDTO searchProjectDTO = new SearchProjectDTO();
        SearchProject searchProject = new SearchProject();

        List<Project> resultProjects = new ArrayList<>();
        resultProjects.add(testObjectsFactory.prepareProject());
        resultProjects.add(testObjectsFactory.prepareProject());
        resultProjects.add(testObjectsFactory.prepareProject());

        List<ProjectDTO> resultProjectsDTO = new ArrayList<>();
        resultProjectsDTO.add(testObjectsFactory.prepareProjectDTO());
        resultProjectsDTO.add(testObjectsFactory.prepareProjectDTO());
        resultProjectsDTO.add(testObjectsFactory.prepareProjectDTO());

        when(getDao().findByMultipleCriteria(any(SearchProject.class))).thenReturn(resultProjects);
        when(searchProjectsMapper.convertDtoToEntity(searchProjectDTO)).thenReturn(searchProject);
        when(projectMapper.convertEntityToDto(any(Project.class))).thenReturn(resultProjectsDTO.get(0), resultProjectsDTO.get(2), resultProjectsDTO.get(2));

        List<ProjectDTO> result = projectService.findByMultipleCriteria(searchProjectDTO);
        assertEquals(resultProjectsDTO.size(), result.size());
        verify(projectMapper, times(3)).convertEntityToDto(any(Project.class));
    }

    @Test
    void findResourceManagerProjectsTest() {

        ResourceManagerSearchProjectDTO resourceManagerSearchProjectDTO = new ResourceManagerSearchProjectDTO();
        ResourceManagerSearchProject resourceManagerSearchProject = new ResourceManagerSearchProject();

        List<Project> resultProjects = new ArrayList<>();
        resultProjects.add(testObjectsFactory.prepareProject());
        resultProjects.add(testObjectsFactory.prepareProject());
        resultProjects.add(testObjectsFactory.prepareProject());

        List<ProjectDTO> resultProjectsDTO = new ArrayList<>();
        resultProjectsDTO.add(testObjectsFactory.prepareProjectDTO());
        resultProjectsDTO.add(testObjectsFactory.prepareProjectDTO());
        resultProjectsDTO.add(testObjectsFactory.prepareProjectDTO());

        when(getDao().findResourceManagerProjects(anyLong(), any(ResourceManagerSearchProject.class))).thenReturn(resultProjects);
        when(resourceManagerSearchProjectsMapper.convertDtoToEntity(resourceManagerSearchProjectDTO)).thenReturn(resourceManagerSearchProject);
        when(projectMapper.convertEntityToDto(any(Project.class))).thenReturn(resultProjectsDTO.get(0), resultProjectsDTO.get(1), resultProjectsDTO.get(2));

        List<ProjectDTO> result = projectService.findResourceManagerProjects(1L, resourceManagerSearchProjectDTO);

        assertEquals(resultProjectsDTO.size(), result.size());
        verify(resourceManagerSearchProjectsMapper, times(1)).convertDtoToEntity(any(ResourceManagerSearchProjectDTO.class));
        verify(getDao(), times(1)).findResourceManagerProjects(anyLong(), any(ResourceManagerSearchProject.class));
        verify(projectMapper, times(3)).convertEntityToDto(any(Project.class));
    }

    @Test
    void findSupervisorProjectsTest() {
        SupervisorSearchProjectDTO SupervisorSearchProjectDTO = new SupervisorSearchProjectDTO();
        SupervisorSearchProject SupervisorSearchProject = new SupervisorSearchProject();

        List<Project> resultProjects = new ArrayList<>();
        resultProjects.add(testObjectsFactory.prepareProject());
        resultProjects.add(testObjectsFactory.prepareProject());
        resultProjects.add(testObjectsFactory.prepareProject());

        List<ProjectDTO> resultProjectsDTO = new ArrayList<>();
        resultProjectsDTO.add(testObjectsFactory.prepareProjectDTO());
        resultProjectsDTO.add(testObjectsFactory.prepareProjectDTO());
        resultProjectsDTO.add(testObjectsFactory.prepareProjectDTO());

        when(getDao().findSupervisorProjects(anyLong(), any(SupervisorSearchProject.class))).thenReturn(resultProjects);
        when(supervisorSearchProjectMapper.convertDtoToEntity(SupervisorSearchProjectDTO)).thenReturn(SupervisorSearchProject);
        when(projectMapper.convertEntityToDto(any(Project.class))).thenReturn(resultProjectsDTO.get(0), resultProjectsDTO.get(1), resultProjectsDTO.get(2));

        List<ProjectDTO> result = projectService.findSupervisorProjects(1L, SupervisorSearchProjectDTO);
        assertEquals(resultProjectsDTO.size(), result.size());
        verify(supervisorSearchProjectMapper, times(1)).convertDtoToEntity(any(SupervisorSearchProjectDTO.class));
        verify(getDao(), times(1)).findSupervisorProjects(anyLong(), any(SupervisorSearchProject.class));
        verify(projectMapper, times(3)).convertEntityToDto(any(Project.class));
    }

    @Test
    void findConsultantProjectsTest() {
        List<Project> resultProjects = new ArrayList<>();
        resultProjects.add(testObjectsFactory.prepareProject());
        resultProjects.add(testObjectsFactory.prepareProject());
        resultProjects.add(testObjectsFactory.prepareProject());

        List<ProjectDTO> resultProjectsDTO = new ArrayList<>();
        resultProjectsDTO.add(testObjectsFactory.prepareProjectDTO());
        resultProjectsDTO.add(testObjectsFactory.prepareProjectDTO());
        resultProjectsDTO.add(testObjectsFactory.prepareProjectDTO());

        when(getDao().findConsultantProjects(anyLong())).thenReturn(resultProjects);
        when(projectMapper.convertEntityToDto(any(Project.class))).thenReturn(resultProjectsDTO.get(0), resultProjectsDTO.get(1), resultProjectsDTO.get(2));

        List<ProjectDTO> result = projectService.findConsultantProjects(1L);
        assertEquals(resultProjectsDTO.size(), result.size());
        verify(getDao(), times(1)).findConsultantProjects(anyLong());
        verify(projectMapper, times(3)).convertEntityToDto(any(Project.class));
    }

    @Test
    void updateProjectAspectThenSuccess() {
        ProjectAspectLineDTO projectAspectLineDTO = prepareTestProjectAspectLineDTO();
        Project project = testObjectsFactory.prepareProject();
        project.setId(firstProject.getId());
        Optional<Project> projectOptional = Optional.of(project);
        ProjectAspectLine projectAspectLine = prepareTestProjectAspectLine();
        project.addProjectAspectLine(projectAspectLine);
        project.setProjectManager(firstProjectManager);
        ProjectDTO projectDTO = testObjectsFactory.prepareProjectDTOFromEntity(project);
        Set<ProjectAspectLineDTO> projectAspectLines = new HashSet<>();
        projectAspectLines.add(projectAspectLineDTO);
        projectDTO.setProjectAspectLines(projectAspectLines);

        when(projectDao.find(anyLong())).thenReturn(projectOptional);
        when(projectDao.update(project)).thenReturn(projectOptional);
        when(projectAspectLineMapper.convertDtoToEntity(projectAspectLineDTO)).thenReturn(projectAspectLine);
        when(projectMapper.convertEntityToDto(project)).thenReturn(projectDTO);

        ProjectDTO result = projectService.updateProjectAspects(firstProject.getId(), projectAspectLineDTO, firstProjectManager.getId());

        assertEquals(1, result.getProjectAspectLines().size());
    }

    @Test
    void addRealEndDateThenSuccess() {
        RealEndDateDTO realEndDateDTO = prepareRealEndDateDTO(10, "First Reason");
        RealEndDate realEndDate = prepareRealEndDate(10, "First Reason");

        Project project = testObjectsFactory.prepareProject();
        project.setId(firstProject.getId());

        ProjectManager projectManager = testObjectsFactory.prepareProjectManager();
        projectManager.setId(firstProjectManager.getId());

        project.setProjectManager(projectManager);

        Optional<Project> projectOptional = Optional.of(project);

        ProjectDTO projectDTO = testObjectsFactory.prepareProjectDTOFromEntity(project);
        Set<RealEndDateDTO> realEndDateDTOSet = new HashSet<>();
        realEndDateDTOSet.add(realEndDateDTO);
        projectDTO.setRealEndDates(realEndDateDTOSet);

        when(projectDao.find(firstProject.getId())).thenReturn(projectOptional);
        when(projectDao.update(project)).thenReturn(projectOptional);
        when(realEndDateMapper.convertDtoToEntity(realEndDateDTO)).thenReturn(realEndDate);
        when(projectMapper.convertEntityToDto(project)).thenReturn(projectDTO);
        ProjectDTO result = projectService.addProjectEndDate(project.getId(), realEndDateDTO, firstProjectManager.getId());
        assertEquals(1, result.getRealEndDates().size());
    }

    private ProjectAspectLine prepareTestProjectAspectLine() {
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

    private ProjectAspectLineDTO prepareTestProjectAspectLineDTO() {
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

    private RealEndDate prepareRealEndDate(int offset, String reason) {
        RealEndDate realEndDate = new RealEndDate();
        realEndDate.setEndDate(LocalDateTime.now().plusDays(offset));
        realEndDate.setReason(reason);
        return realEndDate;
    }

    @Override
    protected Project createNewEntity() {
        return testObjectsFactory.prepareProject();
    }

    @Override
    protected IProjectDao getDao() {
        return projectDao;
    }

    @Override
    protected IProjectService getService() {
        return projectService;
    }
}
