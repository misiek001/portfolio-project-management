package com.mbor.service;

import com.mbor.configuration.ServiceMockConfiguration;
import com.mbor.configuration.TestConfiguration;
import com.mbor.dao.IBusinessUnitDao;
import com.mbor.domain.BusinessUnit;
import com.mbor.entityFactory.TestObjectFactory;
import com.mbor.mapper.businessUnit.BusinessUnitMapper;
import com.mbor.model.creation.BusinessUnitCreatedDTO;
import com.mbor.model.creation.BusinessUnitCreationDTO;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, TestConfiguration.class, ServiceMockConfiguration.class})
@ActiveProfiles({"test", "businessunit-tests-mock"})
class BusinessUnitServiceTest extends IServiceTestImpl {

    @Autowired
    IBusinessUnitService businessUnitService;

    @Autowired
    IBusinessUnitDao businessUnitDao;

    @Autowired
    BusinessUnitMapper businessUnitMapper;

    @Autowired
    TestObjectFactory testObjectFactory;

    @BeforeAll
    static void init() {
        for(long i = 0; i < createdEntitiesNumber; i++){
            entityIdList.add(i);
        }
    }

    @AfterEach
    void resetMock(){
        reset(businessUnitDao);
        reset(businessUnitMapper);
    }

    @AfterAll
    static void clear(){
        entityIdList.clear();
    }

    @Test
    void saveFromDtoThenSuccess() {
        BusinessUnitCreationDTO businessUnitCreationDTO = testObjectFactory.prepareBusinessUnitCreationDto();
        BusinessUnit businessUnitFromCreationDTO = testObjectFactory.prepareBusinessUnitFromCreationDTO(businessUnitCreationDTO);
        Optional<BusinessUnit> optionalBusinessUnit = Optional.of(businessUnitFromCreationDTO);

        BusinessUnitCreatedDTO businessUnitCreatedDTO = testObjectFactory.prepareBusinessUnitCreatedDTOFromEntity(businessUnitFromCreationDTO);

        when(businessUnitMapper.convertCreationDtoToEntity(businessUnitCreationDTO)).thenReturn(businessUnitFromCreationDTO);
        when(businessUnitDao.save(businessUnitFromCreationDTO)).thenReturn(optionalBusinessUnit);
        when(businessUnitMapper.convertEntityToCreatedDto(businessUnitFromCreationDTO)).thenReturn(businessUnitCreatedDTO);
        BusinessUnitCreatedDTO result =businessUnitService.save(businessUnitCreationDTO);

        assertNotNull(result);
        assertEquals(businessUnitCreatedDTO.getName(),result.getName());
    }

    @Override
    public BusinessUnit createNewEntity() {
        return testObjectFactory.prepareBusinessUnit();
    }

    @Override
    protected IBusinessUnitService getService() {
        return businessUnitService;
    }

    @Override
    protected IBusinessUnitDao getDao() {
        return businessUnitDao;
    }
}