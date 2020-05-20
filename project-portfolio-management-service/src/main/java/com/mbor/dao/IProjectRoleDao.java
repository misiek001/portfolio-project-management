package com.mbor.dao;

import com.mbor.domain.employeeinproject.ProjectRole;

import java.util.List;

public interface IProjectRoleDao extends IDao<ProjectRole> {

    List<ProjectRole> findAllRoleOfEmployee(Long employeeId);

    <T extends ProjectRole> List<T> findAllDemandedRole(Class<T> clazz);
}
