package com.mbor.mapper.project;

import com.mbor.domain.Project;
import com.mbor.domain.ProjectClass;
import com.mbor.mapper.CreationPojoMapper;
import com.mbor.model.ProjectDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper extends CreationPojoMapper<ProjectDTO, Project, ProjectCreationDTO, ProjectCreatedDTO> {

    public ProjectMapper(@Qualifier(value = "projectModelMapper") ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProjectDTO convertEntityToDto(Project project) {
        return  modelMapper.map(project, ProjectDTO.class);
    }

    @Override
    public Project convertCreationDtoToEntity(ProjectCreationDTO projectCreationDTO) {
        Project project = new Project();
        project.setProjectName(projectCreationDTO.getProjectName());
        project.setProjectClass(ProjectClass.valueOf(projectCreationDTO.getProjectClass().name()));
        return project;
    }

    @Override
    public ProjectCreatedDTO convertEntityToCreatedDto(Project project) {
        return modelMapper.map(project, ProjectCreatedDTO.class);
    }

}
