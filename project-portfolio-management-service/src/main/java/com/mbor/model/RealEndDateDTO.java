package com.mbor.model;

import com.mbor.domain.Project;

import java.time.LocalDateTime;

public class RealEndDateDTO {


    private Project project;

    private LocalDateTime endDate;

    private String reason;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
