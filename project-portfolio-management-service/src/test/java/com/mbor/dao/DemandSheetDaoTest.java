package com.mbor.dao;

import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.DemandSheet;
import com.mbor.domain.Project;
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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
@Transactional
class DemandSheetDaoTest  extends IDaoImplTest<DemandSheet> {

    @Autowired
    private IDemandSheetDao demandSheetDao;

    private static List<Long> demandSheetIdList = new LinkedList<>();

    @Override
    protected IDao getDao() {
        return demandSheetDao;
    }

    private static Long FIRST_BRM_ID;

    private static Long SECOND_BRM_ID;

    private static Long PROJECT_ID;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < IDaoImplTest.createdEntitiesNumber; i++) {
            DemandSheet demandSheet = new DemandSheet();
            demandSheet.setProjectName("Project Name" + random.nextLong() );
            demandSheet.setDescription("Project Description" + random.nextLong());
            entityManager.persist(demandSheet);
            demandSheetIdList.add(demandSheet.getId());
        }
        BusinessRelationManager firstBRM = new BusinessRelationManager();
        entityManager.persist(firstBRM);
        FIRST_BRM_ID = firstBRM.getId();

        BusinessRelationManager secondBRM = new BusinessRelationManager();
        entityManager.persist(secondBRM);
        SECOND_BRM_ID = secondBRM.getId();

        Project project = new Project();
        entityManager.persist(project);
        PROJECT_ID = project.getId();

        DemandSheet demandSheetWithBRMAndNoProject = entityManager.find(DemandSheet.class, demandSheetIdList.get(0));
        demandSheetWithBRMAndNoProject.setBusinessRelationManager(entityManager.find(BusinessRelationManager.class, FIRST_BRM_ID));

        DemandSheet demandSheetWithBRMAndProject = entityManager.find(DemandSheet.class, demandSheetIdList.get(1));
        demandSheetWithBRMAndProject.setBusinessRelationManager(entityManager.find(BusinessRelationManager.class, FIRST_BRM_ID));
        entityManager.find(Project.class, PROJECT_ID).setDemandSheet(demandSheetWithBRMAndProject);

        DemandSheet demandSheetWithOtherBRMAndNoProject = entityManager.find(DemandSheet.class, demandSheetIdList.get(2));
        demandSheetWithOtherBRMAndNoProject.setBusinessRelationManager(entityManager.find(BusinessRelationManager.class, SECOND_BRM_ID));

        transaction.commit();
    }

    @Test
    public void  getAllDemandSheetsOfBRMWithNoProjectThenSuccess(){
        demandSheetDao.findAll();
        assertEquals(1, demandSheetDao.getAllDemandSheetsOfBRMWithNoProject(FIRST_BRM_ID).size());
    }

    @Override
    protected DemandSheet createNewEntity() {
        Random random = new Random();
        DemandSheet demandSheet = new DemandSheet();
        demandSheet.setProjectName("Project Name" + random.nextLong() );
        demandSheet.setDescription("Project Description" + random.nextLong());
        return demandSheet;
    }

}