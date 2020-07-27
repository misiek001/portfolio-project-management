package com.mbor.mapper;

import com.mbor.domain.ProjectStatusHistoryLine;
import com.mbor.mapper.project.ProjectStatusHistoryLineMapper;
import com.mbor.model.ProjectStatusHistoryLineDTO;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.mbor.mapper.MapperUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
class ProjectStatusHistoryLineMapperTest {


    private static ProjectStatusHistoryLine projectStatusHistoryLine;
    private static ProjectStatusHistoryLineDTO projectStatusHistoryLineDTO;
    @Autowired
    ProjectStatusHistoryLineMapper projectStatusHistoryLineMapper;

    @BeforeAll
    public static void init() {
        prepareProjectStatusHistoryLine();
        prepareProjectStatusHistoryLineDTO();
    }

    private static void prepareProjectStatusHistoryLine() {
        projectStatusHistoryLine = MapperUtils.prepareProjectStatusHistoryLine();
    }

    private static void prepareProjectStatusHistoryLineDTO() {
        projectStatusHistoryLineDTO = MapperUtils.prepareProjectStatusHistoryLineDTO();
    }

    @Test
    void convertToDto() {
        ProjectStatusHistoryLineDTO result = projectStatusHistoryLineMapper.convertEntityToDto(projectStatusHistoryLine);

        assertEquals(result.getId(), MapperUtils.LINE_ID);
        assertEquals(result.getPreviousStatus(), DTO_PREVIOUS_STATUS);
        assertEquals(result.getCurrentStatus(), DTO_CURRENT_STATUS);
        assertEquals(result.getDescription(), LINE_DESCRIPTION);
        assertEquals(result.getCreationTime(), CREATION_TIME);
    }

    @Test
    void convertToEntity() {
        ProjectStatusHistoryLine result = projectStatusHistoryLineMapper.convertDtoToEntity(projectStatusHistoryLineDTO);

        assertEquals(result.getId(), LINE_ID);
        assertEquals(result.getPreviousStatus(), PREVIOUS_STATUS);
        assertEquals(result.getCurrentStatus(), CURRENT_STATUS);
        assertEquals(result.getDescription(), LINE_DESCRIPTION);
        assertEquals(result.getCreationTime(), CREATION_TIME);
        assertNull(result.getProject());
    }
}