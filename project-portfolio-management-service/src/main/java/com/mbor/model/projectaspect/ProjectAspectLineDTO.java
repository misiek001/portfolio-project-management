package com.mbor.model.projectaspect;

public class ProjectAspectLineDTO {


    private BudgetAspectDTO budgetAspect;


    private ResourcesAspectDTO resourcesAspect;


    private ScopeAspectDTO scopeAspect;


    private DeadlineAspectDTO deadlineAspect;

    public BudgetAspectDTO getBudgetAspect() {
        return budgetAspect;
    }

    public void setBudgetAspect(BudgetAspectDTO budgetAspect) {
        this.budgetAspect = budgetAspect;
    }

    public ResourcesAspectDTO getResourcesAspect() {
        return resourcesAspect;
    }

    public void setResourcesAspect(ResourcesAspectDTO resourcesAspect) {
        this.resourcesAspect = resourcesAspect;
    }

    public ScopeAspectDTO getScopeAspect() {
        return scopeAspect;
    }

    public void setScopeAspect(ScopeAspectDTO scopeAspect) {
        this.scopeAspect = scopeAspect;
    }

    public DeadlineAspectDTO getDeadlineAspect() {
        return deadlineAspect;
    }

    public void setDeadlineAspect(DeadlineAspectDTO deadlineAspect) {
        this.deadlineAspect = deadlineAspect;
    }
}
