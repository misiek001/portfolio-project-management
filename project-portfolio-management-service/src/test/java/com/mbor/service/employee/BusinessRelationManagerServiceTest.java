package com.mbor.service.employee;

import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.security.User;
import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;
import com.mbor.model.creation.EmployeeType;
import com.mbor.service.IEmployeeService;
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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@ActiveProfiles("test")

class BusinessRelationManagerServiceTest {

    private static final int createdEntitiesNumber = 3;

    private static Random random = new Random();
    
    @Autowired
    IEmployeeService employeeService;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < createdEntitiesNumber; i++) {
            BusinessRelationManager businessRelationManager = new BusinessRelationManager();
            businessRelationManager.setUserName("BusinessRelationManager" + random.nextLong());
            entityManager.persist(businessRelationManager);
        }
        transaction.commit();
    }

    @Test
    void saveFromDtoThenSuccess() {
        EmployeeCreationDTO businessRelationManagerCreationDTO = prepareBusinessRelationManagerCreationDto();
        EmployeeCreatedDTO businessRelationManagerCreatedDTO = employeeService.save(businessRelationManagerCreationDTO);
        assertNotNull(employeeService.find(businessRelationManagerCreatedDTO.getId()));
        assertNotNull(businessRelationManagerCreatedDTO);
    }

    @Test
    void saveFromDtoWithRoleAndPrivilege(){
        EmployeeCreationDTO businessRelationManagerCreationDTO = prepareBusinessRelationManagerCreationDto();
        EmployeeCreatedDTO businessRelationManagerCreatedDTO = employeeService.save(businessRelationManagerCreationDTO);
        BusinessRelationManager savedBRM = (BusinessRelationManager) employeeService.find(businessRelationManagerCreatedDTO.getId());
        assertNotNull(savedBRM);
        User savedUser = savedBRM.getUser();
        assertNotNull(savedBRM);
        assertNotNull(savedUser.getRoles());;
    }

    @Test
    void find_ThenSuccess() {
        BusinessRelationManager result = (BusinessRelationManager) employeeService.find(1l);
        assertNotNull(result);
    }


    @Test
    void delete_ThenSuccess() {
        employeeService.delete(3L);
        assertEquals(createdEntitiesNumber - 1, employeeService.findAll().size());
    }

    @Test
    void save_ThenSuccess() {
        assertNotNull(employeeService.saveInternal(createNewEntity()));
        assertEquals(createdEntitiesNumber + 1, employeeService.findAll().size());
    }

    private BusinessRelationManager createNewEntity() {
        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setFirstName("BusinessRelationManagerFirstName");
        businessRelationManager.setLastName("BusinessRelationManagerLastName");
        businessRelationManager.setUserName(businessRelationManager.getFirstName().concat(businessRelationManager.getLastName()).concat(String.valueOf(random.nextLong())));
        return businessRelationManager;
    }

    private EmployeeCreationDTO prepareBusinessRelationManagerCreationDto() {
        EmployeeCreationDTO businessRelationManagerCreationDTO = new EmployeeCreationDTO();
        businessRelationManagerCreationDTO.setFirstName("BusinessRelationManagerFirstName");
        businessRelationManagerCreationDTO.setLastName("BusinessRelationManagerLastName");
        businessRelationManagerCreationDTO.setEmployeeType(EmployeeType.BusinessRelationManager);
        return businessRelationManagerCreationDTO;
    }
}