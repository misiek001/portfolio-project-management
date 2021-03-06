package com.mbor.dao.projectroledao;

import com.mbor.dao.*;
import com.mbor.domain.Employee;
import com.mbor.domain.Project;
import com.mbor.domain.Supervisor;
import com.mbor.domain.employeeinproject.ProjectRole;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.AfterAll;
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
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
@Transactional
public class ResourceManagerDaoTest  {

    private static Random random = new Random();

    private static Long employeeId;

    private static int createdEntityNumber = 3;

    @Autowired
    IProjectRoleDao projectRoleDao;

    @Autowired
    IEmployeeDao employeeDao;

    @Autowired
    IProjectDao projectDao;

    @BeforeAll
    static void setUp(@Autowired EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Supervisor supervisor = new Supervisor();
        supervisor.setUserName("Supervisor" + random.nextLong());
        transaction.begin();
        entityManager.persist(supervisor);

        employeeId = supervisor.getId();
        for (int i = 0; i < createdEntityNumber; i++) {
            ResourceManager resourceManager = new ResourceManager();
            entityManager.persist(resourceManager);
        }
        transaction.commit();
    }

    @AfterAll
    static void clear(@Autowired TableClearer tableClearer){
        tableClearer.clearTables();
    }

    @Test
    public void findAll_ThenSuccess() {
        List<ResourceManager> lists = getDao().findAll();

        assertEquals(createdEntityNumber, lists.size());
    }

    @Test
    void addEmployeeToProjectManagerThenSuccess() {
        ResourceManager resourceManager = createNewEntity();
        Optional<Employee> result = employeeDao.find(employeeId);
        if (result.isPresent()) {
            resourceManager.setEmployee((Supervisor) employeeDao.find(employeeId).get());
        } else {
            fail();
        }
        Optional<ProjectRole> projectRoleResult = projectRoleDao.save(resourceManager);
        if(projectRoleResult.isPresent()) {
            resourceManager = (ResourceManager) projectRoleDao.save(resourceManager).get();
            assertNotNull(resourceManager.getEmployee());
        } else {
            fail();
        }
    }

    @Test
    void addProjectManagerToProjectThenSuccess() {
        ResourceManager resourceManager = createNewEntity();
        Optional<Employee> result = employeeDao.find(employeeId);
        if (result.isPresent()) {
            resourceManager.setEmployee((Supervisor) result.get());
        } else {
            fail();
        }
        Project project = new Project();
        project.setProjectName("ProjectName" + random.nextLong());
        project.setResourceManager(resourceManager);
        Optional<Project> projectResult = projectDao.save(project);
        if (projectResult.isPresent()) {
            assertNotNull(project.getResourceManager());
        } else {
            fail();
        }
    }


    protected ResourceManager createNewEntity() {
        return new ResourceManager();
    }


    protected IDao getDao() {
        return projectRoleDao;
    }

}
