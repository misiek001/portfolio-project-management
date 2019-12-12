package com.misiek.domain;

import com.misiek.domain.employeeinproject.BusinessLeader;
import com.misiek.domain.employeeinproject.ProjectManager;
import com.misiek.domain.employeeinproject.ResourceManager;
import com.misiek.domain.employeeinproject.SolutionArchitect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "Project")
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @ManyToOne(targetEntity = ResourceManager.class)
    @JoinColumn(name = "resource_manager_id")
    private ResourceManager resourceManager;

    @ManyToOne
    @JoinColumn(name = "project_manager_id")
    private ProjectManager projectManager;

    @ManyToOne
    @JoinColumn(name = "business_relation_manager_id")
    private BusinessRelationManager businessRelationManager;

    @ManyToOne
    @JoinColumn(name = "business_unit_leader_id")
    private BusinessLeader businessUnitLeader;

    @ManyToMany(cascade  ={
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "solution_architects_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "business_unit_id"))
    private Set<SolutionArchitect> solutionArchitect;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private LocalDateTime startDate;

    private LocalDateTime plannedEndDate;

    private LocalDateTime realEndDate;

    @ManyToMany(cascade  ={
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "business_unit_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "business_unit_id"))
    private Set<BusinessUnit> businessUnits;

    private void addBusinessUnit(BusinessUnit businessUnit){
        businessUnits.add(businessUnit);
        businessUnit.getProjects().add(this);
    }

    private void removeBusinessUnit(BusinessUnit businessUnit){
        businessUnits.remove(businessUnit);
        businessUnit.getProjects().remove(this);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public BusinessRelationManager getBusinessRelationManager() {
        return businessRelationManager;
    }

    public void setBusinessRelationManager(BusinessRelationManager businessRelationManager) {
        this.businessRelationManager = businessRelationManager;
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    public Set<SolutionArchitect> getSolutionArchitect() {
        return solutionArchitect;
    }

    public void setSolutionArchitect(Set<SolutionArchitect> solutionArchitect) {
        this.solutionArchitect = solutionArchitect;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
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

    public LocalDateTime getRealEndDate() {
        return realEndDate;
    }

    public void setRealEndDate(LocalDateTime realEndDate) {
        this.realEndDate = realEndDate;
    }

    public BusinessLeader getBusinessUnitLeader() {
        return businessUnitLeader;
    }

    public void setBusinessUnitLeader(BusinessLeader businessUnitLeader) {
        this.businessUnitLeader = businessUnitLeader;
    }

    public Set<BusinessUnit> getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(Set<BusinessUnit> businessUnits) {
        this.businessUnits = businessUnits;
    }
}
