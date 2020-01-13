package com.mbor.controller;

import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;
import com.mbor.service.IEmployeeService;
import com.mbor.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController extends RawController {

    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeController(IEmployeeService iEmployeeService) {
        this.employeeService = iEmployeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeCreatedDTO> save(@RequestBody EmployeeCreationDTO employeeCreationDTO){
       EmployeeCreatedDTO employeeCreatedDTO  =  employeeService.save(employeeCreationDTO);
       return new ResponseEntity<>(employeeCreatedDTO, HttpStatus.OK);
    }

    @Override
    public IService getService() {
        return employeeService;
    }

}
