package com.mbor.service;

import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;

import java.util.Optional;

public interface IProjectService<T> extends IService<T> {

     Optional<ProjectCreatedDTO> save(ProjectCreationDTO projectCreationDTO);
}
