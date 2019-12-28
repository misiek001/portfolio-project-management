package com.misiek.mapping;

import com.misiek.domain.BusinessEmployee;
import com.misiek.model.BusinessEmployeeDTO;
import com.misiek.spring.WebConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WebConfiguration.class)
@WebAppConfiguration
class BusinessEmployeeMapperTest {

    private static Random random = new Random();

    @Autowired
    BusinessEmployeeMapper businessEmployeeMapper;

    private static BusinessEmployeeDTO businessEmployeeDTO;

    private static BusinessEmployee businessEmployee;

    @BeforeAll
    static void init(){
        businessEmployeeDTO = new BusinessEmployeeDTO();
        businessEmployeeDTO.setId(random.nextLong());
        businessEmployeeDTO.setFirstName("BL First Name");
        businessEmployeeDTO.setLastName("BL Last Name");

        businessEmployee = new BusinessEmployee();
        businessEmployee.setId(businessEmployeeDTO.getId());
        businessEmployee.setFirstName(businessEmployeeDTO.getFirstName());
        businessEmployee.setLastName(businessEmployeeDTO.getLastName());
    }

    @Test
    void convertToDto() {
        BusinessEmployeeDTO createdBusinessEmployeeDTO = businessEmployeeMapper.convertToDto(businessEmployee);

        assertEquals(createdBusinessEmployeeDTO.getId(), businessEmployeeDTO.getId());
        assertEquals(createdBusinessEmployeeDTO.getFirstName(), businessEmployeeDTO.getFirstName());
        assertEquals(createdBusinessEmployeeDTO.getLastName(), businessEmployeeDTO.getLastName());
    }

    @Test
    void convertToEntity() {
        BusinessEmployee createdBusinessEmployee = businessEmployeeMapper.convertToEntity((businessEmployeeDTO));

        assertEquals(createdBusinessEmployee.getId(), businessEmployee.getId());
        assertEquals(createdBusinessEmployee.getFirstName(), businessEmployee.getFirstName());
        assertEquals(createdBusinessEmployee.getLastName(), businessEmployee.getLastName());
    }
}