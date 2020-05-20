package com.mbor.service;

import com.mbor.dao.IEmployeeDao;
import com.mbor.dao.IProjectDao;
import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.domain.employeeinproject.SolutionArchitect;
import com.mbor.domain.projectaspect.*;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class ProjectServiceTest extends IServiceTestImpl<Project> {

    private static final int createdEntitiesNumber = 3;

    private static Random random = new Random();

    private static Long businessEmployeeId;
    private static Long businessRelationManagerId;

    private static Long firstBusinessUnitId;
    private static Long secondBusinessUnitId;

    private static Long firstProjectId;
    private static Long secondProjectId;
    private static Long thirdProjectId;

    private static Long resourceManagerId;
    private static Long superVisorId;
    private static Long firstConsultantId;
    private static Long secondConsultantId;
    private static Long thirdConsultantId;

    private static Long firstProjectManagerId;
    private static Long secondProjectManagerId;
    private static Long firstSolutionArchitectId;
    private static Long secondSolutionArchitectId;
    private static Long thirdSolutionArchitectId;

    @Autowired
    IProjectService projectService;

    @Autowired
    static IProjectDao projectDao;

    @Autowired
    IEmployeeDao employeeDao;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < createdEntitiesNumber; i++) {
            Project project = new Project();
            project.setProjectName("ProjectName" + random.nextLong());
            entityManager.persist(project);
            entityIdList.add(project.getId());
        }
        firstProjectId = entityIdList.get(createdEntitiesNumber - 3);
        firstEntityId = firstProjectId;
        secondProjectId = entityIdList.get(createdEntitiesNumber - 2);
        thirdProjectId = entityIdList.get(createdEntitiesNumber - 1);

        transaction.commit();
        prepareTestData(entityManagerFactory, projectDao);
    }

    @Test
    void updateProjectAspectThenSuccess(){
        ProjectAspectLineDTO projectAspectLineDTO = prepareProjectAspectLineDTO();
        projectService.updateProjectAspects(firstProjectId, projectAspectLineDTO, firstProjectManagerId );
        assertEquals(1, projectService.find(firstProjectId).getProjectAspectLineSet().size());
    }

    @Test
    void addRealEndDateThenSuccess(){
        RealEndDateDTO realEndDateDTO = prepareRealEndDateDTO(10, "First Reason");
        projectService.addProjectEndDate(firstProjectId, realEndDateDTO, firstProjectManagerId);
        assertEquals(1, projectService.find(firstProjectId).getRealEndDateSet().size());
    }

    @Test
    void saveFromDtoThenSuccess(){
        ProjectCreationDTO projectCreationDTO = prepareProjectCreationDto();
        ProjectCreatedDTO projectCreatedDTO = projectService.save(projectCreationDTO);
        assertNotNull(projectCreatedDTO);
        assertNotNull(projectService.find(projectCreatedDTO.getId()));
    }

    @Test
    void findAll_ThenSuccess() {
        List<ProjectDTO> lists = projectService.findAll();
        assertEquals(createdEntitiesNumber, lists.size());
    }

    @Test
    @Override
    public void deleteThenSuccess() {
        Project createdProject = createNewEntity();
        createdProject = projectService.saveInternal(createdProject);
        projectService.deleteInternal(createdProject.getId());
        assertEquals(createdEntitiesNumber, projectService.findAll().size());
    }


    private ProjectCreationDTO prepareProjectCreationDto(){
        BusinessRelationManagerDTO businessRelationManagerDTO = new BusinessRelationManagerDTO();
        businessRelationManagerDTO.setId(businessRelationManagerId);

        BusinessEmployeeDTO businessEmployeeDTO = new BusinessEmployeeDTO();
        businessEmployeeDTO.setId(businessEmployeeId);
        BusinessLeaderDTO businessLeaderDTO = new BusinessLeaderDTO();
        businessLeaderDTO.setEmployee(businessEmployeeDTO);

        Set<BusinessUnitDTO> businessUnitDTOSet = new HashSet<>();
        BusinessUnitDTO firstBusinessUnitDto = new BusinessUnitDTO();
        firstBusinessUnitDto.setId(firstBusinessUnitId);
        BusinessUnitDTO secondBusinessUnitDto = new BusinessUnitDTO();
        secondBusinessUnitDto.setId(secondBusinessUnitId);

        businessUnitDTOSet.add(firstBusinessUnitDto);
        businessUnitDTOSet.add(secondBusinessUnitDto);

        ProjectCreationDTO projectCreationDTO = new ProjectCreationDTO();
        projectCreationDTO.setProjectName("ProjectName" + random.nextInt());

        projectCreationDTO.setBusinessRelationManager(businessRelationManagerDTO);
        projectCreationDTO.setBusinessLeader(businessLeaderDTO);
        projectCreationDTO.setBusinessUnits(businessUnitDTOSet);

        return projectCreationDTO;
    }

    private ProjectAspectLine prepareProjectAspectLine(){
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

    private ProjectAspectLineDTO prepareProjectAspectLineDTO(){
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

    private static void prepareTestData(EntityManagerFactory entityManagerFactory, IProjectDao projectDao) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();

        Project firstProject = entityManager.find(Project.class,1l);
        Project secondProject = entityManager.find(Project.class,2l);
        Project thirdProject = entityManager.find(Project.class,3l);

        firstProject.setProjectClass(ProjectClass.I);
        secondProject.setProjectClass(ProjectClass.I);
        thirdProject.setProjectClass(ProjectClass.II);

        //TODO To fix during Open Project Task
//        firstProject.setProjectStatus(ProjectStatus.ANALYSIS);
//        secondProject.setProjectStatus(ProjectStatus.ANALYSIS);
//        thirdProject.setProjectStatus(ProjectStatus.IN_PROGRESS);

        Supervisor supervisor = new Supervisor();
        supervisor.setUserName("Supervisor");
        entityManager.persist(supervisor);

        ResourceManager resourceManager = new ResourceManager();
        resourceManager.setEmployee(supervisor);;

        superVisorId = supervisor.getId();

        Consultant firstConsultant = new Consultant();
        firstConsultant.setUserName("FirstConsultant");
        firstConsultant.setSupervisor(supervisor);
        entityManager.persist(firstConsultant);
        firstConsultantId = firstConsultant.getId();

        Consultant secondConsultant = new Consultant();
        secondConsultant.setUserName("SecondConsultant");
        secondConsultant.setSupervisor(supervisor);
        entityManager.persist(secondConsultant);
        secondConsultantId = secondConsultant.getId();

        Consultant thirdConsultant = new Consultant();
        thirdConsultant.setUserName("ThirdConsultant");
        entityManager.persist(thirdConsultant);
        thirdConsultantId = thirdConsultant.getId();

        ProjectManager firstProjectManager = new ProjectManager();
        firstProjectManager.setEmployee(firstConsultant);
        SolutionArchitect firstSolutionArchitect = new SolutionArchitect();
        firstSolutionArchitect.setEmployee(firstConsultant);

        ProjectManager secondProjectManager = new ProjectManager();
        secondProjectManager.setEmployee(secondConsultant);
        SolutionArchitect secondSolutionArchitect = new SolutionArchitect();
        secondSolutionArchitect.setEmployee(secondConsultant);

        SolutionArchitect thirdSolutionArchitect = new SolutionArchitect();
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

        BusinessEmployee businessEmployee = new BusinessEmployee();
        businessEmployee.setUserName("UserName" + random.nextLong());
        entityManager.persist(businessEmployee);
        businessEmployeeId = businessEmployee.getId();

        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setUserName("BusinessRelationManager" + random.nextLong());
        entityManager.persist(businessRelationManager);
        businessRelationManagerId = businessRelationManager.getId();

        BusinessUnit firstBusinessUnit = new BusinessUnit();
        firstBusinessUnit.setName("BusinessUnitName" + random.nextLong());
        entityManager.persist(firstBusinessUnit);
        firstBusinessUnitId = firstBusinessUnit.getId();

        BusinessUnit secondBusinessUnit = new BusinessUnit();
        secondBusinessUnit.setName("BusinessUnitName" + random.nextLong());
        entityManager.persist(secondBusinessUnit);
        secondBusinessUnitId = secondBusinessUnit.getId();

        transaction.commit();
        entityManager.merge(firstProjectManager);
        firstProjectManagerId = firstProjectManager.getId();
        entityManager.merge(secondProjectManager);
        secondProjectManagerId = secondProjectManager.getId();

        entityManager.merge(firstSolutionArchitect);
        firstSolutionArchitectId = firstSolutionArchitect.getId();
        entityManager.merge(secondSolutionArchitect);
        secondSolutionArchitectId = secondSolutionArchitect.getId();
        entityManager.merge(thirdSolutionArchitect);

        thirdSolutionArchitectId = thirdSolutionArchitect.getId();

        resourceManagerId =  entityManager.merge(resourceManager).getId();
    }

    @Override
    protected Project createNewEntity() {
        Project project = new Project();
        project.setProjectName("ProjectName" + random.nextLong());
        return project;
    }

    private RealEndDateDTO prepareRealEndDateDTO(int offset,String reason) {
        RealEndDateDTO realEndDateDTO = new RealEndDateDTO();
        realEndDateDTO.setEndDate(LocalDateTime.now().plusDays(offset));
        realEndDateDTO.setReason(reason);
        return realEndDateDTO;
    }


    @Override
    protected IProjectService getService() {
        return projectService;
    }
}
