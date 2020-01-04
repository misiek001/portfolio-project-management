package com.misiek.service;

import com.misiek.model.creation.ProjectCreatedDTO;
import com.misiek.model.creation.ProjectCreationDTO;

import java.util.Optional;

public interface IProjectService<T> extends IService<T> {

     Optional<ProjectCreatedDTO> save(ProjectCreationDTO projectCreationDTO);
}
