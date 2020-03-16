package com.mbor.service;

import com.mbor.domain.employeeinproject.ProjectRole;

import java.util.List;

public interface IProjectRoleService<T> extends IService<T> {

    List<ProjectRole> findAllRoleOfEmployee(Long id);
}
