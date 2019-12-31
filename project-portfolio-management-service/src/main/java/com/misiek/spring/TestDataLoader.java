package com.misiek.spring;

import com.misiek.dao.BusinessUnitDao;
import com.misiek.dao.EmployeeDao;
import com.misiek.dao.ProjectDao;
import com.misiek.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class TestDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final BusinessUnitDao businessUnitDao;

    private final EmployeeDao employeeDao;

    private final ProjectDao projectDao;

    @Autowired
    public TestDataLoader(BusinessUnitDao businessUnitDao, EmployeeDao employeeDao, ProjectDao projectDao) {
        this.businessUnitDao = businessUnitDao;
        this.employeeDao = employeeDao;
        this.projectDao = projectDao;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        BusinessUnit operationBusinessUnit = new BusinessUnit();

        operationBusinessUnit.setName("Operation Business Unit");
        Long operationBusinessUnitIt =  businessUnitDao.save(operationBusinessUnit).get().getId();

        BusinessUnit ITBusinessUnit = new BusinessUnit();
        ITBusinessUnit.setName("IT Business Unit");
        Long ITBusinessUnitId =  businessUnitDao.save(ITBusinessUnit).get().getId();

        Director ITDirector = new Director();
        ITDirector.setFirstName("Director First Name");
        ITDirector.setLastName("Director Last  Name");
        ITDirector.setBusinessUnit(businessUnitDao.find(operationBusinessUnitIt).get());

        Long directorID =  employeeDao.save(ITDirector).get().getId();

        Supervisor ITSupervisor = new Supervisor();
        ITSupervisor.setFirstName("Supervisor First Name");
        ITSupervisor.setLastName("Supervisor Last Name");
        ITSupervisor.setBusinessUnit(businessUnitDao.find(ITBusinessUnitId).get());
        ITSupervisor.setDirector((Director) employeeDao.find(directorID).get());

       Long supervisorId =  employeeDao.save(ITSupervisor).get().getId();

        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setFirstName("BRM First Name");
        businessRelationManager.setLastName("BRM Last Name");
        businessRelationManager.setBusinessUnit(businessUnitDao.find(ITBusinessUnitId).get());
        businessRelationManager.setDirector((Director) employeeDao.find(directorID).get());

        Long brmId =  employeeDao.save(businessRelationManager).get().getId();

        Consultant ITConsultant = new Consultant();
        ITConsultant.setFirstName("Consultant First Name");
        ITConsultant.setLastName("Consultant Last Name");
        ITConsultant.setSupervisor((Supervisor) employeeDao.find(supervisorId).get());
        ITConsultant.setBusinessUnit(businessUnitDao.find(ITBusinessUnitId).get());

        Long consultantID = employeeDao.save(ITConsultant).get().getId();

        BusinessEmployee businessEmployee = new BusinessEmployee();
        businessEmployee.setFirstName("BE First Name");
        businessEmployee.setLastName("BE Last Name");
        businessEmployee.setBusinessUnit(businessUnitDao.find(operationBusinessUnitIt).get());

        Long businessEmployeeId = employeeDao.save(businessEmployee).get().getId();
    }
}
