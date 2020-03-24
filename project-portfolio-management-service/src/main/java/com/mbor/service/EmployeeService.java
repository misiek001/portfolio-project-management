package com.mbor.service;

import com.mbor.dao.IDao;
import com.mbor.dao.IEmployeeDao;
import com.mbor.dao.IUserDao;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.Employee;
import com.mbor.domain.security.Privilege;
import com.mbor.domain.security.Role;
import com.mbor.domain.security.User;
import com.mbor.exception.WrongEmployeeTypeException;
import com.mbor.mapper.*;
import com.mbor.model.EmployeeDTO;
import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;
import com.mbor.model.creation.EmployeeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Transactional
public class EmployeeService extends RawService<Employee> implements IEmployeeService {

    private ApplicationContext context;

    private final IEmployeeDao employeeDao;

    private final CustomUserDetailsService customUserDetailsService;

    private Map<EmployeeType, EmployeeMapper> mappers = new HashMap();

    private Random random = new Random();

    private final IUserDao userDao;

    public EmployeeService(IEmployeeDao employeeDao, CustomUserDetailsService customUserDetailsService, IUserDao userDao) {
        this.employeeDao = employeeDao;
        this.customUserDetailsService = customUserDetailsService;
        this.userDao = userDao;
    }

    @Override
    public IDao getDao() {
        return employeeDao;
    }

    //TODO implement mapping for differentType of Employee
    @Override
    public List<EmployeeDTO> findAll() {
        return null;
    }

    //TODO implement mapping for differentType of Employee
    @Override
    public EmployeeDTO find(Long id) {
        return null;
    }

    @Override
    public EmployeeCreatedDTO save(EmployeeCreationDTO employeeCreationDTO) {
        EmployeeType employeeType = employeeCreationDTO.getEmployeeType();
        Employee employee = prepareEmployee(employeeCreationDTO, employeeType);
        setEmployeeUserName(employee);
        Employee returnedEmployee = super.saveInternal(employee);
        createUser(employee);
        return prepareEmployeeCreatedDto(returnedEmployee, employeeType);
    }

    @Override
    public void delete(Long id) {
        deleteInternal(id);
    }

    @Override
    public <T> Long getDemandedProjectRoleId(Class<T> t, String username){
        User user = customUserDetailsService.loadUserByUserName(username);
        Employee employee = user.getEmployee();
        return  employee.getProjectRoleSet().stream()
                .filter(t::isInstance)
                .findFirst().orElseThrow(WrongEmployeeTypeException::new).getId();
    }

    @Override
    public <T> Long getDemandedEmployeeId(Class<T> t, String username){
        User user = customUserDetailsService.loadUserByUserName(username);
        return user.getEmployee().getId();
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

    //Todo Extend Functionality
    private void createUser(Employee employee){
        User user = new User();
        if (employee instanceof BusinessRelationManager){
            Role brmRole = new Role();
            brmRole.setName("BRM");
            Privilege createProjectPrivilege = new Privilege();
            createProjectPrivilege.setName("create_project");
            brmRole.addPrivilege(createProjectPrivilege);
            user.getRoles().add(brmRole);
        }
        user.setPassword("pass");
        employee.setUser(user);
    }

    @Autowired
    public void context(ApplicationContext context) {
        this.context = context; }

    @PostConstruct
    public void initMappers(){
        mappers.put(EmployeeType.BusinessEmployee, context.getBean("businessEmployeeMapper", BusinessEmployeeMapper.class));
        mappers.put(EmployeeType.BusinessRelationManager, context.getBean("businessRelationManagerMapper", BusinessRelationManagerMapper.class));
        mappers.put(EmployeeType.Supervisor, context.getBean("supervisorMapper", SupervisorMapper.class));
        mappers.put(EmployeeType.Director, context.getBean("directorMapper", DirectorMapper.class));
    }


}
