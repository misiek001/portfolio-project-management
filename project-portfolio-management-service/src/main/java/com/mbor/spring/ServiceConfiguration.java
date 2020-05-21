package com.mbor.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.Director;
import com.mbor.model.BusinessRelationManagerDTO;
import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.DirectorDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.mbor.dao", "com.mbor.service", "com.mbor.mapper", "com.mbor.spring", "com.mbor.dataloader"})
public class ServiceConfiguration {

    @Bean
    ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.validate();
        return modelMapper;
    }

    @Bean
    ModelMapper projectModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(BusinessRelationManager.class, BusinessRelationManagerDTO.class)
                .addMappings(mapping -> mapping.skip(BusinessRelationManagerDTO::setProjects));
        modelMapper.typeMap(BusinessUnit.class, BusinessUnitDTO.class)
                .addMappings(mapping -> mapping.skip(BusinessUnitDTO::setPrimaryProjects))
                .addMappings(mapping -> mapping.skip(BusinessUnitDTO::setSecondaryProjects))
                .addMappings(mapping -> mapping.skip(BusinessUnitDTO::setEmployees));
        modelMapper.validate();
        return modelMapper;
    }

    @Bean
    ModelMapper brmModelMapper(){
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

    @Bean
    ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;


    }
}
