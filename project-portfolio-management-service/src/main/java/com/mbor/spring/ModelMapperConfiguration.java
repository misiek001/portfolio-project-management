package com.mbor.spring;

import com.mbor.domain.*;
import com.mbor.domain.search.ResourceManagerSearchProject;
import com.mbor.model.*;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.search.ResourceManagerSearchProjectDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@ComponentScan(basePackages = {"com.mbor.mapper"})
public class ModelMapperConfiguration {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    ModelMapper resourceManagerSearchProjectModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(ResourceManagerSearchProject.class, ResourceManagerSearchProjectDTO.class)
                .addMappings(mapping -> mapping.using(new AbstractConverter<List<ProjectClass>, List<ProjectClassDTO>>() {
                    @Override
                    protected List<ProjectClassDTO> convert(List<ProjectClass> source) {
                        return source.stream()
                                .map(element -> ProjectClassDTO.valueOf(element.name()))
                                .collect(Collectors.toList());
                    }
                }).map(ResourceManagerSearchProject::getProjectClassList, ResourceManagerSearchProjectDTO::setProjectClassDTOList))
                .addMappings(mapping -> mapping.using(new AbstractConverter<List<ProjectStatus>, List<ProjectStatusDTO>>() {
                    @Override
                    protected List<ProjectStatusDTO> convert(List<ProjectStatus> source) {
                        return source.stream()
                                .map(element -> ProjectStatusDTO.valueOf(element.name()))
                                .collect(Collectors.toList());
                    }
                }).map(ResourceManagerSearchProject::getProjectStatusList, ResourceManagerSearchProjectDTO::setProjectStatusDTOList));
        return modelMapper;
    }

    @Bean
    ModelMapper businessUnitModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setImplicitMappingEnabled(false)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(BusinessUnit.class, BusinessUnitDTO.class)
                .addMappings(mapping -> mapping.map(BusinessUnit::getId, BusinessUnitDTO::setId))
                .addMappings(mapping -> mapping.map(BusinessUnit::getName, BusinessUnitDTO::setName))
                .addMappings(mapping -> mapping.using(new AbstractConverter<Set<Employee>, Set<EmployeeDTO>>() {
                    @Override
                    protected Set<com.mbor.model.EmployeeDTO> convert(Set<Employee> source) {
                        if (source == null) {
                            return null;
                        }

                        Set<EmployeeDTO> employees = new HashSet<>();
                        source.forEach(employee -> {
                            Class<?> clazz = employee.getClass();
                            Class<?> dtoClass = null;
                            try {
                                dtoClass = Class.forName("com.mbor.model." + clazz.getSimpleName() + "DTO");
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException();
                            }
                            EmployeeDTO employeeDTO = null;
                            try {
                                employeeDTO = (EmployeeDTO) dtoClass.newInstance();
                            } catch (InstantiationException e) {

                            } catch (IllegalAccessException e) {

                            }
                            employeeDTO.setId(employee.getId());
                            employeeDTO.setFirstName(employee.getFirstName());
                            employeeDTO.setLastName(employee.getLastName());
                            employees.add(employeeDTO);
                        });
                        return employees;
                    }
                }).map(BusinessUnit::getEmployees, BusinessUnitDTO::setEmployees))
                .addMappings(mapping -> mapping.using(new AbstractConverter<BusinessRelationManager, BusinessRelationManagerDTO>() {
                    @Override
                    protected BusinessRelationManagerDTO convert(BusinessRelationManager source) {
                        if (source == null) {
                            return null;
                        }
                        BusinessRelationManagerDTO businessRelationManagerDTO = new BusinessRelationManagerDTO();
                        businessRelationManagerDTO.setFirstName(source.getFirstName());
                        businessRelationManagerDTO.setLastName(source.getLastName());
                        businessRelationManagerDTO.setUserName(source.getUserName());
                        return businessRelationManagerDTO;
                    }
                }).map(BusinessUnit::getBusinessRelationManager, BusinessUnitDTO::setBusinessRelationManager))
                .addMappings(mapping -> mapping.skip(BusinessUnitDTO::setPrimaryProjects))
                .addMappings(mapping -> mapping.skip(BusinessUnitDTO::setSecondaryProjects));
        return modelMapper;
    }

    @Bean
    ModelMapper ProjectRequestModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setImplicitMappingEnabled(false)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(ProjectRequest.class, ProjectRequestDTO.class)
                .addMappings(mapping -> mapping.map(ProjectRequest::getBusinessUnit, ProjectRequestDTO::setBusinessUnit))
                .addMappings(mapping -> mapping.map(ProjectRequest::getProject, ProjectRequestDTO::setProject))
                .addMappings(mapping -> mapping.map(ProjectRequest::getId, ProjectRequestDTO::setId))
                .addMappings(mapping -> mapping.map(ProjectRequest::getDescription, ProjectRequestDTO::setDescription))
                .addMappings(mapping -> mapping.map(ProjectRequest::getProjectName, ProjectRequestDTO::setProjectName))
                .addMappings(mapping -> mapping.using(new AbstractConverter<BusinessRelationManager, BusinessRelationManagerDTO>() {
                    @Override
                    protected BusinessRelationManagerDTO convert(BusinessRelationManager source) {
                        if (source == null) {
                            return null;
                        }
                        BusinessRelationManagerDTO businessRelationManagerDTO = new BusinessRelationManagerDTO();
                        businessRelationManagerDTO.setId(source.getId());
                        businessRelationManagerDTO.setFirstName(source.getFirstName());
                        businessRelationManagerDTO.setLastName(source.getLastName());
                        businessRelationManagerDTO.setUserName(source.getUserName());
                        BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
                        if (source.getBusinessUnit() != null) {
                            businessUnitDTO.setId(source.getBusinessUnit().getId());
                            businessUnitDTO.setName(source.getBusinessUnit().getName());
                            businessRelationManagerDTO.setBusinessUnit(businessUnitDTO);
                        }
                        return businessRelationManagerDTO;
                    }
                }).map(ProjectRequest::getBusinessRelationManager, ProjectRequestDTO::setBusinessRelationManager));
        modelMapper.typeMap(BusinessUnit.class, BusinessUnitDTO.class)
                .addMappings(mapping -> mapping.map(BusinessUnit::getId, BusinessUnitDTO::setId));
        modelMapper.typeMap(Project.class, ProjectDTO.class)
                .addMappings(mapping -> mapping.map(Project::getId, ProjectDTO::setId))
                .addMappings(mapping -> mapping.map(Project::getProjectName, ProjectDTO::setProjectName));
        return modelMapper;
    }

    @Bean
    ModelMapper projectModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setImplicitMappingEnabled(false)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(Project.class, ProjectDTO.class)
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getProjectRequestDTO().getBusinessUnit().setPrimaryProjects(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getProjectRequestDTO().getBusinessUnit().setSecondaryProjects(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getProjectRequestDTO().getBusinessUnit().setBusinessRelationManager(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getProjectRequestDTO().getBusinessUnit().setEmployees(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getProjectRequestDTO().getBusinessRelationManager().setBusinessUnit(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getProjectRequestDTO().getBusinessRelationManager().setDirector(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getProjectRequestDTO().getBusinessRelationManager().setProjects(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getProjectRequestDTO().getBusinessRelationManager().setAssignedBusinessUnits(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getProjectRequestDTO().setProject(null)))
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getResourceManager().getEmployee().setProjectRoleSet(null))))
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getResourceManager().getEmployee().setProjectRoleSet(null))))
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getResourceManager().getEmployee().setEmployees(null))))
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getResourceManager().getEmployee().setDirector(null))))
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getProjectManager().getEmployee().setProjectRoleSet(null))))
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getProjectManager().getEmployee().setProjectRoleSet(null))))
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getProjectManager().getEmployee().setBusinessUnit(null))))
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getBusinessLeader().getEmployee().setBusinessUnit(null))))
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getBusinessLeader().getEmployee().setBusinessUnit(null))))
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getBusinessLeader().getEmployee().setProjectRoleSet(null))))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getBusinessRelationManager().setProjects(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getBusinessRelationManager().setDirector(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getBusinessRelationManager().getBusinessUnit().setEmployees(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getPrimaryBusinessUnit().setEmployees(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getPrimaryBusinessUnit().setBusinessRelationManager(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getPrimaryBusinessUnit().setPrimaryProjects(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getPrimaryBusinessUnit().setSecondaryProjects(null)))
                .implicitMappings();
        modelMapper.typeMap(Project.class, ProjectCreatedDTO.class)
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getBusinessLeader().getEmployee().setBusinessUnit(null))))
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getBusinessLeader().getEmployee().setBusinessUnit(null))))
                .addMappings(mapping -> mapping.skip(((destination, value) -> destination.getBusinessLeader().getEmployee().setProjectRoleSet(null))))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getBusinessRelationManager().setProjects(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getBusinessRelationManager().setDirector(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getBusinessRelationManager().getBusinessUnit().setEmployees(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getPrimaryBusinessUnit().setEmployees(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getPrimaryBusinessUnit().setBusinessRelationManager(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getPrimaryBusinessUnit().setPrimaryProjects(null)))
                .addMappings(mapping -> mapping.skip((destination, value) -> destination.getPrimaryBusinessUnit().setSecondaryProjects(null)))
                .implicitMappings();
        return modelMapper;
    }

    @Bean
    ModelMapper brmModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(BusinessRelationManager.class, BusinessRelationManagerDTO.class)
                .addMappings(mapping -> mapping.skip(BusinessRelationManagerDTO::setProjects))
                .addMappings(mapping -> mapping.skip(BusinessRelationManagerDTO::setUserName));
        modelMapper.typeMap(BusinessUnit.class, BusinessUnitDTO.class)
                .addMappings(mapping -> mapping.skip(BusinessUnitDTO::setPrimaryProjects))
                .addMappings(mapping -> mapping.skip(BusinessUnitDTO::setSecondaryProjects))
                .addMappings(mapping -> mapping.skip(BusinessUnitDTO::setEmployees))
                .addMappings(mapping -> mapping.skip(BusinessUnitDTO::setName));
        modelMapper.typeMap(Director.class, DirectorDTO.class)
                .addMappings(mapping -> mapping.skip(DirectorDTO::setBusinessRelationManagers))
                .addMappings(mapping -> mapping.skip(DirectorDTO::setUserName))
                .addMappings(mapping -> mapping.skip(DirectorDTO::setSupervisors));
        modelMapper.validate();
        return modelMapper;
    }
}
