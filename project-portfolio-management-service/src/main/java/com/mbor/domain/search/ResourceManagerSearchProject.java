package com.mbor.domain.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

public class ResourceManagerSearchProject extends AbstractSearchProject{

    public ResourceManagerSearchProject() {
    }

    public ResourceManagerSearchProject(List<ProjectClass> projectClassList, List<ProjectStatus> projectStatusList, Long projectId, String projectName, String businessUnitName, LocalDate projectStartDateLaterThat) {
        super(projectClassList, projectStatusList);
        this.projectId = projectId;
        this.projectName = projectName;
        this.businessUnitName = businessUnitName;
        this.projectStartDateLaterThat = projectStartDateLaterThat;
    }

    private Long projectId;

    private String projectName;

    private String businessUnitName;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectStartDateLaterThat;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public LocalDate getProjectStartDateLaterThat() {
        return projectStartDateLaterThat;
    }

    public void setProjectStartDateLaterThat(LocalDate projectStartDateLaterThat) {
        this.projectStartDateLaterThat = projectStartDateLaterThat;
    }
}
