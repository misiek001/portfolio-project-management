package com.mbor.model.search;

import com.mbor.model.ProjectClassDTO;
import com.mbor.model.ProjectStatusDTO;

import java.util.List;

public interface ISearchProjectDTO {

    List<ProjectClassDTO> getProjectClassDTOList();

    List<ProjectStatusDTO> getProjectStatusDTOList();
}
