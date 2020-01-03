package com.misiek.controller;

import com.misiek.service.IBusinessUnitService;
import com.misiek.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/businessunits")
public class BusinessUnitController extends RawController {

    private final IBusinessUnitService businessUnitService;

    @Autowired
    public BusinessUnitController(IBusinessUnitService businessUnitService) {
        this.businessUnitService = businessUnitService;
    }

    @Override
    public IService getService() {
        return businessUnitService;
    }



}


