package com.mbor.validation;

import com.mbor.model.creation.EmployeeCreationDTO;
import com.mbor.model.creation.EmployeeType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.EnumSet;

public class EmployeeAssignmentValidator implements ConstraintValidator<EmployeeAssignment, EmployeeCreationDTO> {


    @Override
    public void initialize(EmployeeAssignment constraintAnnotation) {

    }

    @Override
    public boolean isValid(EmployeeCreationDTO value, ConstraintValidatorContext context) {
        Long directorId = null;
        if(value.getDirectorId() != null){
            directorId = value.getDirectorId();
        }
        Long supervisorId = null;
        if(value.getSupervisorId() != null){
            supervisorId = value.getSupervisorId();
        }
        EmployeeType employeeType = value.getEmployeeType();
        if(employeeType == EmployeeType.Director && (directorId != null || supervisorId !=null)){
            return false;
        }
        if(employeeType == EmployeeType.Supervisor && (directorId == null || supervisorId != null)){
            return false;
        }
        EnumSet enumSet = EnumSet.of(EmployeeType.BusinessEmployee,EmployeeType.Consultant, EmployeeType.BusinessRelationManager);
        if(enumSet.contains(employeeType) && (directorId != null || supervisorId == null)){
            return false;
        }
        return true;
    }
}
