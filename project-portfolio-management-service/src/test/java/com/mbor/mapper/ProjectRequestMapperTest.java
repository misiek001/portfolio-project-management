package com.mbor.mapper;

import com.mbor.configuration.TestConfiguration;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.ProjectRequest;
import com.mbor.domain.Project;
import com.mbor.entityFactory.TestObjectFactory;
import com.mbor.mapper.project.ProjectRequestMapper;
import com.mbor.model.ProjectRequestDTO;
import com.mbor.model.creation.ProjectRequestCreationDTO;
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
@ContextConfiguration(classes = {ServiceConfiguration.class, TestConfiguration.class})
@ActiveProfiles("test")
class ProjectRequestMapperTest {

    private static ProjectRequestCreationDTO projectRequestCreationDTO;
    private static ProjectRequest projectRequest;
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
    ProjectRequestMapper projectRequestMapper;

    @BeforeAll
    static void init(@Autowired TestObjectFactory testObjectFactory) {

        businessUnit = testObjectFactory.prepareBusinessUnit("BusinessUnit");
        businessUnit.setId(BUSINESS_UNIT_ID);

        businessRelationManager = testObjectFactory.prepareBusinessRelationManager("BusinessRelationManager");
        businessRelationManager.setId(BUSINESS_RELATION_MANAGER_ID);
        businessRelationManager.addAssignedBusinessUnit(businessUnit);

        projectRequestCreationDTO = new ProjectRequestCreationDTO();
        projectRequestCreationDTO.setProjectName(PROJECT_NAME);
        projectRequestCreationDTO.setDescription(PROJECT_DESCRIPTION);
        projectRequestCreationDTO.setBusinessUnitId(BUSINESS_UNIT_ID);

        projectRequest = new ProjectRequest();
        projectRequest.setId(DEMAND_SHEET_ID);
        projectRequest.setProjectName(PROJECT_NAME);
        projectRequest.setDescription(PROJECT_DESCRIPTION);
        projectRequest.setBusinessUnit(businessUnit);
        projectRequest.setBusinessRelationManager(businessRelationManager);

        project = testObjectFactory.prepareProject();
        project.setId(PROJECT_ID);
        project.setProjectRequest(projectRequest);
        project.setBusinessRelationManager(businessRelationManager);
    }

    @Test
    void convertCreationDtoToEntityThenSuccess() {
        ProjectRequest result = projectRequestMapper.convertCreationDtoToEntity(projectRequestCreationDTO);

        assertEquals(PROJECT_NAME, result.getProjectName());
        assertEquals(PROJECT_DESCRIPTION, result.getDescription());
    }


    @Test
    void convertEntityToDtoThenSuccess() {
        ProjectRequestDTO result = projectRequestMapper.convertEntityToDto(projectRequest);

        assertEquals(projectRequest.getId(), result.getId());
        assertEquals(projectRequest.getProjectName(), result.getProjectName());
        assertEquals(projectRequest.getDescription(), result.getDescription());
        assertEquals(projectRequest.getBusinessRelationManager().getId(), result.getBusinessRelationManager().getId());
        assertEquals(projectRequest.getBusinessUnit().getId(), result.getBusinessUnit().getId());
        assertEquals(projectRequest.getProject().getId(), result.getProject().getId());
    }
}