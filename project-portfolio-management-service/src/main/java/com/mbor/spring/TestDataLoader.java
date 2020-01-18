package com.mbor.spring;

import com.mbor.dao.IBusinessUnitDao;
import com.mbor.dao.IEmployeeDao;
import com.mbor.dao.IProjectDao;
import com.mbor.domain.*;
import com.mbor.service.IBusinessUnitService;
import com.mbor.service.IEmployeeService;
import com.mbor.service.IProjectService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Profile("dev")
public class TestDataLoader {

    private final SessionFactory sessionFactory;

    private final IBusinessUnitDao businessUnitDao;

    private final IEmployeeDao employeeDao;

    private final IProjectDao projectDao;

    private final IBusinessUnitService businessUnitService;

    private final IEmployeeService employeeService;

    private final IProjectService projectService;

    public TestDataLoader(SessionFactory sessionFactory, IBusinessUnitDao businessUnitDao, IEmployeeDao employeeDao, IProjectDao projectDao, IBusinessUnitService businessUnitService, IEmployeeService employeeService, IProjectService projectService) {
        this.sessionFactory = sessionFactory;
        this.businessUnitDao = businessUnitDao;
        this.employeeDao = employeeDao;
        this.projectDao = projectDao;
        this.businessUnitService = businessUnitService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @PostConstruct
    public void onApplicationEvent() {

        BusinessUnit operationBusinessUnit = new BusinessUnit();

        operationBusinessUnit.setName("Operation Business Unit");
        operationBusinessUnit = (BusinessUnit) businessUnitService.saveInternal(operationBusinessUnit);
        Long operationBusinessUnitId = operationBusinessUnit.getId();

        BusinessUnit ITBusinessUnit = new BusinessUnit();
        ITBusinessUnit.setName("IT Business Unit");
        ITBusinessUnit = (BusinessUnit) businessUnitService.saveInternal(ITBusinessUnit);
        Long ITBusinessUnitId = ITBusinessUnit.getId();

        Director ITDirector = new Director();
        ITDirector.setUserName("DirectorUserName");
        ITDirector.setBusinessUnit((BusinessUnit) businessUnitService.find(operationBusinessUnitId));

        ITDirector = (Director) employeeService.saveInternal(ITDirector);
        Long directorID = ITDirector.getId();

        Supervisor ITSupervisor = new Supervisor();
        ITSupervisor.setUserName("ITSupervisorUserName");
        ITSupervisor.setBusinessUnit((BusinessUnit) businessUnitService.find((ITBusinessUnitId)));
        ITSupervisor.setDirector((Director) employeeService.find(directorID));

        ITSupervisor = (Supervisor) employeeService.saveInternal(ITSupervisor);
        Long supervisorId = ITSupervisor.getId();

        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setUserName("BRMrUserName");
        businessRelationManager.setBusinessUnit((BusinessUnit) businessUnitService.find(ITBusinessUnitId));
        businessRelationManager.setDirector((Director) employeeService.find(directorID));

        businessRelationManager = (BusinessRelationManager) employeeService.saveInternal(businessRelationManager);
        Long brmId = businessRelationManager.getId();

        Consultant ITConsultant = new Consultant();
        ITConsultant.setUserName("ConsultantUserName");
        ITConsultant.setSupervisor((Supervisor) employeeService.find(supervisorId));
        ITConsultant.setBusinessUnit((BusinessUnit) businessUnitService.find(ITBusinessUnitId));

        ITConsultant = (Consultant) employeeService.saveInternal(ITConsultant);
        Long consultantID = ITConsultant.getId();

        BusinessEmployee businessEmployee = new BusinessEmployee();
        businessEmployee.setUserName("BusinessEmployeeUserName");
        businessEmployee.setBusinessUnit((BusinessUnit) businessUnitService.find(operationBusinessUnitId));

        businessEmployee = (BusinessEmployee) employeeService.saveInternal(businessEmployee);
        Long businessEmployeeId = businessEmployee.getId();


        Project project = new Project();
        project.setProjectName("First Project Name");
        operationBusinessUnit = (BusinessUnit) businessUnitService.find(operationBusinessUnitId);
        project.addBusinessUnit(operationBusinessUnit);
        project.setProjectClass(ProjectClass.I);
        projectService.saveInternal(project);


        project = new Project();
        project.setProjectName("Second Project Name");
        project.addBusinessUnit((BusinessUnit) businessUnitService.find(ITBusinessUnitId));
        project.setProjectClass(ProjectClass.II);
        projectService.saveInternal(project);

        ITBusinessUnit = (BusinessUnit) businessUnitService.find(ITBusinessUnitId);
        operationBusinessUnit = (BusinessUnit) businessUnitService.find(operationBusinessUnitId);
        System.out.println(ITBusinessUnitId);
    }
}
