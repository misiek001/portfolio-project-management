package com.mbor.domain.search;

import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;

import java.util.List;

public class SupervisorSearchProject extends AbstractSearchProject {

    private Long projectId;
    private String projectName;
    private List<Long> projectManagerIdList;
    private List<Long> solutionArchitectIdList;

    public SupervisorSearchProject() {
    }

    public SupervisorSearchProject(List<ProjectClass> projectClassList, List<ProjectStatus> projectStatusList, Long projectId, String projectName, List<Long> projectManagerIdList, List<Long> solutionArchitectIdList) {
        super(projectClassList, projectStatusList);
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectManagerIdList = projectManagerIdList;
        this.solutionArchitectIdList = solutionArchitectIdList;
    }

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
