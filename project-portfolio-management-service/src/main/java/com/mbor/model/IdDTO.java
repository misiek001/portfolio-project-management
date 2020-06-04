package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.model.views.Views;

public class IdDTO {

    @JsonView(Views.Public.class)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
