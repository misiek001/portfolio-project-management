package com.mbor.mapper;

import com.mbor.domain.projectaspect.*;
import com.mbor.model.projectaspect.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectAspectLineMapper extends SimplePojoMapper<ProjectAspectLineDTO, ProjectAspectLine> {

    public ProjectAspectLineMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProjectAspectLineDTO convertToDto(ProjectAspectLine projectAspectLine) {
        ProjectAspectLineDTO projectAspectLineDTO = new ProjectAspectLineDTO();

        BudgetAspectDTO budgetAspectDTO = new BudgetAspectDTO();
        BudgetAspect budgetAspect = projectAspectLine.getBudgetAspect();
        budgetAspectDTO.setAspectStatus(Enum.valueOf(AspectStatusDTO.class, budgetAspect.getAspectStatusName()));
        budgetAspectDTO.setDescription(budgetAspect.getDescription());
        projectAspectLineDTO.setBudgetAspect(budgetAspectDTO);

        ResourcesAspectDTO resourcesAspectDTO = new ResourcesAspectDTO();
        ResourcesAspect resourcesAspect = projectAspectLine.getResourcesAspect();
        resourcesAspectDTO.setAspectStatus(Enum.valueOf(AspectStatusDTO.class, resourcesAspect.getAspectStatusName()));
        resourcesAspectDTO.setDescription(budgetAspect.getDescription());
        projectAspectLineDTO.setResourcesAspect(resourcesAspectDTO);

        ScopeAspectDTO ScopeAspectDTO = new ScopeAspectDTO();
        ScopeAspect scopeAspect = projectAspectLine.getScopeAspect();
        ScopeAspectDTO.setAspectStatus(Enum.valueOf(AspectStatusDTO.class, scopeAspect.getAspectStatusName()));
        ScopeAspectDTO.setDescription(budgetAspect.getDescription());
        projectAspectLineDTO.setScopeAspect(ScopeAspectDTO);

        DeadlineAspectDTO deadlineAspectDTO = new DeadlineAspectDTO();
        DeadlineAspect DeadlineAspect = projectAspectLine.getDeadlineAspect();
        deadlineAspectDTO.setAspectStatus(Enum.valueOf(AspectStatusDTO.class, DeadlineAspect.getAspectStatusName()));
        deadlineAspectDTO.setDescription(budgetAspect.getDescription());
        projectAspectLineDTO.setDeadlineAspect(deadlineAspectDTO);
        
        return modelMapper.map(projectAspectLine, ProjectAspectLineDTO.class);
    }

    @Override
    public ProjectAspectLine convertToEntity(ProjectAspectLineDTO projectAspectLineDTO) {
        ProjectAspectLine projectAspectLine = new ProjectAspectLine();

        BudgetAspect budgetAspect = new BudgetAspect();
        BudgetAspectDTO budgetAspectDTO = projectAspectLineDTO.getBudgetAspect();
        budgetAspect.setAspectStatus(Enum.valueOf(AspectStatus.class, budgetAspectDTO.getAspectStatusName()));
        budgetAspect.setDescription(budgetAspectDTO.getDescription());
        projectAspectLine.setBudgetAspect(budgetAspect);

        ResourcesAspect resourcesAspect = new ResourcesAspect();
        ResourcesAspectDTO resourcesAspectDTO = projectAspectLineDTO.getResourcesAspect();
        resourcesAspect.setAspectStatus(Enum.valueOf(AspectStatus.class, resourcesAspectDTO.getAspectStatusName()));
        resourcesAspect.setDescription(resourcesAspectDTO.getDescription());
        projectAspectLine.setResourcesAspect(resourcesAspect);

        ScopeAspect scopeAspect = new ScopeAspect();
        ScopeAspectDTO scopeAspectDTO = projectAspectLineDTO.getScopeAspect();
        scopeAspect.setAspectStatus(Enum.valueOf(AspectStatus.class, scopeAspectDTO.getAspectStatusName()));
        scopeAspect.setDescription(scopeAspectDTO.getDescription());
        projectAspectLine.setScopeAspect(scopeAspect);

        DeadlineAspect deadlineAspect = new DeadlineAspect();
        DeadlineAspectDTO deadlineAspectDTO = projectAspectLineDTO.getDeadlineAspect();
        deadlineAspect.setAspectStatus(Enum.valueOf(AspectStatus.class, deadlineAspectDTO.getAspectStatusName()));
        deadlineAspect.setDescription(deadlineAspectDTO.getDescription());
        projectAspectLine.setDeadlineAspect(deadlineAspect);


        return modelMapper.map(projectAspectLineDTO, ProjectAspectLine.class);
    }
}
