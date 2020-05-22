package com.mbor.dao;

import com.mbor.domain.DemandSheet;
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
class DemandSheetDaoTest  extends IDaoImplTest<DemandSheet> {

    @Autowired
    private IDemandSheetDao demandSheetDao;

    @Override
    protected IDao getDao() {
        return demandSheetDao;
    }

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < IDaoImplTest.createdEntitiesNumber; i++) {
            DemandSheet demandSheet = new DemandSheet();
            demandSheet.setProjectName("Project Name" + random.nextLong() );
            demandSheet.setDescription("Project Description" + random.nextLong());
            entityManager.persist(demandSheet);
        }
        transaction.commit();
    }

    @Override
    protected DemandSheet createNewEntity() {
        Random random = new Random();
        DemandSheet demandSheet = new DemandSheet();
        demandSheet.setProjectName("Project Name" + random.nextLong() );
        demandSheet.setDescription("Project Description" + random.nextLong());
        return demandSheet;
    }
}