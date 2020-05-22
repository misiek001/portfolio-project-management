package com.mbor.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.model.creation.DemandSheetCreatedDTO;
import com.mbor.model.creation.DemandSheetCreationDTO;
import com.mbor.model.views.Views;
import com.mbor.service.IDemandSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demandsheets")
public class DemandSheetController extends RawController {

    private final IDemandSheetService demandSheetService;

    @Autowired
    public DemandSheetController(IDemandSheetService demandSheetService) {
        this.demandSheetService = demandSheetService;
    }

    @PostMapping
    @JsonView(Views.Public.class)
    @PreAuthorize("hasAuthority('create_demandsheet')")
    public ResponseEntity<DemandSheetCreatedDTO> createDemandSheet(@RequestBody DemandSheetCreationDTO demandSheetCreationDTO){
        DemandSheetCreatedDTO demandSheetCreatedDTO  =  getService().save(demandSheetCreationDTO);
        return new ResponseEntity<>(demandSheetCreatedDTO, HttpStatus.CREATED);
    }

    @Override
    public IDemandSheetService getService() {
        return demandSheetService;
    }
}
