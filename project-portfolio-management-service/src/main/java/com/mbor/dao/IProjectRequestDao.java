package com.mbor.dao;

import com.mbor.domain.ProjectRequest;

import java.util.List;

public interface IProjectRequestDao extends IDao<ProjectRequest> {

    List<ProjectRequest> getAllProjectRequestsOfBRMWithNoProject(Long brmId);
}
