package com.mbor.model.creation;

import com.mbor.model.IProjectDTO;
import com.mbor.model.ProjectClassDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProjectCreationDTO implements IProjectDTO {

    @NotNull
    private String projectName;

    @NotNull
    private String description;

    @NotEmpty
    private ProjectClassDTO projectClass;

    @NotNull
    private Long businessRelationManagerId;

    @NotNull
    private Long businessLeaderId;

    @NotNull
    private Long primaryBusinessUnitId;

    @NotNull
    private List<Long> secondaryBusinessUnitIds;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getSecondaryBusinessUnitIds() {
        return secondaryBusinessUnitIds;
    }

    public void setSecondaryBusinessUnitIds(List<Long> secondaryBusinessUnitIds) {
        this.secondaryBusinessUnitIds = secondaryBusinessUnitIds;
    }
}
