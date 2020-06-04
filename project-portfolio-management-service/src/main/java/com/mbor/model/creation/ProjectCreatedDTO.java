package com.mbor.model.creation;

import com.mbor.model.BusinessLeaderDTO;
import com.mbor.model.BusinessRelationManagerDTO;
import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.ProjectStatusHistoryLineDTO;

import java.util.List;

public class ProjectCreatedDTO  {

    private Long id;

    private String projectName;

    private List<ProjectStatusHistoryLineDTO> projectStatusHistoryLines;

    private BusinessRelationManagerDTO businessRelationManager;

    private BusinessLeaderDTO businessLeader;

    private BusinessUnitDTO primaryBusinessUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProjectStatusHistoryLineDTO> getProjectStatusHistoryLines() {
        return projectStatusHistoryLines;
    }

    public void setProjectStatusHistoryLines(List<ProjectStatusHistoryLineDTO> projectStatusHistoryLines) {
        this.projectStatusHistoryLines = projectStatusHistoryLines;
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

    public BusinessUnitDTO getPrimaryBusinessUnit() {
        return primaryBusinessUnit;
    }

    public void setPrimaryBusinessUnit(BusinessUnitDTO primaryBusinessUnit) {
        this.primaryBusinessUnit = primaryBusinessUnit;
    }
}
