package com.misiek.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project {

    private Long Id;

    private String name;

    private Employee projectManager;

    private Employee solutionArchitect;

    private Employee resourceManager;

    private String status;

    private LocalDateTime startDate;

    private LocalDateTime plannedEndDate;

    private LocalDateTime realEndDate;

    private Employee businessUnitLeader;

    private BusinessUnit businessUnit;

}
