package com.mbor.mapper;

import com.mbor.domain.ProjectStatus;
import com.mbor.domain.ProjectStatusHistoryLine;
import com.mbor.model.ProjectStatusDTO;
import com.mbor.model.ProjectStatusHistoryLineDTO;

import java.time.LocalDateTime;

public class MapperUtils {

     static final Long LINE_ID = 1l;
     static final String LINE_DESCRIPTION = "Some Description";
     static final LocalDateTime CREATION_TIME = LocalDateTime.now();

     static final ProjectStatus PREVIOUS_STATUS = ProjectStatus.ANALYSIS;
     static final ProjectStatus CURRENT_STATUS = ProjectStatus.AWAITING;
     static final ProjectStatusDTO DTO_PREVIOUS_STATUS = ProjectStatusDTO.ANALYSIS;
     static final ProjectStatusDTO DTO_CURRENT_STATUS = ProjectStatusDTO.AWAITING;

    static ProjectStatusHistoryLine prepareProjectStatusHistoryLine() {
        ProjectStatusHistoryLine projectStatusHistoryLine = new ProjectStatusHistoryLine();

        projectStatusHistoryLine.setId(LINE_ID);
        projectStatusHistoryLine.setPreviousStatus(PREVIOUS_STATUS);
        projectStatusHistoryLine.setCurrentStatus(CURRENT_STATUS);
        projectStatusHistoryLine.setDescription(LINE_DESCRIPTION);
        projectStatusHistoryLine.setCreationTime(CREATION_TIME);

        return projectStatusHistoryLine;
    }

    static ProjectStatusHistoryLineDTO prepareProjectStatusHistoryLineDTO() {
        ProjectStatusHistoryLineDTO projectStatusHistoryLineDTO = new ProjectStatusHistoryLineDTO();

        projectStatusHistoryLineDTO.setId(LINE_ID);
        projectStatusHistoryLineDTO.setPreviousStatus(DTO_PREVIOUS_STATUS);
        projectStatusHistoryLineDTO.setCurrentStatus(DTO_CURRENT_STATUS);
        projectStatusHistoryLineDTO.setDescription(LINE_DESCRIPTION);
        projectStatusHistoryLineDTO.setCreationTime(CREATION_TIME);

        return projectStatusHistoryLineDTO;
    }
}
