package com.mbor.dao;

import com.mbor.domain.Project;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
@Transactional
class ProjectDaoTest extends IDaoImplTest<Project> {

    @Autowired
    public IProjectDao projectDao;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException {
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
