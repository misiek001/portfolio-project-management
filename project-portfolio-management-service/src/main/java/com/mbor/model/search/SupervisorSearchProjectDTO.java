package com.mbor.model.search;

import com.mbor.model.ProjectClassDTO;
import com.mbor.model.ProjectManagerDTO;
import com.mbor.model.ProjectStatusDTO;
import com.mbor.model.SolutionArchitectDTO;

import java.util.List;

public class SupervisorSearchProjectDTO {

    private Long projectId;

    private String projectName;

    private List<ProjectClassDTO> projectClassDTOList;

    private List<ProjectStatusDTO> projectStatusDTOList;

    private List<ProjectManagerDTO> projectManagerDTOList;

    private List<SolutionArchitectDTO> solutionArchitectDTOList;

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

    public List<ProjectManagerDTO> getProjectManagerDTOList() {
        return projectManagerDTOList;
    }

    public void setProjectManagerDTOList(List<ProjectManagerDTO> projectManagerDTOList) {
        this.projectManagerDTOList = projectManagerDTOList;
    }

    public List<SolutionArchitectDTO> getSolutionArchitectDTOList() {
        return solutionArchitectDTOList;
    }

    public void setSolutionArchitectDTOList(List<SolutionArchitectDTO> solutionArchitectDTOList) {
        this.solutionArchitectDTOList = solutionArchitectDTOList;
    }
}
