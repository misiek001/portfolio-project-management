package com.mbor.service;

import com.mbor.model.EmployeeDTO;
import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;

public interface IEmployeeService extends IInternalEmployeeService, IAPIService<EmployeeCreatedDTO, EmployeeCreationDTO, EmployeeDTO> {

    EmployeeCreatedDTO save(EmployeeCreationDTO employeeCreationDTO);

    <T> Long getDemandedProjectRoleId(Class<T> t, String username);

    <T> Long getDemandedEmployeeId(Class<T> t, String username);
}
