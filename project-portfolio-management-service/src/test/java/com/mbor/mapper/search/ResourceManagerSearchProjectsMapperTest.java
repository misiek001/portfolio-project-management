package com.mbor.mapper.search;

import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;
import com.mbor.domain.search.ResourceManagerSearchProject;
import com.mbor.model.ProjectClassDTO;
import com.mbor.model.ProjectStatusDTO;
import com.mbor.model.search.ResourceManagerSearchProjectDTO;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
class ResourceManagerSearchProjectsMapperTest {

    @Autowired
    private ResourceManagerSearchProjectsMapper resourceManagerSearchProjectsMapper;

    private static ResourceManagerSearchProject resourceManagerSearchProject;
    private static ResourceManagerSearchProjectDTO resourceManagerSearchProjectDTO;

    @BeforeAll
    static void setup(){
        List<ProjectClass> projectClassList = new ArrayList<>();
        projectClassList.add(ProjectClass.I);
        projectClassList.add(ProjectClass.II);
        List<ProjectStatus> projectStatusList = new ArrayList<>();
        projectStatusList.add(ProjectStatus.ANALYSIS);
        projectStatusList.add(ProjectStatus.AWAITING);

        resourceManagerSearchProject = new ResourceManagerSearchProject(projectClassList, projectStatusList, 1L, "ProjectName");

        List<ProjectClassDTO> projectClassDTOList = new ArrayList<>();
        projectClassDTOList.add(ProjectClassDTO.I);
        projectClassDTOList.add(ProjectClassDTO.II);
        List<ProjectStatusDTO> projectStatusDTOList = new ArrayList<>();
        projectStatusDTOList.add(ProjectStatusDTO.ANALYSIS);
        projectStatusDTOList.add(ProjectStatusDTO.AWAITING);

        resourceManagerSearchProjectDTO = new ResourceManagerSearchProjectDTO(projectClassDTOList, projectStatusDTOList, 1L, "ProjectName");
    }


    @Test
    void convertEntityToDto() {
        ResourceManagerSearchProjectDTO result = resourceManagerSearchProjectsMapper.convertEntityToDto(resourceManagerSearchProject);
        assertEquals(result.getProjectId(), resourceManagerSearchProjectDTO.getProjectId());
        assertEquals(result.getProjectName(), resourceManagerSearchProjectDTO.getProjectName());
        assertEquals(result.getProjectClassDTOList(), resourceManagerSearchProjectDTO.getProjectClassDTOList());
        assertEquals(result.getProjectStatusDTOList(), resourceManagerSearchProjectDTO.getProjectStatusDTOList());
    }

    @Test
    void convertDtoToEntity() {
    }
}