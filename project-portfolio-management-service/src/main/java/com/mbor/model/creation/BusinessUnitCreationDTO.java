package com.mbor.model.creation;

import javax.validation.constraints.NotBlank;

public class BusinessUnitCreationDTO {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
