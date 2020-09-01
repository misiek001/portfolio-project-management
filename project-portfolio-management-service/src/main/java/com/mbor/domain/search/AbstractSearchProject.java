package com.mbor.domain.search;

import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;

import java.util.List;

public abstract class AbstractSearchProject implements ISearchProject {

    public AbstractSearchProject() {
    }

    public AbstractSearchProject(List<ProjectClass> projectClassList, List<ProjectStatus> projectStatusList) {
        this.projectClassList = projectClassList;
        this.projectStatusList = projectStatusList;
    }

    private List<ProjectClass> projectClassList;

    private List<ProjectStatus> projectStatusList;

    @Override
    public List<ProjectClass> getProjectClassList() {
        return projectClassList;
    }

    @Override
    public List<ProjectStatus> getProjectStatusList() {
        return projectStatusList;
    }

    public void setProjectClassList(List<ProjectClass> projectClassList) {
        this.projectClassList = projectClassList;
    }

    public void setProjectStatusList(List<ProjectStatus> projectStatusList) {
        this.projectStatusList = projectStatusList;
    }
}
