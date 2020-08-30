package com.mbor.model.creation;

import javax.validation.constraints.NotEmpty;

public class ProjectRequestCreationDTO {

    @NotEmpty
    private String projectName;

    @NotEmpty
    private String description;

    @NotEmpty
    private Long businessUnitId;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(Long businessUnitId) {
        this.businessUnitId = businessUnitId;
    }
}
