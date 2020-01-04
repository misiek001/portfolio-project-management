package com.mbor.mapping;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.model.*;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.spring.WebConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WebConfiguration.class)
@WebAppConfiguration
class DTOtoEntityMapperTest {

    private static Random random = new Random();

    private static ProjectCreationDTO projectCreationDTO;
    private static BusinessRelationManagerDTO businessRelationManagerDTO;
    private static BusinessEmployeeDTO businessEmployeeDTO;
    private static BusinessLeaderDTO businessLeaderDTO;
    private static BusinessUnitDTO businessUnitDTOFirst;
    private static BusinessUnitDTO businessUnitDTOSecond;

    private static Project expectedProject;
    private static BusinessRelationManager businessRelationManager;
    private static BusinessEmployee businessEmployee;
    private static BusinessLeader businessLeader;
    private static BusinessUnit businessUnitFirst;
    private static BusinessUnit businessUnitSecond;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    BusinessRelationManagerMapper businessRelationManagerMapper;

    @Autowired
    BusinessLeaderMapper businessLeaderMapper;

    @Autowired
    BusinessEmployeeMapper businessEmployeeMapper;

    @BeforeAll
    static void setUp() {
        projectCreationDTO = new ProjectCreationDTO();
        projectCreationDTO.setProjectName("Project Name");

        businessRelationManagerDTO = new BusinessRelationManagerDTO();
        businessRelationManagerDTO.setId(random.nextLong());
        projectCreationDTO.setBusinessRelationManager(businessRelationManagerDTO);

        businessLeaderDTO = new BusinessLeaderDTO();
        businessEmployeeDTO = new BusinessEmployeeDTO();
        businessEmployeeDTO.setId(random.nextLong());

        businessLeaderDTO.setEmployee(businessEmployeeDTO);
        projectCreationDTO.setBusinessLeader(businessLeaderDTO);

        businessUnitDTOFirst = new BusinessUnitDTO();
        businessUnitDTOFirst.setId(random.nextLong());
        projectCreationDTO.addBusinessUnit(businessUnitDTOFirst);

        businessUnitDTOSecond = new BusinessUnitDTO();
        businessUnitDTOSecond.setId(random.nextLong());
        projectCreationDTO.addBusinessUnit(businessUnitDTOSecond);

        //Creating Expected Project

        expectedProject = new Project();
        expectedProject.setProjectName("Project Name");
        expectedProject.setStatus(ProjectStatus.ANALYSIS);

        businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setId(businessRelationManagerDTO.getId());
        expectedProject.setBusinessRelationManager(businessRelationManager);

        businessLeader = new BusinessLeader();
        businessEmployee = new BusinessEmployee();
        businessEmployee.setId(businessEmployeeDTO.getId());
        businessLeader.setEmployee(businessEmployee);
        expectedProject.setBusinessLeader(businessLeader);

        businessUnitFirst = new BusinessUnit();
        businessUnitFirst.setId(businessUnitDTOFirst.getId());

        businessUnitSecond = new BusinessUnit();
        businessUnitSecond.setId(businessUnitDTOSecond.getId());
        expectedProject.addBusinessUnit(businessUnitFirst);
        expectedProject.addBusinessUnit(businessUnitSecond);
    }

    @Test
     void  mapProjectCreationDTOtoProject() {
        Project createdProject = projectMapper.mapProjectCreationDTOtoProject(projectCreationDTO);

        assertEquals(createdProject.getId(), expectedProject.getId());
        assertEquals(createdProject.getProjectName(), expectedProject.getProjectName());
        assertEquals(createdProject.getBusinessRelationManager().getId(), expectedProject.getBusinessRelationManager().getId());
        assertEquals(createdProject.getBusinessRelationManager().getFirstName(), expectedProject.getBusinessRelationManager().getFirstName());
        assertEquals(createdProject.getBusinessRelationManager().getLastName(), expectedProject.getBusinessRelationManager().getLastName());
        assertEquals(createdProject.getBusinessLeader().getId(), expectedProject.getBusinessLeader().getId());
        assertEquals(createdProject.getBusinessLeader().getEmployee().getId(), expectedProject.getBusinessLeader().getEmployee().getId());
        assertEquals(createdProject.getBusinessLeader().getEmployee().getFirstName(), expectedProject.getBusinessLeader().getEmployee().getFirstName());
        assertEquals(createdProject.getBusinessLeader().getEmployee().getLastName(), expectedProject.getBusinessLeader().getEmployee().getLastName());
        assertEquals(createdProject.getStatus().name(), expectedProject.getStatus().name());
    }

    @Test
    void mapBusinessRelationManagerDTOtoEntity(){
        BusinessRelationManager createdBRM = businessRelationManagerMapper.convertToEntity(businessRelationManagerDTO);

        assertEquals(createdBRM.getId(), businessRelationManager.getId());
        assertEquals(createdBRM.getFirstName(), businessRelationManager.getFirstName());
        assertEquals(createdBRM.getLastName(), businessRelationManager.getLastName());
    }

    @Test
    void mamBusinessLeaderDTOToEntity() {
        BusinessLeader createdBusinessLeader = businessLeaderMapper.convertToEntity(businessLeaderDTO);

        assertEquals(createdBusinessLeader.getId(), businessLeader.getId());
        assertEquals(createdBusinessLeader.getEmployee().getId(), businessLeader.getEmployee().getId());
        assertEquals(createdBusinessLeader.getEmployee().getFirstName(), businessLeader.getEmployee().getFirstName());
        assertEquals(createdBusinessLeader.getEmployee().getLastName(), businessLeader.getEmployee().getLastName());
    }

    @Test
    void mapBusinessEmployeeDTOtoEntity() {
        BusinessEmployee createdBusinessEmployee = businessEmployeeMapper.convertToEntity((businessEmployeeDTO));
        assertEquals(createdBusinessEmployee.getId(), businessEmployee.getId());
        assertEquals(createdBusinessEmployee.getFirstName(), businessEmployee.getFirstName());
        assertEquals(createdBusinessEmployee.getLastName(), businessEmployee.getLastName());
    }

}