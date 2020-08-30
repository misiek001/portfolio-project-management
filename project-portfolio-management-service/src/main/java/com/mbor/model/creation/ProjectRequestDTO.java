package com.mbor.model.creation;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.model.BusinessRelationManagerDTO;
import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.IdDTO;
import com.mbor.model.views.Views;

public class ProjectRequestDTO extends IdDTO {

    @JsonView(Views.Public.class)
    private String projectName;

    @JsonView(Views.Public.class)
    private String description;

    @JsonView(Views.Public.class)
    private BusinessUnitDTO businessUnit;

    @JsonView(Views.Public.class)
    private BusinessRelationManagerDTO businessRelationManager;

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

    public BusinessUnitDTO getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnitDTO businessUnit) {
        this.businessUnit = businessUnit;
    }

    public BusinessRelationManagerDTO getBusinessRelationManager() {
        return businessRelationManager;
    }

    public void setBusinessRelationManager(BusinessRelationManagerDTO businessRelationManager) {
        this.businessRelationManager = businessRelationManager;
    }

}
