package com.mbor.domain.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

public class SearchProject extends AbstractSearchProject {

    public SearchProject() {
    }

    public SearchProject(List<ProjectClass> projectClassList, List<ProjectStatus> projectStatusList, String projectName, String businessUnitName, LocalDate projectStartDateLaterThat) {
        super(projectClassList, projectStatusList);
        this.projectName = projectName;
        this.businessUnitName = businessUnitName;
        this.projectStartDateLaterThat = projectStartDateLaterThat;
    }

    private String projectName;

    private String businessUnitName;

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
