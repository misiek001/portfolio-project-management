package com.misiek.controller;

import com.misiek.domain.BusinessUnit;
import com.misiek.mapping.DTOtoEntityMapper;
import com.misiek.mapping.EntityToDTOMapper;
import com.misiek.service.BusinessUnitService;
import com.misiek.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/businessunits")
public class BusinessUnitController<T> extends RawController<BusinessUnit> {

    private BusinessUnitService businessUnitService;

    @Autowired
    public BusinessUnitController(DTOtoEntityMapper dtOtoEntityMapper, EntityToDTOMapper entityToDTOMapper, BusinessUnitService businessUnitService) {
        super(dtOtoEntityMapper, entityToDTOMapper);
        this.businessUnitService = businessUnitService;
    }

    @Override
    public IService getService() {
        return businessUnitService;
    }
}
