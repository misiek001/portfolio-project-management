package com.mbor.model.search;

import com.mbor.model.ProjectClassDTO;
import com.mbor.model.ProjectManagerDTO;
import com.mbor.model.ProjectStatusDTO;
import com.mbor.model.SolutionArchitectDTO;

import java.util.List;

public class SupervisorSearchProjectDTO {

    private Long id;

    private String projectName;

    private List<ProjectClassDTO> projectClassDTOList;

    private List<ProjectStatusDTO> projectStatusDTOList;

    private ProjectManagerDTO projectManagerDTO;

    private SolutionArchitectDTO solutionArchitectDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProjectManagerDTO getProjectManagerDTO() {
        return projectManagerDTO;
    }

    public void setProjectManagerDTO(ProjectManagerDTO projectManagerDTO) {
        this.projectManagerDTO = projectManagerDTO;
    }

    public SolutionArchitectDTO getSolutionArchitectDTO() {
        return solutionArchitectDTO;
    }

    public void setSolutionArchitectDTO(SolutionArchitectDTO solutionArchitectDTO) {
        this.solutionArchitectDTO = solutionArchitectDTO;
    }
}
