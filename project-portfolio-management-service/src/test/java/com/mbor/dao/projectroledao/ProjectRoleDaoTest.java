package com.mbor.dao.projectroledao;

import com.mbor.dao.IEmployeeDao;
import com.mbor.dao.IProjectDao;
import com.mbor.dao.IProjectRoleDao;
import com.mbor.domain.Supervisor;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
@Transactional
class ProjectRoleDaoTest {

    private static Long employeeId;

    @Autowired
    IProjectRoleDao projectRoleDao;

    @Autowired
    IEmployeeDao employeeDao;

    @Autowired
    IProjectDao projectDao;

    @BeforeAll
    @Rollback
    static void setUp(@Autowired EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Random random = new Random();
        Supervisor supervisor = new Supervisor();
        supervisor.setUserName("Supervisor" + random.nextLong());
        transaction.begin();
        entityManager.persist(supervisor);

        employeeId = supervisor.getId();

        ResourceManager resourceManager = new ResourceManager();
        resourceManager.setEmployee(supervisor);
        entityManager.persist(resourceManager);

        ProjectManager projectManager = new ProjectManager();
        projectManager.setEmployee(supervisor);
        entityManager.persist(projectManager);

        transaction.commit();
    }

    @Test
    void findAllRoleOfEmployee() {
       assertEquals(projectRoleDao.findAllRoleOfEmployee(employeeId).size(), 2);
    }
}