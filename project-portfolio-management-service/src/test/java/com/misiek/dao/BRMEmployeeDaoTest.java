package com.misiek.dao;

import com.misiek.domain.BusinessRelationManager;
import com.misiek.spring.ServiceConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@Rollback
@Transactional
class BRMEmployeeDaoTest extends IDaoImplTest<BusinessRelationManager> {

    @Autowired
    EmployeeDao employeeDao;

    @Override
    BusinessRelationManager createNewEntity() {
        return new BusinessRelationManager();
    }

    @Override
    IDao getDao() {
        return employeeDao;
    }

    @Test
    public void createLoadAndSave(){
        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        Long brmID = employeeDao.save(businessRelationManager).get().getId();

        BusinessRelationManager loadedBRM = (BusinessRelationManager) employeeDao.find(brmID).get();
        employeeDao.save(loadedBRM);
    }
}