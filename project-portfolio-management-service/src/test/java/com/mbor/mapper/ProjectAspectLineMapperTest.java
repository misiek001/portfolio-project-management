package com.mbor.mapper;

import com.mbor.domain.projectaspect.*;
import com.mbor.mapper.project.ProjectAspectLineMapper;
import com.mbor.model.projectaspect.*;
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
class ProjectAspectLineMapperTest {

    private static ProjectAspectLine projectAspectLine;

    private static ProjectAspectLineDTO projectAspectLineDTO;

    @Autowired
    ProjectAspectLineMapper projectAspectLineMapper;

    @BeforeAll
    public static void init(){
        prepareProjectAspectLine();
        prepareProjectAspectLineDTO();
    }

    @Test
    void convertToDto() {
        ProjectAspectLineDTO result = projectAspectLineMapper.convertEntityToDto(projectAspectLine);
        
        assertEquals(result.getBudgetAspect().getAspectStatusName(), projectAspectLine.getBudgetAspect().getAspectStatusName());
        assertEquals(result.getBudgetAspect().getDescription(), projectAspectLine.getBudgetAspect().getDescription());

        assertEquals(result.getResourcesAspect().getAspectStatusName(), projectAspectLine.getResourcesAspect().getAspectStatusName());
        assertEquals(result.getResourcesAspect().getDescription(), projectAspectLine.getResourcesAspect().getDescription());

        assertEquals(result.getScopeAspect().getAspectStatusName(), projectAspectLine.getScopeAspect().getAspectStatusName());
        assertEquals(result.getScopeAspect().getDescription(), projectAspectLine.getScopeAspect().getDescription());

        assertEquals(result.getDeadlineAspect().getAspectStatusName(), projectAspectLine.getDeadlineAspect().getAspectStatusName());
        assertEquals(result.getDeadlineAspect().getDescription(), projectAspectLine.getDeadlineAspect().getDescription());
    }

    @Test
    void convertToEntity() {
        ProjectAspectLine result = projectAspectLineMapper.convertDtoToEntity(projectAspectLineDTO);

        assertEquals(result.getBudgetAspect().getAspectStatusName(), projectAspectLineDTO.getBudgetAspect().getAspectStatusName());
        assertEquals(result.getBudgetAspect().getDescription(), projectAspectLineDTO.getBudgetAspect().getDescription());

        assertEquals(result.getResourcesAspect().getAspectStatusName(), projectAspectLineDTO.getResourcesAspect().getAspectStatusName());
        assertEquals(result.getResourcesAspect().getDescription(), projectAspectLineDTO.getResourcesAspect().getDescription());

        assertEquals(result.getScopeAspect().getAspectStatusName(), projectAspectLineDTO.getScopeAspect().getAspectStatusName());
        assertEquals(result.getScopeAspect().getDescription(), projectAspectLineDTO.getScopeAspect().getDescription());

        assertEquals(result.getDeadlineAspect().getAspectStatusName(), projectAspectLineDTO.getDeadlineAspect().getAspectStatusName());
        assertEquals(result.getDeadlineAspect().getDescription(), projectAspectLineDTO.getDeadlineAspect().getDescription());
    }
    
    private static void prepareProjectAspectLine(){
        projectAspectLine = new ProjectAspectLine();

        BudgetAspect budgetAspect = new BudgetAspect();
        budgetAspect.setAspectStatus(AspectStatus.GREEN);
        budgetAspect.setDescription("Budget Description");
        projectAspectLine.setBudgetAspect(budgetAspect);

        ResourcesAspect resourcesAspect = new ResourcesAspect();
        resourcesAspect.setAspectStatus(AspectStatus.YELLOW);
        resourcesAspect.setDescription("Resources Description");
        projectAspectLine.setResourcesAspect(resourcesAspect);

        ScopeAspect ScopeAspect = new ScopeAspect();
        ScopeAspect.setAspectStatus(AspectStatus.RED);
        ScopeAspect.setDescription("Scope Aspect");
        projectAspectLine.setScopeAspect(ScopeAspect);

        DeadlineAspect deadlineAspect = new DeadlineAspect();
        deadlineAspect.setAspectStatus(AspectStatus.GREEN);
        deadlineAspect.setDescription("Deadline Description");
        projectAspectLine.setDeadlineAspect(deadlineAspect);
    }
    
    private static void prepareProjectAspectLineDTO(){
        projectAspectLineDTO = new ProjectAspectLineDTO();

        BudgetAspectDTO budgetAspectDTO = new BudgetAspectDTO();
        budgetAspectDTO.setAspectStatus(AspectStatusDTO.GREEN);
        budgetAspectDTO.setDescription("Budget Description");
        projectAspectLineDTO.setBudgetAspect(budgetAspectDTO);

        ResourcesAspectDTO resourcesAspectDTO = new ResourcesAspectDTO();
        resourcesAspectDTO.setAspectStatus(AspectStatusDTO.YELLOW);
        resourcesAspectDTO.setDescription("Resources Description");
        projectAspectLineDTO.setResourcesAspect(resourcesAspectDTO);

        ScopeAspectDTO ScopeAspectDTO = new ScopeAspectDTO();
        ScopeAspectDTO.setAspectStatus(AspectStatusDTO.RED);
        ScopeAspectDTO.setDescription("Scope Aspect");
        projectAspectLineDTO.setScopeAspect(ScopeAspectDTO);

        DeadlineAspectDTO deadlineAspectDTO = new DeadlineAspectDTO();
        deadlineAspectDTO.setAspectStatus(AspectStatusDTO.GREEN);
        deadlineAspectDTO.setDescription("Deadline Description");
        projectAspectLineDTO.setDeadlineAspect(deadlineAspectDTO);
    }

}