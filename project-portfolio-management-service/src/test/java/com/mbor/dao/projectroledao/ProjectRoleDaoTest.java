package com.mbor.dao.projectroledao;

import com.mbor.dao.IEmployeeDao;
import com.mbor.dao.IProjectDao;
import com.mbor.dao.IProjectRoleDao;
import com.mbor.dao.TableClearer;
import com.mbor.domain.Consultant;
import com.mbor.domain.Supervisor;
import com.mbor.domain.employeeinproject.ProjectManager;
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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
@Transactional
class ProjectRoleDaoTest {

    private static Random random = new Random();
    
    @Autowired
    IProjectRoleDao projectRoleDao;

    @Autowired
    IEmployeeDao employeeDao;

    @Autowired
    IProjectDao projectDao;

    private static int createdEntityNumber = 3;

    @BeforeAll
    static void setUp(@Autowired EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Consultant consultant = new Consultant();
        consultant.setUserName("ConsultantUserName" + random.nextLong());
        Supervisor supervisor = new Supervisor();
        supervisor.setUserName("Supervisor" + random.nextLong());

        transaction.begin();
        entityManager.persist(consultant);
        for (int i = 0; i < createdEntityNumber; i++) {
            ProjectManager projectManager = new ProjectManager();
            entityManager.persist(projectManager);
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
    void findAllRoleOfEmployee() {
    }

    @Test
    void findAllDemandedRole() {
        assertEquals(projectRoleDao.findAllDemandedRole(ProjectManager.class).size(), createdEntityNumber);
        assertEquals(projectRoleDao.findAllDemandedRole(ResourceManager.class).size(), createdEntityNumber);
    }
}