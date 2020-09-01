package com.mbor.domain.search;

import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;

import java.util.List;

public interface ISearchProject {
    List<ProjectClass> getProjectClassList();

    List<ProjectStatus> getProjectStatusList();

}
