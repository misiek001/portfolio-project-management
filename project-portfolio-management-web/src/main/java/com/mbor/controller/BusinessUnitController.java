package com.mbor.controller;

import com.mbor.model.creation.BusinessUnitCreatedDTO;
import com.mbor.model.creation.BusinessUnitCreationDTO;
import com.mbor.service.IBusinessUnitService;
import com.mbor.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<BusinessUnitCreatedDTO> save(@RequestBody BusinessUnitCreationDTO businessUnitCreationDTO){
       BusinessUnitCreatedDTO businessUnitCreatedDTO =  businessUnitService.save(businessUnitCreationDTO);
       return new ResponseEntity<>(businessUnitCreatedDTO, HttpStatus.CREATED);
    }

    @Override
    public IService getService() {
        return businessUnitService;
    }



}


