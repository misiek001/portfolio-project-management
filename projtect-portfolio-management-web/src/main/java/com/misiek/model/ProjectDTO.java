package com.misiek.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ProjectDTO {

    private Long Id;

    private String name;

    private ResourceManagerDTO resourceManager;

    private ProjectManagerDTO projectManager;

    private BusinessRelationManagerDTO businessRelationManager;

    private BusinessLeaderDTO businessUnitLeader;

    private Set<SolutionArchitectDTO> solutionArchitect;

    private ProjectStatusDTO status;

    private LocalDateTime startDate;

    private LocalDateTime plannedEndDate;

    private Set<RealEndDateDTO> realEndDateSet = new HashSet<>();

    private Set<BusinessUnitDTO> businessUnits;

}
