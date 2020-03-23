package com.mbor.domain.projectaspect;

import com.mbor.domain.Project;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ProjectAspectLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private BudgetAspect budgetAspect;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private ResourcesAspect resourcesAspect;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private ScopeAspect scopeAspect;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private DeadlineAspect deadlineAspect;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date creationTime;

    @OneToOne
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BudgetAspect getBudgetAspect() {
        return budgetAspect;
    }

    public void setBudgetAspect(BudgetAspect budgetAspect) {
        this.budgetAspect = budgetAspect;
    }

    public ResourcesAspect getResourcesAspect() {
        return resourcesAspect;
    }

    public void setResourcesAspect(ResourcesAspect resourcesAspect) {
        this.resourcesAspect = resourcesAspect;
    }

    public ScopeAspect getScopeAspect() {
        return scopeAspect;
    }

    public void setScopeAspect(ScopeAspect scopeAspect) {
        this.scopeAspect = scopeAspect;
    }

    public DeadlineAspect getDeadlineAspect() {
        return deadlineAspect;
    }

    public void setDeadlineAspect(DeadlineAspect deadlineAspect) {
        this.deadlineAspect = deadlineAspect;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
