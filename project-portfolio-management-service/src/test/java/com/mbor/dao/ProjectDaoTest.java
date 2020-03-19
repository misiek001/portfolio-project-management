package com.mbor.dao;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.domain.employeeinproject.SolutionArchitect;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
@Transactional
@Rollback
class ProjectDaoTest extends IDaoImplTest<Project> {

    @Autowired
    public  IProjectDao projectDao;

    private static Long firstConsultantId;
    private static Long secondConsultantId;
    private static Long thirdConsultantId;

    private static Long superVisorId;
    private Long resourceManagerId;


    private static Long firstProjectManagerId;
    private static Long secondProjectManagerId;

    private static Long firstSolutionArchitectId;
    private static Long secondSolutionArchitectId;
    private static Long thirdSolutionArchitectId;


    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory, @Autowired IProjectDao projectDao) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < IDaoImplTest.createdEntitiesNumber; i++) {
            Project project = new Project();
            project.setProjectName("ProjectName" + random.nextLong());
            entityManager.persist(project);
        }
        transaction.commit();
        loadTestDataForFindSupervisorProjects(entityManagerFactory, projectDao);
    }

    @Test
    public void findResourceManagerProjectsIndependentCriteriaThenSuccess(@Autowired EntityManagerFactory entityManagerFactory){
  //      loadTestDataForFindResourceManagerProjects(entityManagerFactory);

        assertEquals( 3, projectDao.findResourceManagerProjects(resourceManagerId, null, null, null, null).size());
        assertEquals(1,  projectDao.findResourceManagerProjects(resourceManagerId, 1l, null, null, null).size());

        List<ProjectClass> projectClasses = new ArrayList<>();
        projectClasses.add(ProjectClass.I);
        assertEquals(2,  projectDao.findResourceManagerProjects(resourceManagerId, null, null, projectClasses, null).size());
        projectClasses.add(ProjectClass.II);
        assertEquals(3,  projectDao.findResourceManagerProjects(resourceManagerId, null, null, projectClasses, null).size());

        List<ProjectStatus> projectStatuses = new ArrayList<>();
        projectStatuses.add(ProjectStatus.ANALYSIS);
        assertEquals(2,  projectDao.findResourceManagerProjects(resourceManagerId, null, null, null, projectStatuses).size());
        projectStatuses.add(ProjectStatus.IN_PROGRESS);
        assertEquals(3,  projectDao.findResourceManagerProjects(resourceManagerId, null, null, null, projectStatuses).size());
    }
    @Test
    public void findResourceManagerProjectsCombinedCriteriaThenSuccess(@Autowired EntityManagerFactory entityManagerFactory){
     //   loadTestDataForFindResourceManagerProjects(entityManagerFactory);

        List<ProjectClass> projectClasses = new ArrayList<>();
        projectClasses.add(ProjectClass.I);

        List<ProjectStatus> projectStatuses = new ArrayList<>();
        projectStatuses.add(ProjectStatus.ANALYSIS);

        assertEquals(1,  projectDao.findResourceManagerProjects(resourceManagerId, 1l, "Name", projectClasses, projectStatuses).size());
    }

    @Test
    public void findSupervisorProjectsIndependentCriteriaThenSuccess(@Autowired EntityManagerFactory entityManagerFactory){
    //    loadTestDataForFindSupervisorProjects(entityManagerFactory);

        assertEquals(2, projectDao.findSupervisorProjects(superVisorId, null, null, null, null, null, null).size());

        assertEquals(2, projectDao.findSupervisorProjects(superVisorId, null, null, null, null, null, Arrays.asList(firstSolutionArchitectId, secondSolutionArchitectId)).size());

        assertEquals(1, projectDao.findSupervisorProjects(superVisorId, null, null, null, null, null, Collections.singletonList(thirdSolutionArchitectId)).size());

        assertEquals(1, projectDao.findSupervisorProjects(superVisorId, null, null, null, null, Collections.singletonList(firstProjectManagerId), null).size());

        assertEquals(2, projectDao.findSupervisorProjects(superVisorId, null, null, null, null, Arrays.asList(firstProjectManagerId, secondProjectManagerId), null).size());
    }

    @Test
    public void findSupervisorProjectsCombinedCriteriaThenSuccess(@Autowired EntityManagerFactory entityManagerFactory) {
   //     loadTestDataForFindSupervisorProjects(entityManagerFactory);
        assertEquals(2, projectDao.findSupervisorProjects(superVisorId, null, null, null, null, Collections.singletonList(firstProjectManagerId), Arrays.asList(firstSolutionArchitectId, secondSolutionArchitectId)).size());
    }

    @Test
    public void findConsultantProjectThenSuccess(@Autowired EntityManagerFactory entityManagerFactory) throws InterruptedException {
    //    loadTestDataForFindSupervisorProjects(entityManagerFactory);

        assertEquals(1, projectDao.findConsultantProject(firstConsultantId).size());

        assertEquals(2, projectDao.findConsultantProject(secondConsultantId).size());

        assertEquals(2, projectDao.findConsultantProject(thirdConsultantId).size());

    }



    private void loadTestDataForFindResourceManagerProjects(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();

        Project firstProject = entityManager.find(Project.class,1l);
        Project secondProject = entityManager.find(Project.class,2l);
        Project thirdProject = entityManager.find(Project.class,3l);

        firstProject.setProjectClass(ProjectClass.I);
        secondProject.setProjectClass(ProjectClass.I);
        thirdProject.setProjectClass(ProjectClass.II);

        firstProject.setProjectStatus(ProjectStatus.ANALYSIS);
        secondProject.setProjectStatus(ProjectStatus.ANALYSIS);
        thirdProject.setProjectStatus(ProjectStatus.IN_PROGRESS);

        Supervisor supervisor = new Supervisor();
        supervisor.setUserName("Supervisor");
        entityManager.persist(supervisor);

        ResourceManager resourceManager = new ResourceManager();
        resourceManager.setEmployee(supervisor);

        firstProject.setResourceManager(resourceManager);
        entityManager.merge(firstProject);
        resourceManager = entityManager.find(ResourceManager.class, 1l);

        secondProject.setResourceManager(resourceManager);
        entityManager.merge(secondProject);

        thirdProject.setResourceManager(resourceManager);
        entityManager.merge(thirdProject);
        transaction.commit();

        resourceManagerId = projectDao.find(1L).get().getResourceManager().getId();
    }

    private static void loadTestDataForFindSupervisorProjects(EntityManagerFactory entityManagerFactory, IProjectDao projectDao) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();

        Project firstProject = entityManager.find(Project.class,1l);
        Project secondProject = entityManager.find(Project.class,2l);
        Project thirdProject = entityManager.find(Project.class,3l);

        firstProject.setProjectClass(ProjectClass.I);
        secondProject.setProjectClass(ProjectClass.I);
        thirdProject.setProjectClass(ProjectClass.II);

        firstProject.setProjectStatus(ProjectStatus.ANALYSIS);
        secondProject.setProjectStatus(ProjectStatus.ANALYSIS);
        thirdProject.setProjectStatus(ProjectStatus.IN_PROGRESS);

        Supervisor supervisor = new Supervisor();
        supervisor.setUserName("Supervisor");
        entityManager.persist(supervisor);

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

        firstProject.setProjectManager(firstProjectManager);
        firstProject.addSolutionArchitect(firstSolutionArchitect);
        firstProject.addSolutionArchitect(secondSolutionArchitect);

        secondProject.setProjectManager(secondProjectManager);
        secondProject.addSolutionArchitect(secondSolutionArchitect);
        secondProject.addSolutionArchitect(thirdSolutionArchitect);

        thirdProject.addSolutionArchitect(thirdSolutionArchitect);

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

    }

    @Override
    protected Project createNewEntity() {
        Project project = new Project();
        project.setProjectName("ProjectName" + ProjectDaoTest.random.nextLong());
        return project;
    }

    @Override
    protected IDao getDao() {
        return projectDao;
    }

}
