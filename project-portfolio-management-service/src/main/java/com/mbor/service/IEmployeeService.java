package com.mbor.service;

import com.mbor.domain.Employee;
import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;

public interface IEmployeeService<T extends Employee> extends IService<T> {

   EmployeeCreatedDTO save(EmployeeCreationDTO employeeCreationDTO);

}
