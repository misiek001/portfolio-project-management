package com.mbor.mapper;

import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.Director;
import com.mbor.mapper.employee.BusinessRelationManagerMapper;
import com.mbor.model.BusinessRelationManagerDTO;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.mbor.mapper.MapperUtils.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
class BusinessRelationManagerMapperTest {

    private static final long BRM_ID = 1L;
    @Autowired
    BusinessRelationManagerMapper businessRelationManagerMapper;

    private static BusinessUnit ITBusinessUnit;
    private static BusinessUnit firstBusinessUnit;
    private static BusinessUnit secondBusinessUnit;

    private static BusinessRelationManager businessRelationManager;
    private static Director director;

    @BeforeAll
    static void init(){

        ITBusinessUnit = new BusinessUnit();
        ITBusinessUnit.setId(IT_BUSINESS_UNIT_ID);
        ITBusinessUnit.setName(IT_BUSINESS_UNIT_NAME);

        director = new Director();
        director.setId(DIRECTOR_ID);
        director.setUserName(DIRECTOR_USER_NAME);
        director.setBusinessUnit(ITBusinessUnit);
        ITBusinessUnit.getEmployees().add(director);

        businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setId(BRM_ID);
        businessRelationManager.setUserName(BRM_USER_NAME);
        businessRelationManager.setBusinessUnit(ITBusinessUnit);
        businessRelationManager.setDirector(director);

        firstBusinessUnit = new BusinessUnit();
        firstBusinessUnit.setId(FIRST_BUSINESS_UNIT_ID);
        firstBusinessUnit.setName(FIRST_BUSINESS_UNIT_NAME);

        secondBusinessUnit = new BusinessUnit();
        secondBusinessUnit.setId(SECOND_BUSINESS_UNIT_ID);
        secondBusinessUnit.setName(SECOND_BUSINESS_UNIT_NAME);

        businessRelationManager.addAssignedBusinessUnit(firstBusinessUnit);
        businessRelationManager.addAssignedBusinessUnit(secondBusinessUnit);

    }

    @Test
    void convertToDto() {
        BusinessRelationManagerDTO result = businessRelationManagerMapper.convertEntityToDto(businessRelationManager);
    }

    @Test
    void convertToEntity() {
    }
}