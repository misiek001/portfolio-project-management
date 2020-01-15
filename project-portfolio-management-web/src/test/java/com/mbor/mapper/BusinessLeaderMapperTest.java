package com.mbor.mapper;

import com.mbor.domain.BusinessEmployee;
import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.model.BusinessEmployeeDTO;
import com.mbor.model.BusinessLeaderDTO;
import com.mbor.spring.WebConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WebConfiguration.class)
@WebAppConfiguration
@ActiveProfiles("test")
class BusinessLeaderMapperTest {

    private static Random random = new Random();

    @Autowired
    BusinessLeaderMapper businessLeaderMapper;

    private static BusinessLeaderDTO businessLeaderDTO;

    private static BusinessEmployeeDTO businessEmployeeDTO;

    private static BusinessLeader businessLeader;

    private static BusinessEmployee businessEmployee;


    @BeforeAll
    static void init(){
        businessEmployeeDTO = new BusinessEmployeeDTO();
        businessEmployeeDTO.setId(random.nextLong());

        businessLeaderDTO = new BusinessLeaderDTO();

        businessLeaderDTO.setEmployee(businessEmployeeDTO);

        businessEmployee = new BusinessEmployee();
        businessEmployee.setId(businessEmployeeDTO.getId());



        businessLeader = new BusinessLeader();

        businessLeader.setEmployee(businessEmployee);


    }

    @Test
    void convertToDto() {
        BusinessLeaderDTO createdBusinessLeaderDTO = businessLeaderMapper.convertToDto(businessLeader);

        assertEquals(createdBusinessLeaderDTO.getEmployee().getId(), businessLeaderDTO.getEmployee().getId());

    }

    @Test
    void convertToEntity() {
        BusinessLeader createdBusinessLeader = businessLeaderMapper.convertToEntity(businessLeaderDTO);

        assertEquals(createdBusinessLeader.getId(), businessLeader.getId());
        assertEquals(createdBusinessLeader.getEmployee().getId(), businessLeader.getEmployee().getId());
    }
}