package com.mbor.domain.projectaspect;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProjectAspect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    protected String name;

    protected String description;

    protected AspectStatus aspectStatus;

    @OneToOne
    protected ProjectAspectLine projectAspectLine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AspectStatus getAspectStatus() {
        return aspectStatus;
    }

    public String getAspectStatusName(){
        return aspectStatus.name();
    }

    public void setAspectStatus(AspectStatus aspectStatus) {
        this.aspectStatus = aspectStatus;
    }

    public ProjectAspectLine getProjectAspectLine() {
        return projectAspectLine;
    }

    public void setProjectAspectLine(ProjectAspectLine projectAspectLine) {
        this.projectAspectLine = projectAspectLine;
    }
}
