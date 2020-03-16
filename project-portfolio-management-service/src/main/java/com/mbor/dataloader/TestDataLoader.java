package com.mbor.dataloader;

import com.mbor.domain.*;
import com.mbor.domain.security.Privilege;
import com.mbor.domain.security.Role;
import com.mbor.domain.security.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;


public class TestDataLoader  {

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    private final PasswordEncoder passwordEncoder;

    public TestDataLoader(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void onApplicationEvent() {
        loadData();
    }

    public void loadData() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        BusinessUnit operationBusinessUnit = new BusinessUnit();

        operationBusinessUnit.setName("Operation Business Unit");
        entityManager.persist(operationBusinessUnit);
        Long operationBusinessUnitId = operationBusinessUnit.getId();

        BusinessUnit ITBusinessUnit = new BusinessUnit();
        ITBusinessUnit.setName("IT Business Unit");
        entityManager.persist(ITBusinessUnit);
        Long ITBusinessUnitId = ITBusinessUnit.getId();

        Director ITDirector = new Director();
        ITDirector.setUserName("DirectorUserName");
        ITDirector.setBusinessUnit(ITBusinessUnit);

        entityManager.persist(ITDirector);
        Long directorID = ITDirector.getId();

        Supervisor ITSupervisor = new Supervisor();
        ITSupervisor.setUserName("ITSupervisorUserName");
        ITSupervisor.setBusinessUnit(ITBusinessUnit);
        ITSupervisor.setDirector(ITDirector);
        createUser(ITSupervisor);

        entityManager.persist(ITSupervisor);
        Long supervisorId = ITSupervisor.getId();

        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setUserName("BRMUserName");
        businessRelationManager.setBusinessUnit(ITBusinessUnit);
        businessRelationManager.setDirector(ITDirector);
        createUser(businessRelationManager);

        entityManager.persist(businessRelationManager);
        Long brmId = businessRelationManager.getId();

        Consultant ITConsultant = new Consultant();
        ITConsultant.setUserName("ConsultantUserName");
        ITConsultant.setSupervisor(ITSupervisor);
        ITConsultant.setBusinessUnit(ITBusinessUnit);

        entityManager.persist(ITConsultant);
        Long consultantID = ITConsultant.getId();

        BusinessEmployee businessEmployee = new BusinessEmployee();
        businessEmployee.setUserName("BusinessEmployeeUserName");
        businessEmployee.setBusinessUnit(operationBusinessUnit);

        entityManager.persist(businessEmployee);
        Long businessEmployeeId = businessEmployee.getId();

        Project project = new Project();
        project.setProjectName("First Project Name");
        project.setProjectStatus(ProjectStatus.ANALYSIS);
        project.setProjectClass(ProjectClass.I);
        project.addBusinessUnit(operationBusinessUnit);
        entityManager.persist(project);

        entityTransaction.commit();
    }

    private void createUser(Employee employee) {
        User user = new User();
        if (employee instanceof BusinessRelationManager) {
            Role brmRole = new Role();
            brmRole.setName("brm");
            Privilege createProjectPrivilege = new Privilege();
            createProjectPrivilege.setName("create_project");
            Privilege assignEmployeePrivilege = new Privilege();
            assignEmployeePrivilege.setName("assign_employee");
            brmRole.addPrivilege(createProjectPrivilege);
            brmRole.addPrivilege(assignEmployeePrivilege);
            user.getRoles().add(brmRole);
        } else if (employee instanceof Supervisor) {
            Role supervisorRole = new Role();
            supervisorRole.setName("supervisor");
            Privilege findResourceManagerProjectsPrivilege = new Privilege();
            findResourceManagerProjectsPrivilege.setName("search_resource_manager_projects");
            supervisorRole.addPrivilege(findResourceManagerProjectsPrivilege);
            user.getRoles().add(supervisorRole);
        } else {

        }
        user.setPassword(passwordEncoder.encode("password"));
        employee.setUser(user);
    }

}
