package com.misiek.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ProjectDTO {

    private Long Id;

    private String projectName;

    private ResourceManagerDTO resourceManager;

    private ProjectManagerDTO projectManager;

    private BusinessRelationManagerDTO businessRelationManager;

    private BusinessLeaderDTO businessUnitLeader;

    private Set<SolutionArchitectDTO> solutionArchitect;

    private ProjectStatusDTO status;

    private LocalDateTime startDate;

    private LocalDateTime plannedEndDate;

    private Set<RealEndDateDTO> realEndDateSet = new HashSet<>();

    private Set<BusinessUnitDTO> businessUnits;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ResourceManagerDTO getResourceManager() {
        return resourceManager;
    }

    public void setResourceManager(ResourceManagerDTO resourceManager) {
        this.resourceManager = resourceManager;
    }

    public ProjectManagerDTO getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(ProjectManagerDTO projectManager) {
        this.projectManager = projectManager;
    }

    public BusinessRelationManagerDTO getBusinessRelationManager() {
        return businessRelationManager;
    }

    public void setBusinessRelationManager(BusinessRelationManagerDTO businessRelationManager) {
        this.businessRelationManager = businessRelationManager;
    }

    public BusinessLeaderDTO getBusinessUnitLeader() {
        return businessUnitLeader;
    }

    public void setBusinessUnitLeader(BusinessLeaderDTO businessUnitLeader) {
        this.businessUnitLeader = businessUnitLeader;
    }

    public Set<SolutionArchitectDTO> getSolutionArchitect() {
        return solutionArchitect;
    }

    public void setSolutionArchitect(Set<SolutionArchitectDTO> solutionArchitect) {
        this.solutionArchitect = solutionArchitect;
    }

    public ProjectStatusDTO getStatus() {
        return status;
    }

    public void setStatus(ProjectStatusDTO status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(LocalDateTime plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public Set<RealEndDateDTO> getRealEndDateSet() {
        return realEndDateSet;
    }

    public void setRealEndDateSet(Set<RealEndDateDTO> realEndDateSet) {
        this.realEndDateSet = realEndDateSet;
    }

    public Set<BusinessUnitDTO> getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(Set<BusinessUnitDTO> businessUnits) {
        this.businessUnits = businessUnits;
    }
}
