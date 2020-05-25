package com.mbor.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.model.DemandSheetDTO;
import com.mbor.model.creation.DemandSheetCreatedDTO;
import com.mbor.model.creation.DemandSheetCreationDTO;
import com.mbor.model.views.Views;
import com.mbor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demandsheets")
public class DemandSheetController extends RawController {

    private final IAPIDemandSheetService demandSheetService;
    private final IAPIEmployeeService employeeService;

    @Autowired
    public DemandSheetController(IAPIDemandSheetService demandSheetService, IAPIEmployeeService employeeService) {
        this.demandSheetService = demandSheetService;
        this.employeeService = employeeService;
    }

    @PostMapping
    @JsonView(Views.Public.class)
    @PreAuthorize("hasAuthority('create_demandsheet')")
    public ResponseEntity<DemandSheetCreatedDTO> createDemandSheet(@RequestBody DemandSheetCreationDTO demandSheetCreationDTO){
        DemandSheetCreatedDTO demandSheetCreatedDTO  =  getService().save(demandSheetCreationDTO);
        return new ResponseEntity<>(demandSheetCreatedDTO, HttpStatus.CREATED);
    }

    @GetMapping(params = "searchingDemandSheets=ofBrmNoProject")
    @JsonView(Views.Public.class)
    @PreAuthorize("hasAuthority('get_all_demandsheets_of_brm_with_no_projects')")
    public ResponseEntity<List<DemandSheetDTO>> createDemandSheet(final Authentication authentication){
        String principal = (String) authentication.getPrincipal();
        Long brmId = employeeService.getDemandedEmployeeId(BusinessRelationManager.class, principal);
        return new ResponseEntity(getService().getAllDemandSheetsOfBRMWithNoProject(brmId), HttpStatus.OK);
    }

    @Override
    public IAPIDemandSheetService getService() {
        return demandSheetService;
    }
}
