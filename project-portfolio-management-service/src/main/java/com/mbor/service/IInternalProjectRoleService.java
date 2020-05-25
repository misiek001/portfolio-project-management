package com.mbor.service;

import com.mbor.domain.employeeinproject.ProjectRole;

import java.util.List;

public interface IInternalProjectRoleService extends IInternalService<ProjectRole> {
    List<ProjectRole> findAllRoleOfEmployee(Long id);
}
