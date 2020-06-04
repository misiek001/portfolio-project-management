package com.mbor.dao;

import com.mbor.configuration.TestConfiguration;
import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.domain.employeeinproject.SolutionArchitect;
import com.mbor.entityFactory.TestEntityFactory;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.AfterAll;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ServiceConfiguration.class, TestConfiguration.class})
@ActiveProfiles("test")
@Transactional
@Rollback
class ProjectDaoTest extends IDaoImplTest<Project> {

    @Autowired
    public IProjectDao projectDao;

    private static Long firstConsultantId;
    private static Long secondConsultantId;
    private static Long thirdConsultantId;

    private static Long superVisorId;
    private static Long resourceManagerId;


    private static Long firstProjectManagerId;
    private static Long secondProjectManagerId;

    private static Long firstSolutionArchitectId;
    private static Long secondSolutionArchitectId;
    private static Long thirdSolutionArchitectId;


    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory, @Autowired TestEntityFactory testEntityFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < IDaoImplTest.CREATED_ENTITIES_NUMBER; i++) {
            Project project = testEntityFactory.prepareProject();
            entityManager.persist(project);
            entityIdList.add(project.getId());
        }
        transaction.commit();
        prepareTestData(entityManagerFactory, testEntityFactory);
    }

    @Override
    public void delete_ThenSuccess() {
        getDao().delete(getElementIndex(3));
        entityIdList.remove(3);
        assertEquals(entityIdList.size(), getDao().findAll().size());
    }

    @Override
    public void findAll_ThenSuccess() {
        List<Project> lists = getDao().findAll();
        assertEquals(entityIdList.size(), lists.size());
    }

    @AfterAll
    static void clear(@Autowired TableClearer tableClearer){
        tableClearer.clearTables();
        entityIdList.clear();
    }

    @Test
    public void addProjectLineThenSuccess(){
        Optional<Project> projectOptional = projectDao.find(getElementIndex(0));
        if(!projectOptional.isPresent()){
            fail();
        }
        Project project = projectOptional.get();
        project.addProjectAspectLine(testEntityFactory.prepareProjectAspectLine());
        projectDao.update(project);
        project = projectDao.find(getElementIndex(0)).get();
        assertEquals(1, project.getProjectAspectLines().size());
    }

    @Test
    public void findResourceManagerProjectsIndependentCriteriaThenSuccess(){
        assertEquals( 3, projectDao.findResourceManagerProjects(resourceManagerId, null, null, null, null).size());
        assertEquals(1,  projectDao.findResourceManagerProjects(resourceManagerId, getElementIndex(3), null, null, null).size());

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
    public void findResourceManagerProjectsCombinedCriteriaThenSuccess(){
        List<ProjectClass> projectClasses = new ArrayList<>();
        projectClasses.add(ProjectClass.I);

        List<ProjectStatus> projectStatuses = new ArrayList<>();
        projectStatuses.add(ProjectStatus.ANALYSIS);

        assertEquals(1,  projectDao.findResourceManagerProjects(resourceManagerId, getElementIndex(0), "Name", projectClasses, projectStatuses).size());
    }

    @Test
    public void findSupervisorProjectsIndependentCriteriaThenSuccess(){
        assertEquals(2, projectDao.findSupervisorProjects(superVisorId, null, null, null, null, null, null).size());
        assertEquals(2, projectDao.findSupervisorProjects(superVisorId, null, null, null, null, null, Arrays.asList(firstSolutionArchitectId, secondSolutionArchitectId)).size());
        assertEquals(1, projectDao.findSupervisorProjects(superVisorId, null, null, null, null, null, Collections.singletonList(thirdSolutionArchitectId)).size());
        assertEquals(1, projectDao.findSupervisorProjects(superVisorId, null, null, null, null, Collections.singletonList(firstProjectManagerId), null).size());
        assertEquals(2, projectDao.findSupervisorProjects(superVisorId, null, null, null, null, Arrays.asList(firstProjectManagerId, secondProjectManagerId), null).size());
    }

    @Test
    public void findSupervisorProjectsCombinedCriteriaThenSuccess() {
        assertEquals(2, projectDao.findSupervisorProjects(superVisorId, null, null, null, null, Collections.singletonList(firstProjectManagerId), Arrays.asList(firstSolutionArchitectId, secondSolutionArchitectId)).size());
    }

    @Test
    public void findConsultantProjectThenSuccess()  {
        assertEquals(1, projectDao.findConsultantProject(firstConsultantId).size());
        assertEquals(2, projectDao.findConsultantProject(secondConsultantId).size());
        assertEquals(2, projectDao.findConsultantProject(thirdConsultantId).size());
    }


    private static void prepareTestData(EntityManagerFactory entityManagerFactory, TestEntityFactory testEntityFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();

        Project firstProject = testEntityFactory.prepareProject();
        entityManager.persist(firstProject);
        entityIdList.add(firstProject.getId());
        Project secondProject =testEntityFactory.prepareProject();
        entityManager.persist(secondProject);
        entityIdList.add(secondProject.getId());

        Project thirdProject = testEntityFactory.prepareProject();
        entityManager.persist(thirdProject);
        entityIdList.add(thirdProject.getId());

        firstProject.setProjectClass(ProjectClass.I);
        secondProject.setProjectClass(ProjectClass.I);
        thirdProject.setProjectClass(ProjectClass.II);

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

        secondProject.setProjectManager(secondProjectManager);
        secondProject.addSolutionArchitect(secondSolutionArchitect);
        secondProject.addSolutionArchitect(thirdSolutionArchitect);
        secondProject.setResourceManager(resourceManager);

        thirdProject.addSolutionArchitect(thirdSolutionArchitect);
        thirdProject.setResourceManager(resourceManager);

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
        return testEntityFactory.prepareProject();
    }

    @Override
    protected IDao getDao() {
        return projectDao;
    }

}
