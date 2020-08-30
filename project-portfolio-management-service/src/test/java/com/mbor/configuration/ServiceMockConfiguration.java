package com.mbor.configuration;

import com.mbor.dao.*;
import com.mbor.mapper.businessUnit.BusinessUnitMapper;
import com.mbor.mapper.employee.BusinessRelationManagerMapper;
import com.mbor.mapper.project.ProjectRequestMapper;
import com.mbor.mapper.project.ProjectAspectLineMapper;
import com.mbor.mapper.project.ProjectMapper;
import com.mbor.mapper.project.RealEndDateMapper;
import com.mbor.service.*;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class ServiceMockConfiguration {

    @Profile({"project-tests-mock"})
    @Bean
    @Primary
    IProjectDao projectDao() {
        return Mockito.mock((ProjectDao.class));
    }

    @Profile({"ProjectRequest-tests-mock"})
    @Bean
    @Primary
    IProjectRequestDao ProjectRequestDao() {
        return Mockito.mock((ProjectRequestDao.class));
    }

    @Profile({"businessunit-tests-mock"})
    @Bean
    @Primary
    IBusinessUnitDao businessUnitDao() {
        return Mockito.mock((BusinessUnitDao.class));
    }

    @Profile({"employee-tests-mock"})
    @Bean
    @Primary
    IEmployeeDao employeeDao() {
        return Mockito.mock((EmployeeDao.class));
    }

    @Profile("project-tests-mock")
    @Bean
    @Primary
    ProjectMapper projectMapper() {
        return Mockito.mock(ProjectMapper.class);
    }

    @Profile("employee-tests-mock")
    @Bean
    @Primary
    BusinessRelationManagerMapper businessRelationManagerMapper() {
        return Mockito.mock(BusinessRelationManagerMapper.class);
    }


    @Profile("project-tests-mock")
    @Bean
    @Primary
    ProjectAspectLineMapper projectAspectMapper() {
        return Mockito.mock(ProjectAspectLineMapper.class);
    }

    @Profile("project-tests-mock")
    @Bean
    @Primary
    RealEndDateMapper realEndDateMapper() {
        return Mockito.mock(RealEndDateMapper.class);
    }

    @Profile("ProjectRequest-tests-mock")
    @Bean
    @Primary
    ProjectRequestMapper ProjectRequestMapper() {
        return Mockito.mock(ProjectRequestMapper.class);
    }

    @Profile("businessunit-tests-mock")
    @Bean
    @Primary
    BusinessUnitMapper businessUnitMapper() {
        return Mockito.mock(BusinessUnitMapper.class);
    }

    @Profile("project-tests-mock")
    @Bean
    @Primary
    IEmployeeService employeeService() {
        return Mockito.mock(EmployeeService.class);
    }

    @Profile("project-tests-mock")
    @Bean
    @Primary
    IProjectRoleService projectRoleService() {
        return Mockito.mock(ProjectRoleService.class);
    }

    @Profile({"project-tests-mock", "ProjectRequest-tests-mock"})
    @Bean
    @Primary
    IBusinessUnitService businessUnitService() {
        return Mockito.mock(BusinessUnitService.class);
    }
}
