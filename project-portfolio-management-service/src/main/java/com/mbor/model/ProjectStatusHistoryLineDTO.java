package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mbor.model.customserializer.LocalDateTimeDeserializer;
import com.mbor.model.customserializer.LocalDateTimeSerializer;
import com.mbor.model.views.Views;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

public class ProjectStatusHistoryLineDTO  extends IdDTO{


    @JsonView(Views.Public.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creationTime;

    @JsonView(Views.Public.class)
    @Enumerated(EnumType.STRING)
    private ProjectStatusDTO previousStatus;

    @JsonView(Views.Public.class)
    @Enumerated(EnumType.STRING)
    private ProjectStatusDTO currentStatus;

    @JsonView(Views.Public.class)
    private String description;

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
