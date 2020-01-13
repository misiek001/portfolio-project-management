package com.mbor.dao;

import com.mbor.domain.BusinessRelationManager;
import com.mbor.spring.ServiceConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@Rollback
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@ActiveProfiles("test")
class BRMEmployeeDaoTest extends IDaoImplTest<BusinessRelationManager> {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    SessionFactory sessionFactory;


    @Override
    BusinessRelationManager createNewEntity() {
        Random random = new Random();
        BusinessRelationManager brm = new BusinessRelationManager();
        brm.setUserName("BRMUserName" + random.nextLong());
        return brm;
    }

    @Override
    IDao getDao() {
        return employeeDao;
    }

    @Test
    public void createLoadAndSave() {
        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        try(Session session = sessionFactory.openSession()) {
             {
                Transaction transaction = session.beginTransaction();
                Long brmID = employeeDao.save(businessRelationManager).get().getId();
                BusinessRelationManager loadedBRM = (BusinessRelationManager) employeeDao.find(brmID).get();
                employeeDao.save(loadedBRM);
                transaction.commit();
            }
        }
    }
}