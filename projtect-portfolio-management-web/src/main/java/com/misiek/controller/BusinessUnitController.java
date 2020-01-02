package com.misiek.controller;

import com.misiek.domain.BusinessUnit;
import com.misiek.mapping.Mapper;
import com.misiek.service.IBusinessUnitService;
import com.misiek.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/businessunits")
public class BusinessUnitController extends RawController<BusinessUnit> {

    private final IBusinessUnitService businessUnitService;

    @Autowired
    public BusinessUnitController(IBusinessUnitService businessUnitService) {
        this.businessUnitService = businessUnitService;
    }

    @Override
    public IService getService() {
        return businessUnitService;
    }

    @Override
    public Mapper getMapper() {
        return null;
    }


}


