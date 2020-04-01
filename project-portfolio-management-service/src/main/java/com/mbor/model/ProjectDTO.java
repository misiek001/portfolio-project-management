package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mbor.domain.projectaspect.ProjectAspectLine;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "projectName")
public class ProjectDTO implements IProjectDTO {

    private Long Id;

    private String projectName;

    private ProjectClassDTO projectClass;

    private ResourceManagerDTO resourceManager;

    private ProjectManagerDTO projectManager;

    private BusinessRelationManagerDTO businessRelationManager;

    private BusinessLeaderDTO businessLeader;

    private Set<SolutionArchitectDTO> solutionArchitect;

    private ProjectStatusDTO status;

    private LocalDateTime startDate;

    private LocalDateTime plannedEndDate;

    private Set<RealEndDateDTO> realEndDateSet = new HashSet<>();

    private Set<BusinessUnitDTO> businessUnits;

    private Set<ProjectAspectLine> projectAspectLineSet;

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

    public ProjectClassDTO getProjectClass() {
        return projectClass;
    }

    public void setProjectClass(ProjectClassDTO projectClass) {
        this.projectClass = projectClass;
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

    public BusinessLeaderDTO getBusinessLeader() {
        return businessLeader;
    }

    public void setBusinessLeader(BusinessLeaderDTO businessLeader) {
        this.businessLeader = businessLeader;
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

    public Set<ProjectAspectLine> getProjectAspectLineSet() {
        return projectAspectLineSet;
    }

    public void setProjectAspectLineSet(Set<ProjectAspectLine> projectAspectLineSet) {
        this.projectAspectLineSet = projectAspectLineSet;
    }
}
