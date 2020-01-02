package com.misiek.spring;

import com.misiek.dao.IBusinessUnitDao;
import com.misiek.dao.IEmployeeDao;
import com.misiek.dao.IProjectDao;
import com.misiek.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class TestDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final SessionFactory sessionFactory;

    private final IBusinessUnitDao businessUnitDao;

    private final IEmployeeDao employeeDao;

    private final IProjectDao projectDao;

    public TestDataLoader(SessionFactory sessionFactory, IBusinessUnitDao businessUnitDao, IEmployeeDao employeeDao, IProjectDao projectDao) {
        this.sessionFactory = sessionFactory;
        this.businessUnitDao = businessUnitDao;
        this.employeeDao = employeeDao;
        this.projectDao = projectDao;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        BusinessUnit operationBusinessUnit = new BusinessUnit();

        operationBusinessUnit.setName("Operation Business Unit");
        Long operationBusinessUnitId =  businessUnitDao.save(operationBusinessUnit).get().getId();

        transaction.commit();
        transaction = session.getTransaction();
        transaction.begin();

        BusinessUnit ITBusinessUnit = new BusinessUnit();
        ITBusinessUnit.setName("IT Business Unit");
        Long ITBusinessUnitId =  businessUnitDao.save(ITBusinessUnit).get().getId();

        transaction.commit();
        transaction = session.getTransaction();
        transaction.begin();

        Director ITDirector = new Director();
        ITDirector.setFirstName("Director First Name");
        ITDirector.setLastName("Director Last  Name");
        ITDirector.setBusinessUnit(businessUnitDao.find(operationBusinessUnitId).get());

        Long directorID =  employeeDao.save(ITDirector).get().getId();

        transaction.commit();
        transaction = session.getTransaction();
        transaction.begin();

        Supervisor ITSupervisor = new Supervisor();
        ITSupervisor.setFirstName("Supervisor First Name");
        ITSupervisor.setLastName("Supervisor Last Name");
        ITSupervisor.setBusinessUnit(businessUnitDao.find(ITBusinessUnitId).get());
        ITSupervisor.setDirector((Director) employeeDao.find(directorID).get());

       Long supervisorId =  employeeDao.save(ITSupervisor).get().getId();

        transaction.commit();
        transaction = session.getTransaction();
        transaction.begin();

        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setFirstName("BRM First Name");
        businessRelationManager.setLastName("BRM Last Name");
        businessRelationManager.setBusinessUnit(businessUnitDao.find(ITBusinessUnitId).get());
        businessRelationManager.setDirector((Director) employeeDao.find(directorID).get());

        Long brmId =  employeeDao.save(businessRelationManager).get().getId();

        transaction.commit();
        transaction = session.getTransaction();
        transaction.begin();

        Consultant ITConsultant = new Consultant();
        ITConsultant.setFirstName("Consultant First Name");
        ITConsultant.setLastName("Consultant Last Name");
        ITConsultant.setSupervisor((Supervisor) employeeDao.find(supervisorId).get());
        ITConsultant.setBusinessUnit(businessUnitDao.find(ITBusinessUnitId).get());

        Long consultantID = employeeDao.save(ITConsultant).get().getId();

        transaction.commit();
        transaction = session.getTransaction();
        transaction.begin();

        BusinessEmployee businessEmployee = new BusinessEmployee();
        businessEmployee.setFirstName("BE First Name");
        businessEmployee.setLastName("BE Last Name");
        businessEmployee.setBusinessUnit(businessUnitDao.find(operationBusinessUnitId).get());

        Long businessEmployeeId = employeeDao.save(businessEmployee).get().getId();

        transaction.commit();



    }

}
