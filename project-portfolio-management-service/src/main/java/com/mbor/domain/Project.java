package com.mbor.domain;

import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.domain.employeeinproject.SolutionArchitect;
import com.mbor.model.IProjectDTO;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Project implements IProjectDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @NaturalId
    private String projectName;

    private ProjectClass projectClass;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "resource_manager_id")
    private ResourceManager resourceManager;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "project_manager_id")
    private ProjectManager projectManager;

    @ManyToOne
    @JoinColumn(name = "business_relation_manager_id")
    @Fetch(value = FetchMode.JOIN)
    private BusinessRelationManager businessRelationManager;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "business_unit_leader_id")
    private BusinessLeader businessLeader;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST} )
    @JoinTable(name = "solution_architects_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "solution_architect_id"))
    private Set<SolutionArchitect> solutionArchitect = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private LocalDateTime startDate;

    private LocalDateTime plannedEndDate;

    @OneToMany
    private Set<RealEndDate> realEndDateSet = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE} )
    @JoinTable(name = "projects_business_units",
            joinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "business_unit_id", referencedColumnName = "id")})
    @Fetch(value = FetchMode.JOIN)
    private Set<BusinessUnit> businessUnits = new HashSet<>();

    public void addBusinessUnit(BusinessUnit businessUnit){
        this.businessUnits.add(businessUnit);
        businessUnit.getProjects().add(this);
    }

    public void removeBusinessUnit(BusinessUnit businessUnit){
        this.businessUnits.remove(businessUnit);
        businessUnit.getProjects().remove(this);
    }

    public void addRealEndDate(RealEndDate realEndDate){
        this.realEndDateSet.add(realEndDate);
        realEndDate.setProject(this);
    }

    public void removeRealEndDate(RealEndDate realEndDate){
        this.realEndDateSet.remove(realEndDate);
        realEndDate.setProject(null);
    }

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

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        resourceManager.getProjects().add(this);
    }

    public BusinessRelationManager getBusinessRelationManager() {
        return businessRelationManager;
    }

    public void setBusinessRelationManager(BusinessRelationManager businessRelationManager) {
        this.businessRelationManager = businessRelationManager;
        businessRelationManager.getProjects().add(this);
    }

    public void removeBusinessRelationManager(BusinessRelationManager businessRelationManager){
        this.businessRelationManager = null;
        businessRelationManager.getProjects().remove(this);
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(ProjectManager projectManager){
        this.projectManager = projectManager;
        projectManager.getProjects().add(this);
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

    public BusinessLeader getBusinessLeader() {
        return businessLeader;
    }

    public void setBusinessLeader(BusinessLeader businessLeader) {
        this.businessLeader = businessLeader;
        businessLeader.getProjects().add(this);
    }

    public void removeBusinessLeader(BusinessLeader businessLeader){
        this.businessLeader = null;
        businessLeader.getProjects().remove(this);
    }

    public Set<BusinessUnit> getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(Set<BusinessUnit> businessUnits) {
        this.businessUnits = businessUnits;
    }

    public void merge(Project project){
        if (project.getProjectName() != null){
            this.projectName = project.getProjectName();
        }
    }

    public ProjectClass getProjectClass() {
        return projectClass;
    }

    public void setProjectClass(ProjectClass projectClass) {
        this.projectClass = projectClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return projectName.equals(project.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName);
    }
}
