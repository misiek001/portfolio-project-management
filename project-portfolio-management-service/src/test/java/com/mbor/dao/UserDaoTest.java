package com.mbor.dao;

import com.mbor.domain.Consultant;
import com.mbor.domain.User;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Rollback
class UserDaoTest {

    private static Long employeeID;

    @Autowired
    private IEmployeeDao employeeDao;

    @Autowired
    private UserDao userDao;

    @BeforeAll
    static void  setup(@Autowired IEmployeeDao employeeDao, @Autowired UserDao userDao){
        Random random = new Random();
        Consultant consultant = new Consultant();
        consultant.setUserName("ConsultantUserName" + random.nextLong());
        employeeID = employeeDao.save(consultant).get().getId();
    }

    @Test
    public void saveThenSuccess(){
        User user = new User();
        user.setEmployee(employeeDao.find(employeeID).get());
        assertNotNull(userDao.save(user).get());
    }

    @Test
    public void saveAndFindThenSuccess(){
        User user = new User();
        user.setEmployee(employeeDao.find(employeeID).get());
        String username = userDao.save(user).get().getEmployee().getUserName();

        assertNotNull(userDao.findByUsername(username).get());

    }

}