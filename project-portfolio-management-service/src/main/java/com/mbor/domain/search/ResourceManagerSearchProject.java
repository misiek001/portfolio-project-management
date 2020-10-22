package com.mbor.domain.search;

import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;

import java.util.List;

public class ResourceManagerSearchProject extends AbstractSearchProject {

    private Long projectId;
    private String projectName;

    public ResourceManagerSearchProject() {
    }

    public ResourceManagerSearchProject(List<ProjectClass> projectClassList, List<ProjectStatus> projectStatusList, Long projectId, String projectName) {
        super(projectClassList, projectStatusList);
        this.projectId = projectId;
        this.projectName = projectName;
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
}
