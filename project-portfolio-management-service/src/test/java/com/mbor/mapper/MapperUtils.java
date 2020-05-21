package com.mbor.mapper;

import com.mbor.domain.ProjectClass;
import com.mbor.domain.ProjectStatus;
import com.mbor.domain.ProjectStatusHistoryLine;
import com.mbor.model.ProjectClassDTO;
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

     static final long PROJECT_ID = 1L;
     static final String PROJECT_NAME = "Project Name";
     static final ProjectClassDTO DTO_PROJECT_CLASS = ProjectClassDTO.I;
     static final ProjectClass PROJECT_CLASS = ProjectClass.I;
     static final long IT_BUSINESS_UNIT_ID = 1L;
     static final long FIRST_BUSINESS_UNIT_ID = 2L;
     static final long SECOND_BUSINESS_UNIT_ID = 3L;
     static final String IT_BUSINESS_UNIT_NAME = "IT Business Unit";
     static final String FIRST_BUSINESS_UNIT_NAME = "First Business Unit";
     static final String SECOND_BUSINESS_UNIT_NAME = "Second Business Unit";
     static final Long DIRECTOR_ID = 1L;
     static final Long BRM_ID = 2L;
     static final Long SUPERVISOR_ID = 3L;
     static final Long CONSULTANT_ID = 4L;
     static final Long BUSINESS_EMPLOYEE_ID = 5L;
     static final Long RESOURCE_MANAGER_ID = 1L;
     static final Long PROJECT_MANAGER_ID = 2L;
     static final Long BUSINESS_LEADER_ID = 3L;

     static final String DIRECTOR_USER_NAME = "Director Username";
     static final String BRM_USER_NAME = "BRM Username";
     static final String SUPERVISOR_USER_NAME = "Supervisor Username";
     static final String CONSULTANT_USER_NAME = "Consultant Username";
     static final String BUSINESS_EMPLOYEE_USERNAME = "BU Username";
     static final String FIRST_REASON = "First Reason";
     static final String SECOND_REASON = "Second Reason";

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
