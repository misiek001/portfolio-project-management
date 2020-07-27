package com.mbor.mapper.businessUnit;

import com.mbor.configuration.TestConfiguration;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.Consultant;
import com.mbor.domain.Supervisor;
import com.mbor.entityFactory.TestObjectFactory;
import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.creation.BusinessUnitCreatedDTO;
import com.mbor.model.creation.BusinessUnitCreationDTO;
import com.mbor.spring.ModelMapperConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {ModelMapperConfiguration.class, TestConfiguration.class})
@ActiveProfiles("test")
class BusinessUnitMapperTest {

    @Autowired
    BusinessUnitMapper businessUnitMapper;

    static BusinessUnit businessUnit;
    static BusinessUnitCreationDTO businessUnitCreationDTO;

    @BeforeAll
    static void setup(@Autowired TestObjectFactory testObjectFactory){
        businessUnit = testObjectFactory.prepareBusinessUnit();

        Supervisor supervisor = testObjectFactory.prepareSupervisor();
        Consultant consultant = testObjectFactory.prepareConsultant();
        supervisor.setBusinessUnit(businessUnit);
        consultant.setBusinessUnit(businessUnit);

        businessUnit.setId(1L);

        businessUnitCreationDTO = testObjectFactory.prepareBusinessUnitCreationDto();
    }

    @Test
    void convertCreationDtoToEntity() {
        BusinessUnit businessUnitCreated = businessUnitMapper.convertCreationDtoToEntity(businessUnitCreationDTO);
        assertNotNull(businessUnitCreated);
    }

    @Test
    void convertEntityToCreatedDto() {
        BusinessUnitCreatedDTO businessUnitCreatedDTO = businessUnitMapper.convertEntityToCreatedDto(businessUnit);
        assertNotNull(businessUnitCreatedDTO);
    }

    @Test
    void convertEntityToDto() {
     BusinessUnitDTO businessUnitDTO =  businessUnitMapper.convertEntityToDto(businessUnit);
     assertNotNull(businessUnitDTO);
    }
}