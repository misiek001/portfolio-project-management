package com.mbor.model.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.mbor.model.ProjectClassDTO;
import com.mbor.model.ProjectStatusDTO;

import java.time.LocalDate;
import java.util.List;

public class SearchProjectDTO {

    private String projectName;

    private List<ProjectClassDTO> projectClassDTOList;

    private String businessUnitName;

    private List<ProjectStatusDTO> projectStatusDTOList;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectStartDate;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProjectClassDTO> getProjectClassDTOList() {
        return projectClassDTOList;
    }

    public void setProjectClassDTOList(List<ProjectClassDTO> projectClassDTOList) {
        this.projectClassDTOList = projectClassDTOList;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public List<ProjectStatusDTO> getProjectStatusDTOList() {
        return projectStatusDTOList;
    }

    public void setProjectStatusDTOList(List<ProjectStatusDTO> projectStatusDTOList) {
        this.projectStatusDTOList = projectStatusDTOList;
    }

    public LocalDate getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(LocalDate projectStartDate) {
        this.projectStartDate = projectStartDate;
    }
}
