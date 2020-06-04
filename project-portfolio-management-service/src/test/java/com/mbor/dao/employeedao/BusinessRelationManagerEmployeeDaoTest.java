package com.mbor.dao.employeedao;

import com.mbor.configuration.TestConfiguration;
import com.mbor.dao.EmployeeDao;
import com.mbor.dao.IDao;
import com.mbor.dao.IDaoImplTest;
import com.mbor.dao.TableClearer;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.entityFactory.TestObjectFactory;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.AfterAll;
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

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ServiceConfiguration.class, TestConfiguration.class})
@ActiveProfiles("test")
@Transactional
class BusinessRelationManagerEmployeeDaoTest extends IDaoImplTest<BusinessRelationManager> {

    @Autowired
    EmployeeDao employeeDao;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory, @Autowired TestObjectFactory testObjectsFactory, @Autowired TableClearer tableClearer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < IDaoImplTest.CREATED_ENTITIES_NUMBER; i++) {
            BusinessRelationManager businessRelationManager = testObjectsFactory.prepareBusinessRelationManager();
            entityManager.persist(businessRelationManager);
            entityIdList.add(businessRelationManager.getId());
        }
        transaction.commit();
    }

    @AfterAll
    static void clear(@Autowired TableClearer tableClearer){
        tableClearer.clearTables();
        entityIdList.clear();
    }

    @Override
    protected IDao getDao() {
        return employeeDao;
    }

    @Override
    protected BusinessRelationManager createNewEntity() {
        return testObjectsFactory.prepareBusinessRelationManager();
    }
}
