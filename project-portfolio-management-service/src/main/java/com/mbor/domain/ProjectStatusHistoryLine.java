package com.mbor.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class ProjectStatusHistoryLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime creationTime;

    @Enumerated(EnumType.STRING)
    private ProjectStatus previousStatus;

    @Enumerated(EnumType.STRING)
    private ProjectStatus currentStatus;

    private String description;

    @ManyToOne
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public ProjectStatus getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(ProjectStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    public ProjectStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(ProjectStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectStatusHistoryLine that = (ProjectStatusHistoryLine) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
