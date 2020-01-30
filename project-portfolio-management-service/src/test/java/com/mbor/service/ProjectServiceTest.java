package com.mbor.service;

import com.mbor.dao.IEmployeeDao;
import com.mbor.dao.IProjectDao;
import com.mbor.domain.BusinessEmployee;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.Project;
import com.mbor.model.BusinessEmployeeDTO;
import com.mbor.model.BusinessLeaderDTO;
import com.mbor.model.BusinessRelationManagerDTO;
import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@ActiveProfiles("test")
public class ProjectServiceTest {

    private static final int createdEntitiesNumber = 3;

    private static Random random = new Random();

    private static Long businessEmployeeId;

    private static Long businessRelationManagerId;

    private static Long firstBusinessUnitId;

    private static Long secondBusinessUnitId;

    @Autowired
    IProjectService projectService;

    @Autowired
    IProjectDao projectDao;

    @Autowired
    IEmployeeDao employeeDao;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < createdEntitiesNumber; i++) {
            Project project = new Project();
            project.setProjectName("ProjectName" + random.nextLong());
            entityManager.persist(project);
        }

        BusinessEmployee businessEmployee = new BusinessEmployee();
        businessEmployee.setUserName("UserName" + random.nextLong());
        entityManager.persist(businessEmployee);
        businessEmployeeId = businessEmployee.getId();

        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setUserName("BusinessRelationManager" + random.nextLong());
        entityManager.persist(businessRelationManager);
        businessRelationManagerId = businessRelationManager.getId();

        BusinessUnit firstBusinessUnit = new BusinessUnit();
        firstBusinessUnit.setName("BusinessUnitName" + random.nextLong());
        entityManager.persist(firstBusinessUnit);
        firstBusinessUnitId = firstBusinessUnit.getId();

        BusinessUnit secondBusinessUnit = new BusinessUnit();
        secondBusinessUnit.setName("BusinessUnitName" + random.nextLong());
        entityManager.persist(secondBusinessUnit);
        secondBusinessUnitId = secondBusinessUnit.getId();

        transaction.commit();
    }

    //ToDo Create test which includes case, when BusinessLeader Role exists
    @Test
    void saveFromDtoThenSuccess(){
        ProjectCreationDTO projectCreationDTO = prepareProjectCreationDto();
        ProjectCreatedDTO projectCreatedDTO = projectService.save(projectCreationDTO);
        assertNotNull(projectCreatedDTO);
        assertNotNull(projectService.find(projectCreatedDTO.getId()));
    }

    @Test
    void find_ThenSuccess() {
        Project result = (Project) projectService.find(1l);
        assertNotNull(result);
    }

    @Test
    void findAll_ThenSuccess() {
        List<Project> lists = projectService.findAll();
        assertEquals(3, lists.size());
    }

    @Test
    void delete_ThenSuccess() {
        projectService.delete(3L);
        assertEquals(createdEntitiesNumber - 1, projectService.findAll().size());
    }

    @Test
    void save_ThenSuccess() {
        assertNotNull(projectService.saveInternal(createNewEntity()));
        assertEquals(createdEntitiesNumber + 1, projectService.findAll().size());
    }

    protected Project createNewEntity() {
        Project project = new Project();
        project.setProjectName("ProjectName" + random.nextLong());
        return project;
    }

    private ProjectCreationDTO prepareProjectCreationDto(){

        BusinessRelationManagerDTO businessRelationManagerDTO = new BusinessRelationManagerDTO();
        businessRelationManagerDTO.setId(businessRelationManagerId);

        BusinessEmployeeDTO businessEmployeeDTO = new BusinessEmployeeDTO();
        businessEmployeeDTO.setId(businessEmployeeId);
        BusinessLeaderDTO businessLeaderDTO = new BusinessLeaderDTO();
        businessLeaderDTO.setEmployee(businessEmployeeDTO);

        Set<BusinessUnitDTO> businessUnitDTOSet = new HashSet<>();
        BusinessUnitDTO firstBusinessUnitDto = new BusinessUnitDTO();
        firstBusinessUnitDto.setId(firstBusinessUnitId);
        BusinessUnitDTO secondBusinessUnitDto = new BusinessUnitDTO();
        secondBusinessUnitDto.setId(secondBusinessUnitId);

        businessUnitDTOSet.add(firstBusinessUnitDto);
        businessUnitDTOSet.add(secondBusinessUnitDto);

        ProjectCreationDTO projectCreationDTO = new ProjectCreationDTO();
        projectCreationDTO.setProjectName("ProjectName" + random.nextInt());

        projectCreationDTO.setBusinessRelationManager(businessRelationManagerDTO);
        projectCreationDTO.setBusinessLeader(businessLeaderDTO);
        projectCreationDTO.setBusinessUnits(businessUnitDTOSet);
        return projectCreationDTO;

    }
}
