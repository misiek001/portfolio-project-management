package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mbor.domain.projectaspect.ProjectAspectLine;
import com.mbor.model.views.Views;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "projectName")
public class ProjectDTO extends IdDTO implements IProjectDTO {

    @JsonView(Views.Public.class)
    private String projectName;

    @JsonView(Views.ProjectInternal.class)
    private DemandSheetDTO demandSheetDTO;

    @JsonView(Views.Public.class)
    private ProjectClassDTO projectClass;

    @JsonView(Views.Public.class)
    private ResourceManagerDTO resourceManager;

    @JsonView(Views.Public.class)
    private ProjectManagerDTO projectManager;

    @JsonView(Views.Public.class)
    private BusinessRelationManagerDTO businessRelationManager;

    @JsonView(Views.Public.class)
    private BusinessLeaderDTO businessLeader;

    @JsonView(Views.Public.class)
    private Set<SolutionArchitectDTO> solutionArchitects;

    @JsonView(Views.Public.class)
    private Set<ProjectStatusHistoryLineDTO> projectStatusHistoryLines;

    @JsonView(Views.Public.class)
    private LocalDateTime startDate;

    @JsonView(Views.Public.class)
    private LocalDateTime plannedEndDate;

    @JsonView(Views.Public.class)
    private Set<RealEndDateDTO> realEndDates = new HashSet<>();

    @JsonView(Views.Public.class)
    private BusinessUnitDTO primaryBusinessUnit;

    @JsonView(Views.ProjectInternal.class)
    private Set<BusinessUnitDTO> secondaryBusinessUnits;

    @JsonView(Views.Public.class)
    private Set<ProjectAspectLine> projectAspectLines;

    public String getProjectName() {
        return projectName;
    }

    public DemandSheetDTO getDemandSheetDTO() {
        return demandSheetDTO;
    }

    public void setDemandSheetDTO(DemandSheetDTO demandSheetDTO) {
        this.demandSheetDTO = demandSheetDTO;
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

    public Set<SolutionArchitectDTO> getSolutionArchitects() {
        return solutionArchitects;
    }

    public void setSolutionArchitects(Set<SolutionArchitectDTO> solutionArchitects) {
        this.solutionArchitects = solutionArchitects;
    }

    public Set<ProjectStatusHistoryLineDTO> getProjectStatusHistoryLines() {
        return projectStatusHistoryLines;
    }

    public void setProjectStatusHistoryLines(Set<ProjectStatusHistoryLineDTO> projectStatusHistoryLines) {
        this.projectStatusHistoryLines = projectStatusHistoryLines;
    }

    public BusinessUnitDTO getPrimaryBusinessUnit() {
        return primaryBusinessUnit;
    }

    public void setPrimaryBusinessUnit(BusinessUnitDTO primaryBusinessUnit) {
        this.primaryBusinessUnit = primaryBusinessUnit;
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

    public Set<RealEndDateDTO> getRealEndDates() {
        return realEndDates;
    }

    public void setRealEndDates(Set<RealEndDateDTO> realEndDates) {
        this.realEndDates = realEndDates;
    }

    public Set<BusinessUnitDTO> getSecondaryBusinessUnits() {
        return secondaryBusinessUnits;
    }

    public void setSecondaryBusinessUnits(Set<BusinessUnitDTO> secondaryBusinessUnits) {
        this.secondaryBusinessUnits = secondaryBusinessUnits;
    }

    public Set<ProjectAspectLine> getProjectAspectLines() {
        return projectAspectLines;
    }

    public void setProjectAspectLines(Set<ProjectAspectLine> projectAspectLines) {
        this.projectAspectLines = projectAspectLines;
    }
}
