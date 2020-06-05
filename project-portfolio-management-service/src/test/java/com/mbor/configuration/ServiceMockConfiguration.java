package com.mbor.configuration;

import com.mbor.dao.DemandSheetDao;
import com.mbor.dao.IDemandSheetDao;
import com.mbor.dao.IProjectDao;
import com.mbor.dao.ProjectDao;
import com.mbor.mapper.DemandSheetMapper;
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
public class ServiceMockConfiguration {

    @Profile({"project-tests-mock"})
    @Bean
    @Primary
    IProjectDao projectDao() {
        return Mockito.mock((ProjectDao.class));
    }

    @Profile({"demandsheet-tests-mock"})
    @Bean
    @Primary
    IDemandSheetDao demandSheetDao() {
        return Mockito.mock((DemandSheetDao.class));
    }

    @Profile("project-tests-mock")
    @Bean
    @Primary
    ProjectMapper projectMapper() {
        return Mockito.mock(ProjectMapper.class);
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

    @Profile("demandsheet-tests-mock")
    @Bean
    @Primary
    DemandSheetMapper demandSheetMapper() {
        return Mockito.mock(DemandSheetMapper.class);
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

    @Profile({"project-tests-mock", "demandsheet-tests-mock"})
    @Bean
    @Primary
    IBusinessUnitService businessUnitService() {
        return Mockito.mock(BusinessUnitService.class);
    }
}
