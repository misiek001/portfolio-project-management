package com.mbor.entityFactory;

import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.DemandSheet;
import com.mbor.domain.Project;
import com.mbor.domain.projectaspect.*;
import com.mbor.model.ProjectDTO;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TestEntityFactory {

    private static Random random = new Random();

    public Project prepareProject() {
        Project project = new Project();
        project.setProjectName("ProjectName" + random.nextLong());
        return project;
    }

    public ProjectDTO prepareProjectDTO(){
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectName("ProjectName" + random.nextLong());
        return projectDTO;
    }

    public ProjectDTO prepareProjectDTOFromProject(Project project){
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectName(project.getProjectName());
        return projectDTO;
    }

    public BusinessUnit prepareBusinessUnit(){
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName("BusinessUnit" + random.nextLong());
        return businessUnit;
    }

    public DemandSheet prepareDemandSheet(){
        DemandSheet demandSheet = new DemandSheet();
        demandSheet.setProjectName("Project Name" + random.nextLong() );
        demandSheet.setDescription("Project Description" + random.nextLong());
        return demandSheet;
    }

    public BusinessRelationManager prepareBusinessRelationManager(){
        BusinessRelationManager  businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setUserName("BRMUserName" + random.nextLong());
        return businessRelationManager;
    }

    public ProjectAspectLine prepareProjectAspectLine() {
        ProjectAspectLine projectAspectLine = new ProjectAspectLine();

        BudgetAspect budgetAspect = new BudgetAspect();
        budgetAspect.setAspectStatus(AspectStatus.GREEN);
        budgetAspect.setDescription("Budget Description");
        projectAspectLine.setBudgetAspect(budgetAspect);

        ResourcesAspect resourcesAspect = new ResourcesAspect();
        resourcesAspect.setAspectStatus(AspectStatus.RED);
        resourcesAspect.setDescription("Resources Description");
        projectAspectLine.setResourcesAspect(resourcesAspect);

        ScopeAspect scopeAspect = new ScopeAspect();
        scopeAspect.setAspectStatus(AspectStatus.YELLOW);
        scopeAspect.setDescription("Scope Description");
        projectAspectLine.setScopeAspect(scopeAspect);

        DeadlineAspect deadlineAspect = new DeadlineAspect();
        deadlineAspect.setAspectStatus(AspectStatus.GREEN);
        deadlineAspect.setDescription("Deadline Description");
        projectAspectLine.setDeadlineAspect(deadlineAspect);

        return projectAspectLine;
    }
}
