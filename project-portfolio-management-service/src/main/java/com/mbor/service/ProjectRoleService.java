package com.mbor.service;

import com.mbor.dao.IDao;
import com.mbor.dao.IProjectRoleDao;
import com.mbor.domain.employeeinproject.*;
import com.mbor.exception.ProjectRoleClassNotFoundException;
import com.mbor.mapper.ProjectRoleMapper;
import com.mbor.model.ProjectRoleDTO;
import com.mbor.model.ProjectRolesDTO;
import com.mbor.model.creation.ProjectRoleCreatedDTO;
import com.mbor.model.creation.ProjectRoleCreationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ProjectRoleService extends RawService<ProjectRole> implements IProjectRoleService {

    private final IProjectRoleDao projectRoleDao;
    private final ProjectRoleMapper projectRoleMapper;
    private  Set<String>  availableProjectRoleSet;

    @Autowired
    public ProjectRoleService(IProjectRoleDao projectRoleDao, ProjectRoleMapper projectRoleMapper) {
        this.projectRoleDao = projectRoleDao;
        this.projectRoleMapper = projectRoleMapper;
        initSet();
    }

    @Override
    public IDao getDao() {
        return projectRoleDao;
    }

    @Override
    public List<ProjectRole> findAllRoleOfEmployee(Long id){
        return projectRoleDao.findAllRoleOfEmployee(id);
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public ProjectRoleDTO find(Long id) {
        return null;
    }

    @Override
    public ProjectRoleCreatedDTO save(ProjectRoleCreationDTO projectRoleCreationDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public <T extends ProjectRole> ProjectRolesDTO findAllDemandRole(Class<T> t) {
        List<T> allDemandedRoles = projectRoleDao.findAllDemandedRole(t);
        List<ProjectRoleDTO> result = new ArrayList<>();
        allDemandedRoles.forEach(projectRole -> result.add(projectRoleMapper.convertToDto(projectRole)));
        ProjectRolesDTO projectRolesDTO = new ProjectRolesDTO();
        projectRolesDTO.setProjectRoles(result);
        return projectRolesDTO;
    }

    private void initSet(){
        availableProjectRoleSet = new HashSet<>();
        availableProjectRoleSet.add(ProjectManager.class.getSimpleName());
        availableProjectRoleSet.add(ResourceManager.class.getSimpleName());
        availableProjectRoleSet.add(BusinessLeader.class.getSimpleName());
        availableProjectRoleSet.add(SolutionArchitect.class.getSimpleName());
    }

    @Override
    public Class returnProjectRoleClass(String projectRole) {
        if(!availableProjectRoleSet.contains(projectRole)){
            throw new ProjectRoleClassNotFoundException();
        }
        try {
            return Class.forName("com.mbor.domain.employeeinproject." +projectRole);
        } catch (ClassNotFoundException e) {
            throw new ProjectRoleClassNotFoundException();
        }
    }
}
