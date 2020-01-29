package com.mbor.dao.projectroledao;

import com.mbor.dao.*;
import com.mbor.domain.Employee;
import com.mbor.domain.Project;
import com.mbor.domain.Supervisor;
import com.mbor.domain.employeeinproject.ProjectRole;
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
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
@Transactional
public class ResourceManagerDaoTest extends IDaoImplTest<ResourceManager> {

    private static Long employeeId;

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
        Random random = new Random();
        Supervisor supervisor = new Supervisor();
        supervisor.setUserName("Supervisor" + random.nextLong());
        transaction.begin();
        entityManager.persist(supervisor);

        employeeId = supervisor.getId();
        for (int i = 0; i < IDaoImplTest.createdEntitiesNumber; i++) {
            ResourceManager resourceManager = new ResourceManager();
            entityManager.persist(resourceManager);
        }
        transaction.commit();
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

    @Override
    protected ResourceManager createNewEntity() {
        return new ResourceManager();
    }

    @Override
    protected IDao getDao() {
        return projectRoleDao;
    }
}
