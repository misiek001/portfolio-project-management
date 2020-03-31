package com.mbor.service.employee;

import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.Consultant;
import com.mbor.model.BusinessRelationManagerDTO;
import com.mbor.model.ConsultantDTO;
import com.mbor.model.EmployeeDTO;
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
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@ActiveProfiles("test")
class EmployeeServiceTest {

    @Autowired
    IEmployeeService employeeService;

    private static Random random = new Random();

    private static Long firstEntityId;

    private static Long secondEntityId;



    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setUserName("BusinessRelationManager" + random.nextLong());
        entityManager.persist(businessRelationManager);
        firstEntityId = businessRelationManager.getId();

        Consultant consultant = new Consultant();
        consultant.setUserName("ConsultantUserName" + random.nextLong());
        entityManager.persist(consultant);
        secondEntityId = consultant.getId();

        transaction.commit();
    }

    @Test
    void findEmployeeThenSuccess(){
        EmployeeDTO businessRelationManagerDTO = employeeService.find(firstEntityId);
        assertTrue(businessRelationManagerDTO instanceof BusinessRelationManagerDTO);

        EmployeeDTO consultantDTO = employeeService.find(secondEntityId);
        assertTrue(consultantDTO instanceof ConsultantDTO);
    }

    @Test
    void findAllEmployeeThenSuccess(){
        List<EmployeeDTO> result = employeeService.findAll();
        List<BusinessRelationManagerDTO> businessRelationManagerDTOList = result.stream()
                .filter(employeeDTO -> employeeDTO instanceof BusinessRelationManagerDTO)
                .map(employeeDTO -> ( BusinessRelationManagerDTO) employeeDTO)
                .collect(Collectors.toList());
        List<ConsultantDTO> consultantDTOList = result.stream()
                .filter(employeeDTO -> employeeDTO instanceof ConsultantDTO)
                .map(employeeDTO -> (ConsultantDTO) employeeDTO)
                .collect(Collectors.toList());
        assertEquals(result.size(), (businessRelationManagerDTOList.size() + consultantDTOList.size()));
    }

}