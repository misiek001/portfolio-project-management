package com.mbor.service.employee;

import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.security.User;
import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;
import com.mbor.model.creation.EmployeeType;
import com.mbor.service.IEmployeeService;
import com.mbor.service.IServiceTestImpl;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@ActiveProfiles("test")
class BusinessRelationManagerServiceTest extends IServiceTestImpl<BusinessRelationManager> {

    private static final int createdEntitiesNumber = 3;

    private static Random random = new Random();
    private static Long firstBusinessRelationManagerId;

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
            entityIdList.add(businessRelationManager.getId());
        }
        firstBusinessRelationManagerId = entityIdList.get(0);
        firstEntityId = firstBusinessRelationManagerId;
        transaction.commit();
    }

    @Test
    void saveFromDtoThenSuccess() {
        EmployeeCreationDTO businessRelationManagerCreationDTO = prepareBusinessRelationManagerCreationDto();
        EmployeeCreatedDTO businessRelationManagerCreatedDTO = employeeService.save(businessRelationManagerCreationDTO);
        assertNotNull(employeeService.findInternal(businessRelationManagerCreatedDTO.getId()));
        assertNotNull(businessRelationManagerCreatedDTO);
    }

    @Test
    void saveFromDtoWithRoleAndPrivilege(){
        EmployeeCreationDTO businessRelationManagerCreationDTO = prepareBusinessRelationManagerCreationDto();
        EmployeeCreatedDTO businessRelationManagerCreatedDTO = employeeService.save(businessRelationManagerCreationDTO);
        BusinessRelationManager savedBRM = (BusinessRelationManager) employeeService.findInternal(businessRelationManagerCreatedDTO.getId());
        assertNotNull(savedBRM);
        User savedUser = savedBRM.getUser();
        assertNotNull(savedBRM);
        assertNotNull(savedUser.getRoles());;
    }

    private EmployeeCreationDTO prepareBusinessRelationManagerCreationDto() {
        EmployeeCreationDTO businessRelationManagerCreationDTO = new EmployeeCreationDTO();
        businessRelationManagerCreationDTO.setFirstName("BusinessRelationManagerFirstName");
        businessRelationManagerCreationDTO.setLastName("BusinessRelationManagerLastName");
        businessRelationManagerCreationDTO.setEmployeeType(EmployeeType.BusinessRelationManager);
        return businessRelationManagerCreationDTO;
    }

    @Override
    protected BusinessRelationManager createNewEntity() {
        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setFirstName("BusinessRelationManagerFirstName");
        businessRelationManager.setLastName("BusinessRelationManagerLastName");
        businessRelationManager.setUserName(businessRelationManager.getFirstName().concat(businessRelationManager.getLastName()).concat(String.valueOf(random.nextLong())));
        return businessRelationManager;
    }

    @Override
    protected IEmployeeService getService() {
        return employeeService;
    }
}