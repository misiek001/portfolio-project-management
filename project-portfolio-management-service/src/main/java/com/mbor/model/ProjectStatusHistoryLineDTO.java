package com.mbor.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mbor.model.customserializer.LocalDateTimeDeserializer;
import com.mbor.model.customserializer.LocalDateTimeSerializer;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

public class ProjectStatusHistoryLineDTO {

    private Long id;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creationTime;

    @Enumerated(EnumType.STRING)
    private ProjectStatusDTO previousStatus;

    @Enumerated(EnumType.STRING)
    private ProjectStatusDTO currentStatus;

    private String description;

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

    public ProjectStatusDTO getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(ProjectStatusDTO previousStatus) {
        this.previousStatus = previousStatus;
    }

    public ProjectStatusDTO getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(ProjectStatusDTO currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
