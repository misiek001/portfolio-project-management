//package com.misiek.mapping;
//
//import com.misiek.domain.*;
//import com.misiek.domain.employeeinproject.BusinessLeader;
//import com.misiek.domain.employeeinproject.ProjectManager;
//import com.misiek.domain.employeeinproject.ResourceManager;
//import com.misiek.domain.employeeinproject.SolutionArchitect;
//import com.misiek.model.ProjectDTO;
//import com.misiek.spring.WebConfiguration;
//import jdk.nashorn.internal.ir.annotations.Ignore;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = WebConfiguration.class)
//@WebAppConfiguration
//class MapperTest {
//
//    @Autowired
//    Mapper mapper;
//
//    @Test
//    void mapperCreated(){
//        assertNotNull(mapper);
//    }
//
//    @Test
//    void mapEmptyProjectEntityToEmptyDTO(){
//        Project project = new Project();
//        ProjectDTO testDTO = new ProjectDTO();
//
//        assertEquals(testDTO.getId(), mapper.mapProjectEntityToDTO(project).getId());
//    }
//
//    @Test
//    @Ignore
//    void mapEmptyProjectDTOToEmptyEntity(){
//        ProjectDTO projectDTO = new ProjectDTO();
//        Project testProject = new Project();
//
//        assertEquals(testProject.getId(), mapper.mapProjectDTOtoEntity(projectDTO).getId());
//    }
//    @Ignore
//    @Test
//    void mapProjectEntityToDTO(){
//        Project project = new Project();
//        project.setId(1l);
//        project.setName("Jan");
//        Director director = new Director();
//        director.setId(4L);
//        ResourceManager resourceManager = new ResourceManager();
//        resourceManager.setId(2l);
//        resourceManager.setEmployee(new Supervisor());
//        resourceManager.getEmployee().setId(5l);
//        resourceManager.getEmployee().setDirector(director);
//        project.setResourceManager(resourceManager);
//        ProjectManager projectManager = new ProjectManager();
//        project.setProjectManager(projectManager);
//        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
//        businessRelationManager.setId(3L);
//        businessRelationManager.setDirector(director);
//        project.setBusinessRelationManager(businessRelationManager);
//        BusinessLeader businessLeader = new BusinessLeader();
//        businessLeader.setId(5L);
//        project.setBusinessUnitLeader(businessLeader);
//        SolutionArchitect solutionArchitect1 = new SolutionArchitect();
//        solutionArchitect1.setId(6l);
//        SolutionArchitect solutionArchitect2 = new SolutionArchitect();
//        solutionArchitect2.setId(7l);
//        Set<SolutionArchitect> set = new HashSet<>();
//        set.add(solutionArchitect1);
//        set.add(solutionArchitect2);
//        project.setSolutionArchitect(set);
//        project.setStatus(ProjectStatus.ANALYSIS);
//        project.setStartDate(LocalDateTime.now());
//        project.setPlannedEndDate(LocalDateTime.of(2020,11,11,1,1));
//        BusinessUnit businessUnit1 = new BusinessUnit();
//        businessUnit1.setId(8L);
//        Set<BusinessUnit> businessUnits = new HashSet<>();
//        businessUnits.add(businessUnit1);
//        project.setBusinessUnits(businessUnits);
//
//        ProjectDTO result = mapper.mapProjectEntityToDTO(project);
//
//        System.out.println(result.getId());
//
//    }
//
//}