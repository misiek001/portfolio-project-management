package com.mbor.model.projectworkflow;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class OpenProjectDTO {

    @NotNull
    private Long resourceManagerId;

    @NotNull
    private Long projectManagerId;

    @NotEmpty
    private Set<Long> solutionArchitects = new HashSet<>();

    public Long getResourceManagerId() {
        return resourceManagerId;
    }

    public void setResourceManagerId(Long resourceManagerId) {
        this.resourceManagerId = resourceManagerId;
    }

    public Long getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(Long projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public Set<Long> getSolutionArchitects() {
        return solutionArchitects;
    }

    public void setSolutionArchitects(Set<Long> solutionArchitects) {
        this.solutionArchitects = solutionArchitects;
    }

}
