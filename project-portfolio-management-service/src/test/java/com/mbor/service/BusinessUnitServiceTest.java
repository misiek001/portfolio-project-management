package com.mbor.service;

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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@ActiveProfiles("test")
class BusinessUnitServiceTest {

    private static final int createdEntitiesNumber = 3;

    private static Random random = new Random();

    @Autowired
    private IBusinessUnitService businessUnitService;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < createdEntitiesNumber; i++) {
            BusinessUnit businessUnit = new BusinessUnit();
            businessUnit.setName("BusinessUnitName" + random.nextLong());
            entityManager.persist(businessUnit);
        }
        transaction.commit();
    }

    @Test
    void saveFromDtoThenSuccess() {
        BusinessUnitCreationDTO businessUnitCreationDTO = prepareBusinessUnitCreationDto();
        BusinessUnitCreatedDTO businessUnitCreatedDTO = businessUnitService.save(businessUnitCreationDTO);
        assertNotNull(businessUnitCreatedDTO);
        assertNotNull(businessUnitService.find(businessUnitCreatedDTO.getId()));
    }

    @Test
    void find_ThenSuccess() {
        BusinessUnit result = (BusinessUnit) businessUnitService.find(1l);
        assertNotNull(result);
    }

    @Test
    void findAll_ThenSuccess() {
        List<BusinessUnit> lists = businessUnitService.findAll();
        assertEquals(createdEntitiesNumber, lists.size());
    }

    @Test
    void delete_ThenSuccess() {
        businessUnitService.delete(3L);
        assertEquals(createdEntitiesNumber - 1, businessUnitService.findAll().size());
    }

    @Test
    void save_ThenSuccess() {
        assertNotNull(businessUnitService.saveInternal(createNewEntity()));
        assertEquals(createdEntitiesNumber + 1, businessUnitService.findAll().size());
    }

    private BusinessUnit createNewEntity() {
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName("BusinessUnitName" + random.nextLong());
        return businessUnit;
    }

    private BusinessUnitCreationDTO prepareBusinessUnitCreationDto() {
        BusinessUnitCreationDTO businessUnitCreationDTO = new BusinessUnitCreationDTO();
        businessUnitCreationDTO.setName("BusinessUnitName" + random.nextLong());
        return businessUnitCreationDTO;
    }
}