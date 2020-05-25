package com.mbor.model.creation;

import com.mbor.model.IProjectDTO;
import com.mbor.model.ProjectClassDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProjectCreationDTO implements IProjectDTO {

    @NotNull
    String projectName;

    @NotNull
    Long businessRelationManagerId;

    @NotNull
    Long businessLeaderId;

    @NotEmpty
    Long primaryBusinessUnitId;

    @NotEmpty
    private ProjectClassDTO projectClass;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getBusinessRelationManagerId() {
        return businessRelationManagerId;
    }

    public void setBusinessRelationManagerId(Long businessRelationManagerId) {
        this.businessRelationManagerId = businessRelationManagerId;
    }

    public Long getBusinessLeaderId() {
        return businessLeaderId;
    }

    public void setBusinessLeaderId(Long businessLeaderId) {
        this.businessLeaderId = businessLeaderId;
    }

    public Long getPrimaryBusinessUnitId() {
        return primaryBusinessUnitId;
    }

    public void setPrimaryBusinessUnitId(Long primaryBusinessUnitId) {
        this.primaryBusinessUnitId = primaryBusinessUnitId;
    }

    public ProjectClassDTO getProjectClass() {
        return projectClass;
    }

    public void setProjectClass(ProjectClassDTO projectClass) {
        this.projectClass = projectClass;
    }
}
