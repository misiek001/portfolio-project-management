package com.mbor.model.search;

import com.mbor.model.ProjectClassDTO;
import com.mbor.model.ProjectStatusDTO;

import java.util.List;

public class SupervisorSearchProjectDTO implements ISearchProjectDTO {

    private Long projectId;

    private String projectName;

    private List<ProjectClassDTO> projectClassDTOList;

    private List<ProjectStatusDTO> projectStatusDTOList;

    private List<Long> projectManagerIdList;

    private List<Long> solutionArchitectIdList;

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

    public List<Long> getProjectManagerIdList() {
        return projectManagerIdList;
    }

    public void setProjectManagerIdList(List<Long> projectManagerIdList) {
        this.projectManagerIdList = projectManagerIdList;
    }

    public List<Long> getSolutionArchitectIdList() {
        return solutionArchitectIdList;
    }

    public void setSolutionArchitectIdList(List<Long> solutionArchitectIdList) {
        this.solutionArchitectIdList = solutionArchitectIdList;
    }
}
