package com.mbor.dao.projectroledao;

import com.mbor.dao.*;
import com.mbor.domain.Consultant;
import com.mbor.domain.Employee;
import com.mbor.domain.Project;
import com.mbor.domain.employeeinproject.IProjectManager;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ProjectRole;
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
public class ProjectManagerDaoTest  {

    private static Random random= new Random();

    private static Long employeeId;

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
        transaction.begin();
        entityManager.persist(consultant);
        ;
        employeeId = consultant.getId();
        for (int i = 0; i < createdEntityNumber; i++) {
            ProjectManager projectManager = new ProjectManager();
            entityManager.persist(projectManager);
        }
        transaction.commit();
    }

    @AfterAll
    static void clear(@Autowired TableClearer tableClearer){
        tableClearer.clearTables();
    }

    @Test
    public void findAll_ThenSuccess() {
        List<ProjectManager> lists =  getDao().findAll();
        assertEquals(createdEntityNumber, lists.size());
    }

    @Test
    void addEmployeeToProjectManagerThenSuccess() {
        ProjectManager projectManager = createNewEntity();
        Optional<Employee> result = employeeDao.find(employeeId);
        if (result.isPresent()) {
            projectManager.setEmployee((IProjectManager) employeeDao.find(employeeId).get());
        } else {
            fail();
        }
        Optional<ProjectRole> projectRoleResult = projectRoleDao.save(projectManager);
        if(projectRoleResult.isPresent()) {
            projectManager = (ProjectManager) projectRoleDao.save(projectManager).get();
            assertNotNull(projectManager.getEmployee());
        } else {
            fail();
        }
    }

    @Test
    void addProjectManagerToProjectThenSuccess() {
        ProjectManager projectManager = createNewEntity();
        Optional<Employee> result = employeeDao.find(employeeId);
        if (result.isPresent()) {
            projectManager.setEmployee((IProjectManager) result.get());
        } else {
            fail();
        }
        Project project = new Project();
        project.setProjectName("ProjectName" + random.nextLong());
        project.setProjectManager(projectManager);
        Optional<Project> projectResult = projectDao.save(project);
        if (projectResult.isPresent()) {
            assertNotNull(project.getProjectManager());
        } else {
            fail();
        }
    }

    protected ProjectManager createNewEntity() {
        return new ProjectManager();
    }

    protected IDao getDao() {
        return projectRoleDao;
    }
}
