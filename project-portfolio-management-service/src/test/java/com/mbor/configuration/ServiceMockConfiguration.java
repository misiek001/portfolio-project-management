package com.mbor.configuration;

import com.mbor.dao.IProjectDao;
import com.mbor.dao.ProjectDao;
import com.mbor.mapper.ProjectAspectLineMapper;
import com.mbor.mapper.ProjectMapper;
import com.mbor.mapper.RealEndDateMapper;
import com.mbor.service.*;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("project-tests-mock")
public class ServiceMockConfiguration {

    @Bean
    @Primary
    IProjectDao projectDao() {
        return Mockito.mock((ProjectDao.class));
    }

    @Bean
    @Primary
    ProjectMapper projectMapper(){
        return Mockito.mock(ProjectMapper.class);
    }

    @Bean
    @Primary
    ProjectAspectLineMapper projectAspectMapper(){
        return Mockito.mock(ProjectAspectLineMapper.class);
    }

    @Bean
    @Primary
    RealEndDateMapper realEndDateMapper(){
        return Mockito.mock(RealEndDateMapper.class);
    }

    @Bean
    @Primary
    IEmployeeService employeeService(){
        return Mockito.mock(EmployeeService.class);
    }

    @Bean
    @Primary
    IProjectRoleService projectRoleService(){
        return Mockito.mock(ProjectRoleService.class);
    }

    @Bean
    @Primary
    IBusinessUnitService businessUnitService(){
        return Mockito.mock(BusinessUnitService.class);
    }

}
