package com.mbor.mapper;

import com.mbor.domain.RealEndDate;
import com.mbor.model.RealEndDateDTO;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
class RealEndDateMapperTest {

    @Autowired
    RealEndDateMapper realEndDateMapper;

    private static RealEndDate expectedEndDate;

    private static RealEndDateDTO expectedEndDateDTO;

    @BeforeAll
    public static void init(){
        expectedEndDate = new RealEndDate();
        expectedEndDate.setEndDate(LocalDateTime.now().plusDays(10));
        expectedEndDate.setReason("Some reason");

        expectedEndDateDTO = new RealEndDateDTO();
        expectedEndDateDTO.setEndDate(expectedEndDate.getEndDate());
        expectedEndDateDTO.setReason(expectedEndDate.getReason());
    }

    @Test
    void convertToDto() {
        RealEndDateDTO result = realEndDateMapper.convertToDto(expectedEndDate);
        assertEquals(result.getEndDate(), expectedEndDateDTO.getEndDate());
        assertEquals(result.getReason(), expectedEndDateDTO.getReason());
    }

    @Test
    void convertToEntity() {
        RealEndDate result = realEndDateMapper.convertToEntity(expectedEndDateDTO);
        assertEquals(result.getEndDate(), expectedEndDate.getEndDate());
        assertEquals(result.getReason(), expectedEndDateDTO.getReason());
    }
}