package com.mbor.dao.employeedao;

import com.mbor.dao.EmployeeDao;
import com.mbor.dao.IDao;
import com.mbor.dao.IDaoImplTest;
import com.mbor.domain.BusinessRelationManager;
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
import java.util.Random;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
@Transactional
class BusinessRelationManagerEmployeeDaoTest extends IDaoImplTest<BusinessRelationManager> {

    @Autowired
    EmployeeDao employeeDao;

    @Override
    protected BusinessRelationManager createNewEntity() {
        Random random = new Random();
        BusinessRelationManager brm = new BusinessRelationManager();
        brm.setUserName("BRMUserName" + random.nextLong());
        return brm;
    }

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < IDaoImplTest.createdEntitiesNumber; i++) {
            BusinessRelationManager  businessRelationManager = new BusinessRelationManager();
            businessRelationManager.setUserName("BRMUserName" + random.nextLong());
            entityManager.persist(businessRelationManager);
        }
        transaction.commit();
    }
    @Override
    protected IDao getDao() {
        return employeeDao;
    }
}
