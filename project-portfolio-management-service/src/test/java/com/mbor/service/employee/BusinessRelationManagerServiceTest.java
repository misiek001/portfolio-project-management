package com.mbor.service.employee;

import com.mbor.configuration.ServiceMockConfiguration;
import com.mbor.configuration.TestConfiguration;
import com.mbor.dao.IEmployeeDao;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.Director;
import com.mbor.domain.Employee;
import com.mbor.entityFactory.TestObjectFactory;
import com.mbor.mapper.BusinessRelationManagerMapper;
import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;
import com.mbor.model.creation.EmployeeType;
import com.mbor.service.IEmployeeService;
import com.mbor.service.IServiceTestImpl;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, ServiceMockConfiguration.class, TestConfiguration.class})
@ActiveProfiles({"test", "employee-tests-mock"})
class BusinessRelationManagerServiceTest extends IServiceTestImpl<BusinessRelationManager> {

    @Autowired
    IEmployeeService employeeService;

    @Autowired
    IEmployeeDao employeeDao;

    @Autowired
    BusinessRelationManagerMapper businessRelationManagerMapper;

    @Autowired
    TestObjectFactory testObjectFactory;

    @BeforeAll
    static void init() {
        for(long i = 0; i < createdEntitiesNumber; i++){
            entityIdList.add(i);
        }
    }

    @Test
    void saveFromDtoThenSuccess() {
        EmployeeCreationDTO businessRelationManagerCreationDTO = testObjectFactory.prepareEmployeeCreationDTO(EmployeeType.BusinessRelationManager, "BRMFirstName", "BRMLastName");

        BusinessRelationManager businessRelationManagerFromCreationDTO = testObjectFactory.prepareBusinessRelationManagerFromEmployeeCreationDTO(businessRelationManagerCreationDTO);
        BusinessUnit businessUnit = testObjectFactory.prepareBusinessUnit();
        businessUnit.setId(prepareBusinessRelationManagerCreationDto().getBusinessUnitId());
        businessRelationManagerFromCreationDTO.setBusinessUnit(businessUnit);

        Director director = testObjectFactory.prepareDirector();
        director.setId(businessRelationManagerCreationDTO.getDirectorId());
        businessRelationManagerFromCreationDTO.setDirector(director);

        Optional<Employee> businessRelationManagerOptional = Optional.of(businessRelationManagerFromCreationDTO);

        EmployeeCreatedDTO businessRelationManagerCreatedDTO = new EmployeeCreatedDTO();
        businessRelationManagerCreatedDTO.setFirstName(businessRelationManagerCreationDTO.getFirstName());
        businessRelationManagerCreatedDTO.setLastName(businessRelationManagerCreationDTO.getLastName());
        businessRelationManagerCreatedDTO.setEmployeeType(businessRelationManagerCreationDTO.getEmployeeType());
        businessRelationManagerCreatedDTO.setBusinessUnitId(businessRelationManagerCreationDTO.getBusinessUnitId());

        when(businessRelationManagerMapper.convertCreationDtoToEntity(businessRelationManagerCreationDTO)).thenReturn(businessRelationManagerFromCreationDTO);
        when(employeeDao.save(businessRelationManagerFromCreationDTO)).thenReturn(businessRelationManagerOptional);
        when(businessRelationManagerMapper.convertEntityToCreatedDto(businessRelationManagerFromCreationDTO)).thenReturn(businessRelationManagerCreatedDTO);

        employeeService.save(businessRelationManagerCreationDTO);
    }


    private EmployeeCreationDTO prepareBusinessRelationManagerCreationDto() {
        EmployeeCreationDTO businessRelationManagerCreationDTO = new EmployeeCreationDTO();
        businessRelationManagerCreationDTO.setFirstName("BusinessRelationManagerFirstName");
        businessRelationManagerCreationDTO.setLastName("BusinessRelationManagerLastName");
        businessRelationManagerCreationDTO.setEmployeeType(EmployeeType.BusinessRelationManager);
        return businessRelationManagerCreationDTO;
    }

    @Override
    protected BusinessRelationManager createNewEntity() {
        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setFirstName("BusinessRelationManagerFirstName");
        businessRelationManager.setLastName("BusinessRelationManagerLastName");
        businessRelationManager.setUserName(businessRelationManager.getFirstName().concat(businessRelationManager.getLastName()).concat(String.valueOf(random.nextLong())));
        return businessRelationManager;
    }

    @Override
    protected IEmployeeService getService() {
        return employeeService;
    }

    @Override
    protected IEmployeeDao getDao() {
        return employeeDao;
    }
}