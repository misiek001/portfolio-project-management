package com.mbor.service;

import com.mbor.dao.IDao;
import com.mbor.domain.BusinessUnit;
import com.mbor.model.creation.BusinessUnitCreatedDTO;
import com.mbor.model.creation.BusinessUnitCreationDTO;
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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@ActiveProfiles("test")
class BusinessUnitServiceTest extends IServiceTestImpl {

    private static final int createdEntitiesNumber = 3;

    private static Random random = new Random();
    private static Long firstBusinessUnitId;

    @Autowired
    private IBusinessUnitService businessUnitService;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < createdEntitiesNumber; i++) {
            BusinessUnit businessUnit = new BusinessUnit();
            businessUnit.setName("BusinessUnitName" + random.nextLong());
            entityManager.persist(businessUnit);
            entityIdList.add(businessUnit.getId());
        }
        firstBusinessUnitId = entityIdList.get(0);
        firstEntityId = firstBusinessUnitId;
        transaction.commit();
    }

    @Test
    void saveFromDtoThenSuccess() {
        BusinessUnitCreationDTO businessUnitCreationDTO = prepareBusinessUnitCreationDto();
        BusinessUnitCreatedDTO businessUnitCreatedDTO = businessUnitService.save(businessUnitCreationDTO);
        assertNotNull(businessUnitCreatedDTO);
        assertNotNull(businessUnitService.findInternal(businessUnitCreatedDTO.getId()));
    }

    private BusinessUnitCreationDTO prepareBusinessUnitCreationDto() {
        BusinessUnitCreationDTO businessUnitCreationDTO = new BusinessUnitCreationDTO();
        businessUnitCreationDTO.setName("BusinessUnitName" + random.nextLong());
        return businessUnitCreationDTO;
    }

    @Override
    public BusinessUnit createNewEntity() {
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName("BusinessUnitName" + random.nextLong());
        return businessUnit;
    }

    @Override
    protected IBusinessUnitService getService() {
        return businessUnitService;
    }

    @Override
    protected IDao getDao() {
        return null;
    }
}