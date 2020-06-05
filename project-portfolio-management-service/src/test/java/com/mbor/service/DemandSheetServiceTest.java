package com.mbor.service;

import com.mbor.configuration.ServiceMockConfiguration;
import com.mbor.configuration.TestConfiguration;
import com.mbor.dao.IDemandSheetDao;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.DemandSheet;
import com.mbor.entityFactory.TestObjectFactory;
import com.mbor.exception.NoBRMAssignedToBusinessUnitException;
import com.mbor.mapper.DemandSheetMapper;
import com.mbor.model.DemandSheetDTO;
import com.mbor.model.creation.DemandSheetCreatedDTO;
import com.mbor.model.creation.DemandSheetCreationDTO;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, TestConfiguration.class, ServiceMockConfiguration.class})
@ActiveProfiles({"test", "demandsheet-tests-mock"})
class DemandSheetServiceTest extends IServiceTestImpl<DemandSheet> {

    private static Long FIRST_BRM_ID = 1L;
    private static Long SECOND_BRM_ID = 2L;
    private static Long PROJECT_ID = 1L;
    private static Long FIRST_BUSINESS_UNIT_ID = 1L;
    private static Long SECOND_BUSINESS_UNIT_ID = 2L;

    @Autowired
    IDemandSheetService demandSheetService;
    @Autowired
    IBusinessUnitService businessUnitService;
    @Autowired
    IDemandSheetDao demandSheetDao;
    @Autowired
    DemandSheetMapper demandSheetMapper;
    @Autowired
    TestObjectFactory testObjectFactory;

    @BeforeAll
    static void init() {
        for (long i = 0; i < createdEntitiesNumber; i++) {
            entityIdList.add(i);
        }
    }

    @AfterEach
    void resetMock() {
        reset(businessUnitService);
        reset(demandSheetDao);
        reset(demandSheetMapper);
    }

    @Test
    void createDemandSheetThenSuccess() {
        DemandSheetCreationDTO demandSheetCreationDTO = testObjectFactory.prepareDemandSheetCreationDTO("Project Name", "Project Description", FIRST_BUSINESS_UNIT_ID);
        DemandSheet demandSheetFromCreationDTO = testObjectFactory.prepareDemandSheetFromCreationDTO(demandSheetCreationDTO);

        Optional<DemandSheet> demandSheetOptional = Optional.of(demandSheetFromCreationDTO);

        BusinessUnit businessUnit = testObjectFactory.prepareBusinessUnit();
        businessUnit.setId(demandSheetCreationDTO.getBusinessUnitId());
        BusinessRelationManager businessRelationManager = testObjectFactory.prepareBusinessRelationManager();
        businessUnit.setBusinessRelationManager(businessRelationManager);
        demandSheetFromCreationDTO.setBusinessUnit(businessUnit);
        demandSheetFromCreationDTO.setBusinessRelationManager(businessRelationManager);

        DemandSheetCreatedDTO demandSheetCreatedDTO = testObjectFactory.prepareDemandSheetCreatedDTOFromDemandSheet(demandSheetFromCreationDTO);

        when(demandSheetMapper.convertCreationDtoToEntity(demandSheetCreationDTO)).thenReturn(demandSheetFromCreationDTO);
        when(businessUnitService.findInternal(demandSheetCreationDTO.getBusinessUnitId())).thenReturn(demandSheetFromCreationDTO.getBusinessUnit());
        when(demandSheetDao.save(demandSheetFromCreationDTO)).thenReturn(demandSheetOptional);
        when(demandSheetMapper.convertEntityToCreatedDto(demandSheetFromCreationDTO)).thenReturn(demandSheetCreatedDTO);

        DemandSheetCreatedDTO result = demandSheetService.save(demandSheetCreationDTO);

        verify(demandSheetMapper, times(1)).convertCreationDtoToEntity(any(DemandSheetCreationDTO.class));
        verify(demandSheetMapper, times(1)).convertEntityToCreatedDto(any(DemandSheet.class));
        verify(demandSheetDao, times(1)).save(any(DemandSheet.class));

        assertEquals(result.getProjectName(), demandSheetCreatedDTO.getProjectName());
        assertEquals(result.getDescription(), demandSheetCreatedDTO.getDescription());
        assertEquals(result.getBusinessRelationManager().getUserName(), demandSheetCreatedDTO.getBusinessRelationManager().getUserName());
        assertEquals(result.getBusinessUnit().getName(), demandSheetCreatedDTO.getBusinessUnit().getName());
    }

    @Test
    void createDemandSheetThenException() {
        DemandSheetCreationDTO demandSheetCreationDTO = testObjectFactory.prepareDemandSheetCreationDTO("Project Name", "Project Description", SECOND_BUSINESS_UNIT_ID);

        BusinessUnit businessUnit = testObjectFactory.prepareBusinessUnit();
        businessUnit.setId(demandSheetCreationDTO.getBusinessUnitId());

        DemandSheet demandSheetFromCreationDTO = testObjectFactory.prepareDemandSheetFromCreationDTO(demandSheetCreationDTO);
        demandSheetFromCreationDTO.setBusinessUnit(businessUnit);

        when(demandSheetMapper.convertCreationDtoToEntity(demandSheetCreationDTO)).thenReturn(demandSheetFromCreationDTO);
        when(businessUnitService.findInternal(demandSheetCreationDTO.getBusinessUnitId())).thenReturn(demandSheetFromCreationDTO.getBusinessUnit());

        assertThrows(NoBRMAssignedToBusinessUnitException.class, () -> demandSheetService.save(demandSheetCreationDTO));
    }

    @Test
    void findAllDemandSheetsOfBRMWithNoProjectThenSuccess() {
        List<DemandSheet> demandSheetList = new ArrayList<>();
        demandSheetList.add(testObjectFactory.prepareDemandSheet());
        demandSheetList.add(testObjectFactory.prepareDemandSheet());
        demandSheetList.add(testObjectFactory.prepareDemandSheet());

        List<DemandSheetDTO> demandSheetDTOList = new ArrayList<>();
        demandSheetDTOList.add(testObjectFactory.prepareDemandSheetDTO());
        demandSheetDTOList.add(testObjectFactory.prepareDemandSheetDTO());
        demandSheetDTOList.add(testObjectFactory.prepareDemandSheetDTO());

        when(demandSheetDao.getAllDemandSheetsOfBRMWithNoProject(FIRST_BRM_ID)).thenReturn(demandSheetList);
        when(demandSheetMapper.convertToDto(any(DemandSheet.class))).thenReturn(demandSheetDTOList.get(0), demandSheetDTOList.get(1), demandSheetDTOList.get(2));

        assertEquals(3, demandSheetService.findAllDemandSheetsOfBRMWithNoProject(FIRST_BRM_ID).size());
    }

    @Override
    protected DemandSheet createNewEntity() {
        return new DemandSheet();
    }

    @Override
    protected IInternalService getService() {
        return demandSheetService;
    }

    @Override
    protected IDemandSheetDao getDao() {
        return demandSheetDao;
    }
}