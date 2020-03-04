package com.mbor.dataloader;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.domain.security.Privilege;
import com.mbor.domain.security.Role;
import com.mbor.domain.security.User;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.*;

@Service
@Profile("dev")
public class TestDataLoader {

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    private final PasswordEncoder passwordEncoder;

    public TestDataLoader(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void onApplicationEvent() {

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
        project.setProjectClass(ProjectClass.I);
        project.addBusinessUnit(operationBusinessUnit);
        ResourceManager resourceManager = new ResourceManager();
        resourceManager.setEmployee(ITSupervisor);
        project.setResourceManager(resourceManager);
        ProjectManager projectManager = new ProjectManager();
        projectManager.setEmployee( ITConsultant);
        project.setProjectManager(projectManager);
        entityManager.persist(project);

       entityTransaction.commit();
        System.out.println();
    }

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
        user.setPassword(passwordEncoder.encode("pass"));
        employee.setUser(user);
    }

}
