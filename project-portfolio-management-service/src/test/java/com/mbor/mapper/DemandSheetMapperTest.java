package com.mbor.mapper;

import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.DemandSheet;
import com.mbor.domain.Project;
import com.mbor.model.DemandSheetDTO;
import com.mbor.model.creation.DemandSheetCreatedDTO;
import com.mbor.model.creation.DemandSheetCreationDTO;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
class DemandSheetMapperTest {

    private static DemandSheetCreationDTO demandSheetCreationDTO;
    private static DemandSheet demandSheet;
    private static BusinessUnit businessUnit;
    private static Project project;

    private static BusinessRelationManager businessRelationManager;
    private static Long DEMAND_SHEET_ID = 1L;
    private static String PROJECT_NAME = "Project Name";
    private static String PROJECT_DESCRIPTION = "Project Description";
    private static Long BUSINESS_UNIT_ID = 1L;
    private static Long BUSINESS_RELATION_MANAGER_ID = 1L;
    private static Long PROJECT_ID = 1l;
    @Autowired
    DemandSheetMapper demandSheetMapper;

    @BeforeAll
    static void init() {

        businessUnit = new BusinessUnit();
        businessUnit.setId(BUSINESS_UNIT_ID);

        businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setId(BUSINESS_RELATION_MANAGER_ID);
        businessRelationManager.addAssignedBusinessUnit(businessUnit);

        demandSheetCreationDTO = new DemandSheetCreationDTO();
        demandSheetCreationDTO.setProjectName(PROJECT_NAME);
        demandSheetCreationDTO.setDescription(PROJECT_DESCRIPTION);
        demandSheetCreationDTO.setBusinessUnitId(BUSINESS_UNIT_ID);

        demandSheet = new DemandSheet();
        demandSheet.setId(DEMAND_SHEET_ID);
        demandSheet.setProjectName(PROJECT_NAME);
        demandSheet.setDescription(PROJECT_DESCRIPTION);
        demandSheet.setBusinessUnit(businessUnit);
        demandSheet.setBusinessRelationManager(businessRelationManager);

        project = new Project();
        project.setId(PROJECT_ID);
        project.setDemandSheet(demandSheet);
        project.setBusinessRelationManager(businessRelationManager);
    }

    @Test
    void convertCreationDtoToEntityThenSuccess() {
        DemandSheet result = demandSheetMapper.convertCreationDtoToEntity(demandSheetCreationDTO);

        assertEquals(PROJECT_NAME, result.getProjectName());
        assertEquals(PROJECT_DESCRIPTION, result.getDescription());
    }

    @Test
    void convertEntityToCreatedDtoThenSuccess() {
        DemandSheetCreatedDTO result = demandSheetMapper.convertEntityToCreatedDto(demandSheet);

        assertEquals(demandSheet.getId(), result.getId());
        assertEquals(demandSheet.getProjectName(), result.getProjectName());
        assertEquals(demandSheet.getDescription(), result.getDescription());
        assertEquals(demandSheet.getBusinessRelationManager().getId(), result.getBusinessRelationManager().getId());
        assertEquals(demandSheet.getBusinessUnit().getId(), result.getBusinessUnit().getId());
    }

    @Test
    void convertEntityToDtoThenSuccess() {
        DemandSheetDTO result = demandSheetMapper.convertToDto(demandSheet);

        assertEquals(demandSheet.getId(), result.getId());
        assertEquals(demandSheet.getProjectName(), result.getProjectName());
        assertEquals(demandSheet.getDescription(), result.getDescription());
        assertEquals(demandSheet.getBusinessRelationManager().getId(), result.getBusinessRelationManager().getId());
        assertEquals(demandSheet.getBusinessUnit().getId(), result.getBusinessUnit().getId());
        assertEquals(demandSheet.getProject().getId(), result.getProject().getId());
    }
}