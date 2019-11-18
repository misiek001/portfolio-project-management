package com.misiek.dao;

import com.misiek.domain.Project;

import java.util.Optional;

public interface ProjectDao {

    Optional<Project> save(Project project);

    Optional<Project> find(Long id);

}
