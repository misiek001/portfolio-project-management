package com.mbor.service;

import com.mbor.configuration.ServiceMockConfiguration;
import com.mbor.configuration.TestConfiguration;
import com.mbor.dao.IDao;
import com.mbor.domain.BusinessUnit;
import com.mbor.model.creation.BusinessUnitCreatedDTO;
import com.mbor.model.creation.BusinessUnitCreationDTO;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, TestConfiguration.class, ServiceMockConfiguration.class})
@ActiveProfiles("test")
class BusinessUnitServiceTest extends IServiceTestImpl {

    @Autowired
    private IBusinessUnitService businessUnitService;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) {

    }

    @Test
    void saveFromDtoThenSuccess() {
        BusinessUnitCreationDTO businessUnitCreationDTO = prepareBusinessUnitCreationDto();
        BusinessUnitCreatedDTO businessUnitCreatedDTO = businessUnitService.save(businessUnitCreationDTO);
        assertNotNull(businessUnitCreatedDTO);
        assertNotNull(businessUnitService.findInternal(businessUnitCreatedDTO.getId()));
    }

    private BusinessUnitCreationDTO prepareBusinessUnitCreationDto() {
        BusinessUnitCreationDTO businessUnitCreationDTO = new BusinessUnitCreationDTO();
        businessUnitCreationDTO.setName("BusinessUnitName" + random.nextLong());
        return businessUnitCreationDTO;
    }

    @Override
    public BusinessUnit createNewEntity() {
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName("BusinessUnitName" + random.nextLong());
        return businessUnit;
    }

    @Override
    protected IBusinessUnitService getService() {
        return businessUnitService;
    }

    @Override
    protected IDao getDao() {
        return null;
    }
}