package com.mbor.dao;

import com.mbor.domain.Project;
import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;
import com.mbor.domain.Supervisor;
import com.mbor.domain.employeeinproject.ResourceManager;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
@Transactional
class ProjectDaoTest extends IDaoImplTest<Project> {

    @Autowired
    public IProjectDao projectDao;

    private Long resourceManagerId;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < IDaoImplTest.createdEntitiesNumber; i++) {
            Project project = new Project();
            project.setProjectName("ProjectName" + random.nextLong());
            entityManager.persist(project);
        }
        transaction.commit();
    }

    @Test
    public void findResourceManagerProjectsTestIndependentCriteriaThenSuccess(@Autowired EntityManagerFactory entityManagerFactory){
        loadTestDataForFindResourceManagerProjects(entityManagerFactory);

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
    public void findResourceManagerProjectsTestCombinedCriteriaThenSuccess(@Autowired EntityManagerFactory entityManagerFactory){
        loadTestDataForFindResourceManagerProjects(entityManagerFactory);

        List<ProjectClass> projectClasses = new ArrayList<>();
        projectClasses.add(ProjectClass.I);

        List<ProjectStatus> projectStatuses = new ArrayList<>();
        projectStatuses.add(ProjectStatus.ANALYSIS);

        assertEquals(1,  projectDao.findResourceManagerProjects(resourceManagerId, 1l, "Name", projectClasses, projectStatuses).size());
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
