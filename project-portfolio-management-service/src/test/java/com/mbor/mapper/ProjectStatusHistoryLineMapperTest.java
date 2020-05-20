package com.mbor.mapper;

import com.mbor.domain.ProjectStatus;
import com.mbor.domain.ProjectStatusHistoryLine;
import com.mbor.model.ProjectStatusDTO;
import com.mbor.model.ProjectStatusHistoryLineDTO;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
class ProjectStatusHistoryLineMapperTest {

    private static ProjectStatusHistoryLine projectStatusHistoryLine;

    private static ProjectStatusHistoryLineDTO projectStatusHistoryLineDTO;

    private static final Long LINE_ID = 1l;
    private static final String LINE_DESCRIPTION  = "Some Description";
    private static final LocalDateTime CREATION_TIME = LocalDateTime.now();

    private static final ProjectStatus PREVIOUS_STATUS = ProjectStatus.ANALYSIS;
    private static final ProjectStatus CURRENT_STATUS = ProjectStatus.AWAITING;
    private static final ProjectStatusDTO DTO_PREVIOUS_STATUS = ProjectStatusDTO.ANALYSIS;
    private static final ProjectStatusDTO DTO_CURRENT_STATUS = ProjectStatusDTO.AWAITING;

    @Autowired
    ProjectStatusHistoryLineMapper projectStatusHistoryLineMapper;

    @BeforeAll
    public static void init(){
        prepareProjectStatusHistoryLine();
        prepareProjectStatusHistoryLineDTO();
    }

    @Test
    void convertToDto() {
        ProjectStatusHistoryLineDTO result = projectStatusHistoryLineMapper.convertToDto(projectStatusHistoryLine);

        assertEquals(result.getId(), LINE_ID);
        assertEquals(result.getPreviousStatus(), DTO_PREVIOUS_STATUS);
        assertEquals(result.getCurrentStatus(), DTO_CURRENT_STATUS);
        assertEquals(result.getDescription(), LINE_DESCRIPTION);
        assertEquals(result.getCreationTime(), CREATION_TIME);
    }

    @Test
    void convertToEntity() {
        ProjectStatusHistoryLine result = projectStatusHistoryLineMapper.convertToEntity(projectStatusHistoryLineDTO);

        assertEquals(result.getId(), LINE_ID);
        assertEquals(result.getPreviousStatus(), PREVIOUS_STATUS);
        assertEquals(result.getCurrentStatus(), CURRENT_STATUS);
        assertEquals(result.getDescription(), LINE_DESCRIPTION);
        assertEquals(result.getCreationTime(), CREATION_TIME);
        assertNull(result.getProject());

    }

    private static void prepareProjectStatusHistoryLine() {
     projectStatusHistoryLine = new ProjectStatusHistoryLine();

     projectStatusHistoryLine.setId(LINE_ID);
     projectStatusHistoryLine.setPreviousStatus(PREVIOUS_STATUS);
     projectStatusHistoryLine.setCurrentStatus(CURRENT_STATUS);
     projectStatusHistoryLine.setDescription(LINE_DESCRIPTION);
     projectStatusHistoryLine.setCreationTime(CREATION_TIME);

    }

    private static void prepareProjectStatusHistoryLineDTO() {
        projectStatusHistoryLineDTO = new ProjectStatusHistoryLineDTO();

        projectStatusHistoryLineDTO.setId(LINE_ID);
        projectStatusHistoryLineDTO.setPreviousStatus(DTO_PREVIOUS_STATUS);
        projectStatusHistoryLineDTO.setCurrentStatus(DTO_CURRENT_STATUS);
        projectStatusHistoryLineDTO.setDescription(LINE_DESCRIPTION);
        projectStatusHistoryLineDTO.setCreationTime(CREATION_TIME);
    }
}