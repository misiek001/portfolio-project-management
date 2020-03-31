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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

@Service
@Profile({"dev", "controller-integration"})
public class DevDataLoader {

    private final PasswordEncoder passwordEncoder;
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    public DevDataLoader(PasswordEncoder passwordEncoder) {
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

        BusinessUnit ITBusinessUnit = new BusinessUnit();
        ITBusinessUnit.setName("IT Business Unit");
        entityManager.persist(ITBusinessUnit);

        Director ITDirector = new Director();
        ITDirector.setUserName("DirectorUserName");
        ITDirector.setBusinessUnit(ITBusinessUnit);

        entityManager.persist(ITDirector);

        Supervisor ITSupervisor = new Supervisor();
        ITSupervisor.setUserName("ITSupervisorUserName");
        ITSupervisor.setBusinessUnit(ITBusinessUnit);
        ITSupervisor.setDirector(ITDirector);
        createUser(ITSupervisor);

        entityManager.persist(ITSupervisor);

        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setUserName("BRMUserName");
        businessRelationManager.setBusinessUnit(ITBusinessUnit);
        businessRelationManager.setDirector(ITDirector);
        createUser(businessRelationManager);

        entityManager.persist(businessRelationManager);

        Consultant ITConsultant = new Consultant();
        ITConsultant.setUserName("ConsultantUserName");
        ITConsultant.setSupervisor(ITSupervisor);
        ITConsultant.setBusinessUnit(ITBusinessUnit);
        createUser(ITConsultant);
        entityManager.persist(ITConsultant);

        BusinessEmployee businessEmployee = new BusinessEmployee();
        businessEmployee.setUserName("BusinessEmployeeUserName");
        businessEmployee.setBusinessUnit(operationBusinessUnit);

        entityManager.persist(businessEmployee);

        Project project = new Project();
        project.setProjectName("First Project Name");
        project.setProjectStatus(ProjectStatus.ANALYSIS);
        project.setProjectClass(ProjectClass.I);
        project.addBusinessUnit(operationBusinessUnit);

        ResourceManager resourceManager = new ResourceManager();
        resourceManager.setEmployee(ITSupervisor);
        project.setResourceManager(resourceManager);

        ProjectManager projectManager = new ProjectManager();
        projectManager.setEmployee(ITConsultant);

        project.setProjectManager(projectManager);

        entityManager.persist(project);

        entityTransaction.commit();
        System.out.println();
    }

    private void createUser(Employee employee) {

        Role resourceManagerRole = new Role();
        resourceManagerRole.setName("resource-manager");
        Privilege searchResourceManagerProjectsPrivilege = new Privilege();
        searchResourceManagerProjectsPrivilege.setName("search_resource_manager_projects");
        resourceManagerRole.addPrivilege(searchResourceManagerProjectsPrivilege);

        Role projectManagerRole = new Role();
        projectManagerRole.setName("project-manager");
        Privilege addProjectAspectLinePrivilege = new Privilege();
        addProjectAspectLinePrivilege.setName("add_project_aspects_line");
        projectManagerRole.addPrivilege(addProjectAspectLinePrivilege);
        Privilege addRealEndDatePrivilege = new Privilege();
        addRealEndDatePrivilege.setName("add_real_end_date");
        projectManagerRole.addPrivilege(addRealEndDatePrivilege);

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

            Privilege findSupervisorProjectsPrivilege = new Privilege();
            findSupervisorProjectsPrivilege.setName("search_supervisor_projects");
            supervisorRole.addPrivilege(findSupervisorProjectsPrivilege);

            user.getRoles().add(supervisorRole);
            user.getRoles().add(resourceManagerRole);

        } else if (employee instanceof Consultant) {
            Role consultantRole = new Role();
            consultantRole.setName("consultant");
            Privilege searchConsultantProjectsPrivilege = new Privilege();
            searchConsultantProjectsPrivilege.setName("search_consultant_projects");
            consultantRole.addPrivilege(searchConsultantProjectsPrivilege);
            user.getRoles().add(consultantRole);
            user.getRoles().add(projectManagerRole);
        } else {

        }


        user.setPassword(passwordEncoder.encode("password"));
        employee.setUser(user);
    }
}
