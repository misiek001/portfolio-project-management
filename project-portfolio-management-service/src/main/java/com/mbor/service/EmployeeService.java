package com.mbor.service;

import com.mbor.dao.EmployeeDao;
import com.mbor.dao.IDao;
import com.mbor.domain.Employee;
import com.mbor.mapping.BusinessEmployeeMapper;
import com.mbor.mapping.BusinessRelationManagerMapper;
import com.mbor.mapping.EmployeeMapper;
import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;
import com.mbor.model.creation.EmployeeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class EmployeeService extends RawService<Employee> implements IEmployeeService<Employee> {

    private ApplicationContext context;

    private EmployeeDao employeeDao;

    private Map<EmployeeType, EmployeeMapper> mappers = new HashMap();

    private Random random = new Random();

    @Autowired
    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public IDao getDao() {
        return employeeDao;
    }

    @Override
    public EmployeeCreatedDTO save(EmployeeCreationDTO employeeCreationDTO) {
        EmployeeType employeeType = employeeCreationDTO.getEmployeeType();
        Employee employee = prepareEmployee(employeeCreationDTO, employeeType);
        setEmployeeUserName(employee);
        Employee returnedEmployee = super.saveInternal(employee);
        return prepareEmployeeCreatedDto(returnedEmployee, employeeType);
    }

    private Employee prepareEmployee(EmployeeCreationDTO employeeCreationDTO, EmployeeType employeeType){
        return mappers.get(employeeType).convertCreationDtoToEntity(employeeCreationDTO);
    }

    private EmployeeCreatedDTO prepareEmployeeCreatedDto(Employee employee, EmployeeType employeeType){
        return (EmployeeCreatedDTO) mappers.get(employeeType).convertEntityToCreatedDto(employee);
    }

    private void setEmployeeUserName(Employee employee){
        String userName = employee.getFirstName().concat(employee.getLastName()).concat(String.valueOf(random.nextLong()));
        employee.setUserName(userName);
    }

    @Autowired
    public void context(ApplicationContext context) {
        this.context = context; }

    @PostConstruct
    public void initMappers(){
        mappers.put(EmployeeType.BusinessEmployee, context.getBean("businessEmployeeMapper", BusinessEmployeeMapper.class));
        mappers.put(EmployeeType.BusinessRelationManager, context.getBean("businessRelationManagerMapper", BusinessRelationManagerMapper.class));
    }


}
