package com.mbor.service;

import com.mbor.domain.employeeinproject.ProjectRole;
import com.mbor.model.ProjectRoleDTO;
import com.mbor.model.ProjectRolesDTO;
import com.mbor.model.creation.ProjectRoleCreatedDTO;
import com.mbor.model.creation.ProjectRoleCreationDTO;

public interface IAPIProjectRoleService extends IAPIService<ProjectRoleCreatedDTO, ProjectRoleCreationDTO, ProjectRoleDTO>  {

   <T extends ProjectRole> ProjectRolesDTO findAllDemandRole(Class<T> t);

   Class returnProjectRoleClass(String projectRole);

}
