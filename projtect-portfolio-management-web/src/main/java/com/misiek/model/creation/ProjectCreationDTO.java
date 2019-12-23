package com.misiek.model.creation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.misiek.model.BusinessLeaderDTO;
import com.misiek.model.BusinessRelationManagerDTO;
import com.misiek.model.BusinessUnitDTO;
import com.misiek.model.ProjectStatusDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class ProjectCreationDTO {

    @NotNull
    String projectName;

    @JsonIgnore
    ProjectStatusDTO projectStatus = ProjectStatusDTO.ANALYSIS;

    @NotNull
    BusinessRelationManagerDTO businessRelationManager;

    @NotNull
    BusinessLeaderDTO businessLeader;

    @NotEmpty
    Set<BusinessUnitDTO> businessUnits = new HashSet<>();

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ProjectStatusDTO getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatusDTO projectStatus) {
        this.projectStatus = projectStatus;
    }

    public BusinessRelationManagerDTO getBusinessRelationManager() {
        return businessRelationManager;
    }

    public void setBusinessRelationManager(BusinessRelationManagerDTO businessRelationManager) {
        this.businessRelationManager = businessRelationManager;
    }

    public BusinessLeaderDTO getBusinessLeader() {
        return businessLeader;
    }

    public void setBusinessLeader(BusinessLeaderDTO businessLeader) {
        this.businessLeader = businessLeader;
    }

    public Set<BusinessUnitDTO> getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(Set<BusinessUnitDTO> businessUnits) {
        this.businessUnits = businessUnits;
    }

    public void addBusinessUnit(BusinessUnitDTO businessUnitDTO){
        businessUnits.add(businessUnitDTO);
    }
}
