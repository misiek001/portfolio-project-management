package com.mbor.domain;

import javax.persistence.*;

@Entity
public class DemandSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    private String projectName;

    private String description;

    @OneToOne
    private BusinessUnit businessUnit;

    @OneToOne(mappedBy = "demandSheet")
    private Project project;

    @ManyToOne
    private BusinessRelationManager businessRelationManager;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public BusinessRelationManager getBusinessRelationManager() {
        return businessRelationManager;
    }

    public void setBusinessRelationManager(BusinessRelationManager businessRelationManager) {
        this.businessRelationManager = businessRelationManager;
    }
}
