package com.mbor.service;

import com.mbor.configuration.ServiceMockConfiguration;
import com.mbor.configuration.TestConfiguration;
import com.mbor.dao.IProjectRequestDao;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.ProjectRequest;
import com.mbor.entityFactory.TestObjectFactory;
import com.mbor.exception.NoBRMAssignedToBusinessUnitException;
import com.mbor.mapper.project.ProjectRequestMapper;
import com.mbor.model.ProjectRequestDTO;
import com.mbor.model.creation.ProjectRequestCreationDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, TestConfiguration.class, ServiceMockConfiguration.class})
@ActiveProfiles({"test", "ProjectRequest-tests-mock"})
class ProjectRequestServiceTest extends IServiceTestImpl<ProjectRequest> {

    private static Long FIRST_BRM_ID = 1L;
    private static Long SECOND_BRM_ID = 2L;
    private static Long PROJECT_ID = 1L;
    private static Long FIRST_BUSINESS_UNIT_ID = 1L;
    private static Long SECOND_BUSINESS_UNIT_ID = 2L;

    @Autowired
    IProjectRequestService ProjectRequestService;
    @Autowired
    IBusinessUnitService businessUnitService;
    @Autowired
    IProjectRequestDao ProjectRequestDao;
    @Autowired
    ProjectRequestMapper projectRequestMapper;
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
        reset(ProjectRequestDao);
        reset(projectRequestMapper);
    }

    @AfterAll
    static void clear(){
        entityIdList.clear();
    }

    @Test
    void createProjectRequestThenSuccess() {
        ProjectRequestCreationDTO projectRequestCreationDTO = testObjectFactory.prepareProjectRequestCreationDTO("Project Name", "Project Description", FIRST_BUSINESS_UNIT_ID);
        ProjectRequest projectRequestFromCreationDTO = testObjectFactory.prepareProjectRequestFromCreationDTO(projectRequestCreationDTO);

        Optional<ProjectRequest> ProjectRequestOptional = Optional.of(projectRequestFromCreationDTO);

        BusinessUnit businessUnit = testObjectFactory.prepareBusinessUnit();
        businessUnit.setId(projectRequestCreationDTO.getBusinessUnitId());
        BusinessRelationManager businessRelationManager = testObjectFactory.prepareBusinessRelationManager();
        businessUnit.setBusinessRelationManager(businessRelationManager);
        projectRequestFromCreationDTO.setBusinessUnit(businessUnit);
        projectRequestFromCreationDTO.setBusinessRelationManager(businessRelationManager);

        com.mbor.model.creation.ProjectRequestDTO projectRequestDTO = testObjectFactory.prepareProjectRequestCreatedDTOFromProjectRequest(projectRequestFromCreationDTO);

        when(projectRequestMapper.convertCreationDtoToEntity(projectRequestCreationDTO)).thenReturn(projectRequestFromCreationDTO);
        when(businessUnitService.findInternal(projectRequestCreationDTO.getBusinessUnitId())).thenReturn(projectRequestFromCreationDTO.getBusinessUnit());
        when(ProjectRequestDao.save(projectRequestFromCreationDTO)).thenReturn(ProjectRequestOptional);
        when(projectRequestMapper.convertEntityToCreatedDto(projectRequestFromCreationDTO)).thenReturn(projectRequestDTO);

        com.mbor.model.creation.ProjectRequestDTO result = ProjectRequestService.save(projectRequestCreationDTO);

        verify(projectRequestMapper, times(1)).convertCreationDtoToEntity(any(ProjectRequestCreationDTO.class));
        verify(projectRequestMapper, times(1)).convertEntityToCreatedDto(any(ProjectRequest.class));
        verify(ProjectRequestDao, times(1)).save(any(ProjectRequest.class));

        assertEquals(result.getProjectName(), projectRequestDTO.getProjectName());
        assertEquals(result.getDescription(), projectRequestDTO.getDescription());
        assertEquals(result.getBusinessRelationManager().getUserName(), projectRequestDTO.getBusinessRelationManager().getUserName());
        assertEquals(result.getBusinessUnit().getName(), projectRequestDTO.getBusinessUnit().getName());
    }

    @Test
    void createProjectRequestThenException() {
        ProjectRequestCreationDTO projectRequestCreationDTO = testObjectFactory.prepareProjectRequestCreationDTO("Project Name", "Project Description", SECOND_BUSINESS_UNIT_ID);

        BusinessUnit businessUnit = testObjectFactory.prepareBusinessUnit();
        businessUnit.setId(projectRequestCreationDTO.getBusinessUnitId());

        ProjectRequest projectRequestFromCreationDTO = testObjectFactory.prepareProjectRequestFromCreationDTO(projectRequestCreationDTO);
        projectRequestFromCreationDTO.setBusinessUnit(businessUnit);

        when(projectRequestMapper.convertCreationDtoToEntity(projectRequestCreationDTO)).thenReturn(projectRequestFromCreationDTO);
        when(businessUnitService.findInternal(projectRequestCreationDTO.getBusinessUnitId())).thenReturn(projectRequestFromCreationDTO.getBusinessUnit());

        assertThrows(NoBRMAssignedToBusinessUnitException.class, () -> ProjectRequestService.save(projectRequestCreationDTO));
    }

    @Test
    void findAllProjectRequestsOfBRMWithNoProjectThenSuccess() {
        List<ProjectRequest> projectRequestList = new ArrayList<>();
        projectRequestList.add(testObjectFactory.prepareProjectRequest());
        projectRequestList.add(testObjectFactory.prepareProjectRequest());
        projectRequestList.add(testObjectFactory.prepareProjectRequest());

        List<ProjectRequestDTO> projectRequestDTOList = new ArrayList<>();
        projectRequestDTOList.add(testObjectFactory.prepareProjectRequestDTO());
        projectRequestDTOList.add(testObjectFactory.prepareProjectRequestDTO());
        projectRequestDTOList.add(testObjectFactory.prepareProjectRequestDTO());

        when(ProjectRequestDao.getAllProjectRequestsOfBRMWithNoProject(FIRST_BRM_ID)).thenReturn(projectRequestList);
        when(projectRequestMapper.convertEntityToDto(any(ProjectRequest.class))).thenReturn(projectRequestDTOList.get(0), projectRequestDTOList.get(1), projectRequestDTOList.get(2));

        assertEquals(3, ProjectRequestService.findAllProjectRequestsOfBRMWithNoProject(FIRST_BRM_ID).size());
    }

    @Override
    protected ProjectRequest createNewEntity() {
        return new ProjectRequest();
    }

    @Override
    protected IInternalService getService() {
        return ProjectRequestService;
    }

    @Override
    protected IProjectRequestDao getDao() {
        return ProjectRequestDao;
    }
}