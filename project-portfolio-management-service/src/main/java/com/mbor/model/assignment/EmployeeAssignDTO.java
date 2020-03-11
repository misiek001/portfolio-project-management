package com.mbor.model.assignment;

import com.mbor.model.*;

import javax.validation.constraints.Positive;
import java.util.Set;

public class EmployeeAssignDTO {

    @Positive
    private Long projectId;

    private ProjectManagerDTO projectManagerDTO;

    private BusinessRelationManagerDTO businessRelationManagerDTO;

    private ResourceManagerDTO resourceManagerDTO;

    private Set<SolutionArchitectDTO> solutionArchitectDTOS;

    private BusinessLeaderDTO businessLeaderDTO;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public ProjectManagerDTO getProjectManagerDTO() {
        return projectManagerDTO;
    }

    public void setProjectManagerDTO(ProjectManagerDTO projectManagerDTO) {
        this.projectManagerDTO = projectManagerDTO;
    }

    public BusinessRelationManagerDTO getBusinessRelationManagerDTO() {
        return businessRelationManagerDTO;
    }

    public void setBusinessRelationManagerDTO(BusinessRelationManagerDTO businessRelationManagerDTO) {
        this.businessRelationManagerDTO = businessRelationManagerDTO;
    }

    public ResourceManagerDTO getResourceManagerDTO() {
        return resourceManagerDTO;
    }

    public void setResourceManagerDTO(ResourceManagerDTO resourceManagerDTO) {
        this.resourceManagerDTO = resourceManagerDTO;
    }

    public Set<SolutionArchitectDTO> getSolutionArchitectDTOS() {
        return solutionArchitectDTOS;
    }

    public void setSolutionArchitectDTOS(Set<SolutionArchitectDTO> solutionArchitectDTOS) {
        this.solutionArchitectDTOS = solutionArchitectDTOS;
    }

    public BusinessLeaderDTO getBusinessLeaderDTO() {
        return businessLeaderDTO;
    }

    public void setBusinessLeaderDTO(BusinessLeaderDTO businessLeaderDTO) {
        this.businessLeaderDTO = businessLeaderDTO;
    }
}
