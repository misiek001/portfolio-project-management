package com.misiek.domain;

import com.misiek.domain.employeeinproject.BusinessLeader;
import com.misiek.domain.employeeinproject.ProjectManager;
import com.misiek.domain.employeeinproject.ResourceManager;
import com.misiek.domain.employeeinproject.SolutionArchitect;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Project")
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @ManyToOne
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

    @OneToMany
    private Set<RealEndDate> realEndDateSet = new HashSet<>();

    @ManyToMany(cascade  ={
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "business_unit_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "business_unit_id"))
    private Set<BusinessUnit> businessUnits;

    private void addBusinessUnit(BusinessUnit businessUnit){
        this.businessUnits.add(businessUnit);
        businessUnit.getProjects().add(this);
    }

    private void removeBusinessUnit(BusinessUnit businessUnit){
        this.businessUnits.remove(businessUnit);
        businessUnit.getProjects().remove(this);
    }

    private void addRealEndDate(RealEndDate realEndDate){
        this.realEndDateSet.add(realEndDate);
        realEndDate.setProject(this);
    }

    private void removeRealEndDate(RealEndDate realEndDate){
        this.realEndDateSet.remove(realEndDate);
        realEndDate.setProject(null);
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

    public Set<RealEndDate> getRealEndDateSet() {
        return realEndDateSet;
    }

    public void setRealEndDateSet(Set<RealEndDate> realEndDateSet) {
        this.realEndDateSet = realEndDateSet;
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



    public void merge(Project project){
        if (project.getName() != null){
            this.name = project.getName();
        }
    }
}
