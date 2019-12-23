package com.misiek.mapping;

import com.misiek.domain.Director;
import com.misiek.domain.Project;
import com.misiek.domain.Supervisor;
import com.misiek.domain.employeeinproject.ResourceManager;
import com.misiek.model.DirectorDTO;
import com.misiek.model.ProjectDTO;
import com.misiek.model.ResourceManagerDTO;
import com.misiek.model.SupervisorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EntityToDTOMapper extends Mapper {

    public EntityToDTOMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    public ProjectDTO mapProjectEntityToDTO(Project project) {
        ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
        projectDTO.setResourceManager(mapResourceManagerEntityToDTO(project.getResourceManager()));
        return projectDTO;
    }

    public ResourceManagerDTO mapResourceManagerEntityToDTO(ResourceManager resourceManager) {
        SupervisorDTO supervisorDTO = mapSupervisorEntityToDTO(resourceManager.getEmployee());
        ResourceManagerDTO resourceManagerDTO = modelMapper.map(resourceManager, ResourceManagerDTO.class);
        resourceManagerDTO.setEmployee(supervisorDTO);
        return resourceManagerDTO;
    }

    public SupervisorDTO mapSupervisorEntityToDTO(Supervisor supervisor) {
        SupervisorDTO supervisorDTO = modelMapper.map(supervisor, SupervisorDTO.class);
        supervisorDTO.setDirectorDTO(mapDirectorEntityToDTO(supervisor.getDirector()));
        return supervisorDTO;
    }

    public DirectorDTO mapDirectorEntityToDTO(Director director) {
        return modelMapper.map(director, DirectorDTO.class);
    }
}
