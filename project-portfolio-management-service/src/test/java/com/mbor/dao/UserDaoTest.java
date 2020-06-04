package com.mbor.dao;

import com.mbor.configuration.TestConfiguration;
import com.mbor.domain.Consultant;
import com.mbor.domain.Employee;
import com.mbor.domain.security.User;
import com.mbor.entityFactory.TestObjectFactory;
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
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ServiceConfiguration.class, TestConfiguration.class})
@ActiveProfiles("test")
@Transactional
class UserDaoTest {

    private static Long employeeID;

    @Autowired
    private IEmployeeDao employeeDao;

    @Autowired
    private IUserDao userDao;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory, @Autowired TestObjectFactory testObjectsFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        Random random = new Random();
        Consultant consultant = new Consultant();
        consultant.setUserName("ConsultantUserName" + random.nextLong());
        transaction.begin();
        entityManager.persist(consultant);;
        employeeID = consultant.getId();
        transaction.commit();
    }


    @Test
    public void saveThenSuccess() {
        User user = new User();
        Optional<Employee> employeeOptional = employeeDao.find(employeeID);
        if(!employeeOptional.isPresent()){
            fail();
        }
        user.setEmployee(employeeOptional.get());
        assertNotNull(userDao.save(user).get());
    }

    @Test
    public void saveAndFindThenSuccess() {
        User user = new User();
        Optional<Employee> employeeOptional = employeeDao.find(employeeID);
        if(!employeeOptional.isPresent()){
            fail();
        }
        user.setEmployee(employeeOptional.get());
        String username = userDao.save(user).get().getEmployee().getUserName();
        assertNotNull(userDao.findByUsername(username).get());
    }

    @Test
    public void deleteThenSuccess(){
        User user = new User();
        Optional<Employee> employeeOptional = employeeDao.find(employeeID);
        if(!employeeOptional.isPresent()){
            fail();
        }
        Long userId = userDao.save(user).get().getId();
        userDao.delete(userId);
        assertFalse(userDao.find(userId).isPresent());
    }
}