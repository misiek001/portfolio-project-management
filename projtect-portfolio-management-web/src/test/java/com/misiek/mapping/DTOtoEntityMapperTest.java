package com.misiek.mapping;

import com.misiek.domain.*;
import com.misiek.domain.employeeinproject.BusinessLeader;
import com.misiek.model.*;
import com.misiek.model.creation.ProjectCreationDTO;
import com.misiek.spring.WebConfiguration;
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

    static Random random = new Random();

    static ProjectCreationDTO projectCreationDTO;
    static BusinessRelationManagerDTO businessRelationManagerDTO;
    static BusinessEmployeeDTO businessEmployeeDTO;
    static BusinessLeaderDTO businessLeaderDTO;
    static BusinessUnitDTO businessUnitDTOFirst;
    static BusinessUnitDTO businessUnitDTOSecond;

    static Project expectedProject;
    static BusinessRelationManager businessRelationManager;
    static BusinessEmployee businessEmployee;
    static BusinessLeader businessLeader;
    static BusinessUnit businessUnitFirst;
    static BusinessUnit businessUnitSecond;

    @Autowired
    DTOtoEntityMapper dtOtoEntityMapper;

    @BeforeAll
    static void setUp() {
        projectCreationDTO = new ProjectCreationDTO();
        projectCreationDTO.setProjectName("Project Name");

        businessRelationManagerDTO = new BusinessRelationManagerDTO();
        businessRelationManagerDTO.setId(random.nextLong());
        businessRelationManagerDTO.setFirstName("BRM First Name");
        businessRelationManagerDTO.setLastName("BRM Last Name");
        projectCreationDTO.setBusinessRelationManager(businessRelationManagerDTO);

        businessLeaderDTO = new BusinessLeaderDTO();
        businessEmployeeDTO = new BusinessEmployeeDTO();
        businessEmployeeDTO.setId(random.nextLong());
        businessEmployeeDTO.setFirstName("BL First Name");
        businessEmployeeDTO.setLastName("BL Last Name");
        businessLeaderDTO.setId(random.nextLong());
        businessLeaderDTO.setEmployee(businessEmployeeDTO);
        projectCreationDTO.setBusinessLeader(businessLeaderDTO);

        businessUnitDTOFirst = new BusinessUnitDTO();
        businessUnitDTOFirst.setId(random.nextLong());
        businessUnitDTOFirst.setName("First Business Unit");
        projectCreationDTO.addBusinessUnit(businessUnitDTOFirst);

        businessUnitDTOSecond = new BusinessUnitDTO();
        businessUnitDTOSecond.setId(random.nextLong());
        businessUnitDTOSecond.setName("Second Business Unit");
        projectCreationDTO.addBusinessUnit(businessUnitDTOSecond);

        //Creating Expected Project

        expectedProject = new Project();
        expectedProject.setProjectName("Project Name");
        expectedProject.setStatus(ProjectStatus.ANALYSIS);

        businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setId(businessRelationManagerDTO.getId());
        businessRelationManager.setFirstName(businessRelationManagerDTO.getFirstName());
        businessRelationManager.setLastName(businessRelationManagerDTO.getLastName());
        expectedProject.setBusinessRelationManager(businessRelationManager);

        businessLeader = new BusinessLeader();
        businessEmployee = new BusinessEmployee();
        businessEmployee.setId(businessEmployeeDTO.getId());
        businessEmployee.setFirstName(businessEmployeeDTO.getFirstName());
        businessEmployee.setLastName(businessEmployeeDTO.getLastName());
        businessLeader.setId(businessLeaderDTO.getId());
        businessLeader.setEmployee(businessEmployee);
        expectedProject.setBusinessLeader(businessLeader);

        businessUnitFirst = new BusinessUnit();
        businessUnitFirst.setId(businessUnitDTOFirst.getId());
        businessUnitFirst.setName(businessUnitDTOFirst.getName());

        businessUnitSecond = new BusinessUnit();
        businessUnitSecond.setId(businessUnitDTOSecond.getId());
        businessUnitSecond.setName(businessUnitDTOSecond.getName());
        expectedProject.addBusinessUnit(businessUnitFirst);
        expectedProject.addBusinessUnit(businessUnitSecond);
    }

    @Test
     void  mapProjectCreationDTOtoProject() {
        Project createdProject = dtOtoEntityMapper.mapProjectCreationDTOtoProject(projectCreationDTO);

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
        BusinessRelationManager createdBRM = dtOtoEntityMapper.mapBusinessRelationManagerDTOtoEntity(businessRelationManagerDTO);

        assertEquals(createdBRM.getId(), businessRelationManager.getId());
        assertEquals(createdBRM.getFirstName(), businessRelationManager.getFirstName());
        assertEquals(createdBRM.getLastName(), businessRelationManager.getLastName());
    }

    @Test
    void mamBusinessLeaderDTOToEntity() {

        BusinessLeader createdBusinessLeader = dtOtoEntityMapper.mamBusinessLeaderDTOToEntity(businessLeaderDTO);

        assertEquals(createdBusinessLeader.getId(), businessLeader.getId());
        assertEquals(createdBusinessLeader.getEmployee().getId(), businessLeader.getEmployee().getId());
        assertEquals(createdBusinessLeader.getEmployee().getFirstName(), businessLeader.getEmployee().getFirstName());
        assertEquals(createdBusinessLeader.getEmployee().getLastName(), businessLeader.getEmployee().getLastName());

    }

    @Test
    void mapBusinessEmployeeDTOtoEntity() {
        BusinessEmployee createdBusinessEmployee = dtOtoEntityMapper.mapBusinessEmployeeDTOtoEntity(businessEmployeeDTO);
        assertEquals(createdBusinessEmployee.getId(), businessEmployee.getId());
        assertEquals(createdBusinessEmployee.getFirstName(), businessEmployee.getFirstName());
        assertEquals(createdBusinessEmployee.getLastName(), businessEmployee.getLastName());

    }

}