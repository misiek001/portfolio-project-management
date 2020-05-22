package com.mbor.model.creation;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

public class DemandSheetCreationDTO {

    @NotEmpty
    private String projectName;

    @NotEmpty
    private String description;

    @OneToOne
    private Long businessUnitId;

}
