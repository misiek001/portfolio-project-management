package com.mbor.service;

import com.mbor.model.ProjectRequestDTO;
import com.mbor.model.creation.ProjectRequestCreationDTO;

import java.util.List;

public interface IAPIProjectRequestService extends IAPIService<com.mbor.model.creation.ProjectRequestDTO, ProjectRequestCreationDTO, ProjectRequestDTO> {
    List<ProjectRequestDTO> findAllProjectRequestsOfBRMWithNoProject(Long brmId);
}
