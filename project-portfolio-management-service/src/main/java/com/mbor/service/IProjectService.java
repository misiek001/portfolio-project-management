package com.mbor.service;

import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;

public interface IProjectService<T> extends IService<T> {

     ProjectCreatedDTO save(ProjectCreationDTO projectCreationDTO);
}
