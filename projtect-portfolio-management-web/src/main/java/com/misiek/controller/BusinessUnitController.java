package com.misiek.controller;

import com.misiek.domain.BusinessUnit;
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
    public BusinessUnitController(BusinessUnitService businessUnitService) {
        this.businessUnitService = businessUnitService;
    }

    @Override
    public IService getService() {
        return businessUnitService;
    }
}
