package com.mbor.model.projectaspect;

public class ProjectAspectDTO {

    protected String name;

    protected String description;

    protected AspectStatusDTO aspectStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AspectStatusDTO getAspectStatus() {
        return aspectStatus;
    }

    public String getAspectStatusName(){
        return aspectStatus.name();
    }

    public void setAspectStatus(AspectStatusDTO aspectStatus) {
        this.aspectStatus = aspectStatus;
    }
}
