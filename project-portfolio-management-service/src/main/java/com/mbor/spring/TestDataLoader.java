package com.mbor.spring;

import com.mbor.dao.IBusinessUnitDao;
import com.mbor.dao.IEmployeeDao;
import com.mbor.dao.IProjectDao;
import com.mbor.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TestDataLoader {

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


    @PostConstruct
    public void onApplicationEvent() {

        try(Session session = sessionFactory.openSession();){
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
            ITDirector.setUserName("DirectorUserName");
            ITDirector.setBusinessUnit(businessUnitDao.find(operationBusinessUnitId).get());

            Long directorID =  employeeDao.save(ITDirector).get().getId();

            transaction.commit();
            transaction = session.getTransaction();
            transaction.begin();

            Supervisor ITSupervisor = new Supervisor();
            ITSupervisor.setUserName("ITSupervisorUserName");
            ITSupervisor.setBusinessUnit(businessUnitDao.find(ITBusinessUnitId).get());
            ITSupervisor.setDirector((Director) employeeDao.find(directorID).get());

            Long supervisorId =  employeeDao.save(ITSupervisor).get().getId();

            transaction.commit();
            transaction = session.getTransaction();
            transaction.begin();

            BusinessRelationManager businessRelationManager = new BusinessRelationManager();
            businessRelationManager.setUserName("BRMrUserName");
            businessRelationManager.setBusinessUnit(businessUnitDao.find(ITBusinessUnitId).get());
            businessRelationManager.setDirector((Director) employeeDao.find(directorID).get());

            Long brmId =  employeeDao.save(businessRelationManager).get().getId();

            transaction.commit();
            transaction = session.getTransaction();
            transaction.begin();

            Consultant ITConsultant = new Consultant();
            ITConsultant.setUserName("ConsultantUserName");
            ITConsultant.setSupervisor((Supervisor) employeeDao.find(supervisorId).get());
            ITConsultant.setBusinessUnit(businessUnitDao.find(ITBusinessUnitId).get());

            Long consultantID = employeeDao.save(ITConsultant).get().getId();

            transaction.commit();
            transaction = session.getTransaction();
            transaction.begin();

            BusinessEmployee businessEmployee = new BusinessEmployee();
            businessEmployee.setUserName("BusinessEmployeeUserName");
            businessEmployee.setBusinessUnit(businessUnitDao.find(operationBusinessUnitId).get());

            Long businessEmployeeId = employeeDao.save(businessEmployee).get().getId();

            transaction.commit();

            employeeDao.find(businessEmployeeId);
        }


    }

}
