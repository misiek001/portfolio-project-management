package com.mbor.mapper;

import com.mbor.domain.BusinessEmployee;
import com.mbor.mapper.employee.BusinessEmployeeMapper;
import com.mbor.model.BusinessEmployeeDTO;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
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

        businessEmployee = new BusinessEmployee();
        businessEmployee.setId(businessEmployeeDTO.getId());
    }

    @Test
    void convertToDto() {
        BusinessEmployeeDTO mappedBusinessEmployeeDTO = businessEmployeeMapper.convertEntityToDto(businessEmployee);

        assertEquals(mappedBusinessEmployeeDTO.getId(), businessEmployeeDTO.getId());
    }


}