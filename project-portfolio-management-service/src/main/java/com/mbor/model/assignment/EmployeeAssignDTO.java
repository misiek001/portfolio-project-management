package com.mbor.model.assignment;

import java.util.HashSet;
import java.util.Set;

public class EmployeeAssignDTO {

    private Long projectManagerId;

    private Long businessRelationManagerId;

    private Long resourceManagerId;

    private Set<Long> solutionArchitectIdSet = new HashSet<>();

    private Long businessLeaderId;

    public Long getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(Long projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public Long getBusinessRelationManagerId() {
        return businessRelationManagerId;
    }

    public void setBusinessRelationManagerId(Long businessRelationManagerId) {
        this.businessRelationManagerId = businessRelationManagerId;
    }

    public Long getResourceManagerId() {
        return resourceManagerId;
    }

    public void setResourceManagerId(Long resourceManagerId) {
        this.resourceManagerId = resourceManagerId;
    }

    public Set<Long> getSolutionArchitectIdSet() {
        return solutionArchitectIdSet;
    }

    public void setSolutionArchitectIdSet(Set<Long> solutionArchitectIdSet) {
        this.solutionArchitectIdSet = solutionArchitectIdSet;
    }

    public Long getBusinessLeaderId() {
        return businessLeaderId;
    }

    public void setBusinessLeaderId(Long businessLeaderId) {
        this.businessLeaderId = businessLeaderId;
    }
}
