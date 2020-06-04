package com.mbor.service;

import com.mbor.dao.IDao;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.DemandSheet;
import com.mbor.domain.Project;
import com.mbor.exception.NoBRMAssignedToBusinessUnitException;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@ActiveProfiles("test")
class DemandSheetServiceTest extends IServiceTestImpl<DemandSheet> {

    @Autowired
    IDemandSheetService demandSheetService;

    private static Long FIRST_BRM_ID;
    private static Long SECOND_BRM_ID;

    private static Long PROJECT_ID;

    private static Long FIRST_BUSINESS_UNIT_ID;
    private static Long SECOND_BUSINESS_UNIT_ID;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < createdEntitiesNumber; i++) {
            DemandSheet demandSheet = new DemandSheet();
            demandSheet.setProjectName("Project Name" + random.nextLong());
            demandSheet.setDescription("Description" + random.nextLong());
            entityManager.persist(demandSheet);
            entityIdList.add(demandSheet.getId());
        }
        firstEntityId = entityIdList.get(0);

        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setUserName("BusinessRelationManager" + random.nextLong());
        entityManager.persist(businessRelationManager);
        FIRST_BRM_ID = businessRelationManager.getId();

        BusinessUnit firstBusinessUnit = new BusinessUnit();
        firstBusinessUnit.setName("Business Unit" + random.nextLong());
        firstBusinessUnit.setBusinessRelationManager(businessRelationManager);
        entityManager.persist(firstBusinessUnit);
        FIRST_BUSINESS_UNIT_ID = firstBusinessUnit.getId();

        BusinessUnit secondBusinessUnit = new BusinessUnit();
        secondBusinessUnit.setName("Business Unit" + random.nextLong());
        entityManager.persist(secondBusinessUnit);
        SECOND_BUSINESS_UNIT_ID = secondBusinessUnit.getId();

        BusinessRelationManager secondBRM = new BusinessRelationManager();
        entityManager.persist(secondBRM);
        SECOND_BRM_ID = secondBRM.getId();

        Project project = new Project();
        entityManager.persist(project);
        PROJECT_ID = project.getId();

        DemandSheet demandSheetWithBRMAndNoProject = entityManager.find(DemandSheet.class, entityIdList.get(0));
        demandSheetWithBRMAndNoProject.setBusinessRelationManager(entityManager.find(BusinessRelationManager.class, FIRST_BRM_ID));

        DemandSheet demandSheetWithBRMAndProject = entityManager.find(DemandSheet.class, entityIdList.get(1));
        demandSheetWithBRMAndProject.setBusinessRelationManager(entityManager.find(BusinessRelationManager.class, FIRST_BRM_ID));
        entityManager.find(Project.class, PROJECT_ID).setDemandSheet(demandSheetWithBRMAndProject);

        DemandSheet demandSheetWithOtherBRMAndNoProject = entityManager.find(DemandSheet.class, entityIdList.get(2));
        demandSheetWithOtherBRMAndNoProject.setBusinessRelationManager(entityManager.find(BusinessRelationManager.class, SECOND_BRM_ID));

        transaction.commit();
    }

    @Test
    void getAllDemandSheetsOfBRMWithNoProjectThenSuccess(){
        assertEquals(1, demandSheetService.getAllDemandSheetsOfBRMWithNoProject(FIRST_BRM_ID).size());
    }


    @Test
    void createDemandSheetThenSuccess() {
        DemandSheetCreationDTO demandSheetCreationDTO =  prepareDemandSheetCreationDTO("Project Name", "Project Description", FIRST_BUSINESS_UNIT_ID);
        DemandSheetCreatedDTO result = demandSheetService.save(demandSheetCreationDTO);

        assertNotNull(result.getId());
        assertEquals(demandSheetCreationDTO.getProjectName(), result.getProjectName());
        assertEquals(demandSheetCreationDTO.getDescription(), result.getDescription());
        assertEquals(FIRST_BUSINESS_UNIT_ID, result.getBusinessUnit().getId());
        assertEquals(FIRST_BRM_ID, result.getBusinessRelationManager().getId());
    }

    @Test
    void createDemandSheetThenException(){
        DemandSheetCreationDTO demandSheetCreationDTO =  prepareDemandSheetCreationDTO("Project Name", "Project Description", SECOND_BUSINESS_UNIT_ID);
        assertThrows(NoBRMAssignedToBusinessUnitException.class, () -> demandSheetService.save(demandSheetCreationDTO));
    }


    private DemandSheetCreationDTO prepareDemandSheetCreationDTO(String projectName, String projectDescription, Long businessUnitId) {
        DemandSheetCreationDTO demandSheetCreationDTO = new DemandSheetCreationDTO();
        demandSheetCreationDTO.setProjectName(projectName);
        demandSheetCreationDTO.setDescription(projectDescription);
        demandSheetCreationDTO.setBusinessUnitId(businessUnitId);
        return demandSheetCreationDTO;
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
    protected IDao getDao() {
        return null;
    }
}