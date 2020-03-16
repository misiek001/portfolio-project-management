package com.mbor.model.search;

import com.mbor.model.ProjectClassDTO;
import com.mbor.model.ProjectStatusDTO;

import java.util.List;

public class ResourceManagerSearchProjectDTO {

    private Long projectId;

    private String projectName;

    private List<ProjectClassDTO> projectClassDTOList;

    private List<ProjectStatusDTO> projectStatusDTOList;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

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

    public List<ProjectStatusDTO> getProjectStatusDTOList() {
        return projectStatusDTOList;
    }

    public void setProjectStatusDTOList(List<ProjectStatusDTO> projectStatusDTOList) {
        this.projectStatusDTOList = projectStatusDTOList;
    }
}
