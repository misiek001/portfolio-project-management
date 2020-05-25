package com.mbor.dataloader;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.domain.employeeinproject.SolutionArchitect;
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
import java.util.function.Consumer;

@Service
@Profile({"dev", "controller-integration"})
public class DevDataLoader {


    private final PasswordEncoder passwordEncoder;

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    public static Long RESOURCE_MANAGER_ROLE_ID;
    public static Long PROJECT_MANAGER_ROLE_ID;
    public static Long BRM_ROLE_ID;
    public static Long SUPERVISOR_ROLE_ID;
    public static Long CONSULTANT_ROLE_ID;
    public static  Long BUSINESS_EMPLOYEE_ROLE_ID;

    public static Long IT_BUSINESS_UNIT_ID;
    public static Long FIRST_OPERATION_BUSINESS_UNIT_ID;
    public static Long SECOND_OPERATION_BUSINESS_UNIT_ID;

    public static Long IT_DIRECTOR_ID;
    public static Long BRM_ID;
    public static Long SUPERVISOR_ID;
    public static Long FIRST_CONSULTANT_ID;
    public static Long SECOND_CONSULTANT_ID;
    public static Long THIRD_CONSULTANT_ID;
    public static Long FIRST_BUSINESS_EMPLOYEE_ID;

    public static Long FIRST_RESOURCE_MANAGER_ID;
    public static Long FIRST_PROJECT_MANAGER_ID;
    public static Long SECOND_PROJECT_MANAGER_ID;
    public static Long THIRD_PROJECT_MANAGER_ID;
    public static Long FIRST_SOLUTION_ARCHITECT_ID;
    public static Long SECOND_SOLUTION_ARCHITECT_ID;
    public static Long THIRD_SOLUTION_ARCHITECT_ID;
    public static Long FIRST_BUSINESS_LEADER_ID;

    public static Long FIRST_PROJECT_ID;
    public static Long SECOND_PROJECT_ID;
    public static Long THIRD_PROJECT_ID;

    public DevDataLoader(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void onApplicationEvent() {
        doWithinTransaction(addProjectRoleRoles);
        doWithinTransaction(addBusinessUnits);
        doWithinTransaction(addEmployees);
        doWithinTransaction(addBRMToBusinessUnits);
        doWithinTransaction(addProjectRoles);
        doWithinTransaction(addProject);
    }

    private void doWithinTransaction(Consumer<EntityManager> consumer){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        consumer.accept(entityManager);

        entityTransaction.commit();
    }

    private Consumer<EntityManager> addProjectRoleRoles = entityManager ->  {

        Role resourceManagerRole = new Role();
        resourceManagerRole.setName("resource-manager");
        Privilege searchResourceManagerProjectsPrivilege = new Privilege();
        searchResourceManagerProjectsPrivilege.setName("search_resource_manager_projects");
        resourceManagerRole.addPrivilege(searchResourceManagerProjectsPrivilege);
        entityManager.persist(resourceManagerRole);
        RESOURCE_MANAGER_ROLE_ID  = resourceManagerRole.getId();

        Role projectManagerRole = new Role();
        projectManagerRole.setName("project-manager");
        Privilege addProjectAspectLinePrivilege = new Privilege();
        addProjectAspectLinePrivilege.setName("add_project_aspects_line");
        projectManagerRole.addPrivilege(addProjectAspectLinePrivilege);
        Privilege addRealEndDatePrivilege = new Privilege();
        addRealEndDatePrivilege.setName("add_real_end_date");
        projectManagerRole.addPrivilege(addRealEndDatePrivilege);
        entityManager.persist(projectManagerRole);
        PROJECT_MANAGER_ROLE_ID = projectManagerRole.getId();

        Role brmRole = new Role();
        brmRole.setName("brm");
        Privilege createProjectPrivilege = new Privilege();
        createProjectPrivilege.setName("create_project");
        Privilege assignEmployeePrivilege = new Privilege();
        assignEmployeePrivilege.setName("assign_employee");
        Privilege getAllDemandSheetsOfBrmWithNoProjectsPrivilege = new Privilege();
        getAllDemandSheetsOfBrmWithNoProjectsPrivilege.setName("get_all_demandsheets_of_brm_with_no_projects");
        brmRole.addPrivilege(createProjectPrivilege);
        brmRole.addPrivilege(assignEmployeePrivilege);
        brmRole.addPrivilege(getAllDemandSheetsOfBrmWithNoProjectsPrivilege);
        entityManager.persist(brmRole);
        BRM_ROLE_ID = brmRole.getId();

        Role supervisorRole = new Role();
        supervisorRole.setName("supervisor");

        Privilege findSupervisorProjectsPrivilege = new Privilege();
        findSupervisorProjectsPrivilege.setName("search_supervisor_projects");
        supervisorRole.addPrivilege(findSupervisorProjectsPrivilege);
        entityManager.persist(supervisorRole);
        SUPERVISOR_ROLE_ID = supervisorRole.getId();

        Role consultantRole = new Role();
        consultantRole.setName("consultant");
        Privilege searchConsultantProjectsPrivilege = new Privilege();
        searchConsultantProjectsPrivilege.setName("search_consultant_projects");
        consultantRole.addPrivilege(searchConsultantProjectsPrivilege);
        entityManager.persist(consultantRole);
        CONSULTANT_ROLE_ID  = consultantRole.getId();

        Role businessEmployeeRole = new Role();
        businessEmployeeRole.setName("business-employee");
        Privilege createDemandSheetPrivilege = new Privilege();
        createDemandSheetPrivilege.setName("create_demandsheet");
        businessEmployeeRole.addPrivilege(createDemandSheetPrivilege);
        entityManager.persist(businessEmployeeRole);
        BUSINESS_EMPLOYEE_ROLE_ID = businessEmployeeRole.getId();

    };

    private Consumer<EntityManager> addBusinessUnits  =  entityManager -> {

        BusinessUnit ITBusinessUnit = new BusinessUnit();
        ITBusinessUnit.setName("IT Business Unit");
        entityManager.persist(ITBusinessUnit);
        IT_BUSINESS_UNIT_ID = ITBusinessUnit.getId();

        BusinessUnit operationBusinessUnit = new BusinessUnit();
        operationBusinessUnit.setName("First Operation Business Unit");
        entityManager.persist(operationBusinessUnit);
        FIRST_OPERATION_BUSINESS_UNIT_ID = operationBusinessUnit.getId();

        operationBusinessUnit = new BusinessUnit();
        operationBusinessUnit.setName("Second Operation Business Unit");
        entityManager.persist(operationBusinessUnit);
        SECOND_OPERATION_BUSINESS_UNIT_ID = operationBusinessUnit.getId();

    };

    private Consumer<EntityManager> addEmployees = entityManager -> {

        BusinessUnit ITBusinessUnit = entityManager.find(BusinessUnit.class, IT_BUSINESS_UNIT_ID);

        Director ITDirector = new Director();
        ITDirector.setUserName("DirectorUserName");
        ITDirector.setBusinessUnit(ITBusinessUnit);
        createUser(ITDirector);
        entityManager.persist(ITDirector);
        IT_DIRECTOR_ID = ITDirector.getId();

        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setUserName("BRMUserName");
        businessRelationManager.setBusinessUnit(ITBusinessUnit);
        businessRelationManager.setDirector(ITDirector);
        createUser(businessRelationManager);
        businessRelationManager.getUser().getRoles().add(entityManager.find(Role.class, BRM_ROLE_ID));
        entityManager.persist(businessRelationManager);
        BRM_ID = businessRelationManager.getId();

        Supervisor ITSupervisor = new Supervisor();
        ITSupervisor.setUserName("ITSupervisorUserName");
        ITSupervisor.setBusinessUnit(ITBusinessUnit);
        ITSupervisor.setDirector(ITDirector);
        createUser(ITSupervisor);
        ITSupervisor.getUser().getRoles().add(entityManager.find(Role.class, PROJECT_MANAGER_ROLE_ID));
        ITSupervisor.getUser().getRoles().add(entityManager.find(Role.class, SUPERVISOR_ROLE_ID));
        ITSupervisor.getUser().getRoles().add(entityManager.find(Role.class, RESOURCE_MANAGER_ROLE_ID));
        entityManager.persist(ITSupervisor);
        SUPERVISOR_ID = ITSupervisor.getId();

        Consultant firstConsultant = new Consultant();
        firstConsultant.setUserName("FirstConsultantUserName");
        firstConsultant.setSupervisor(ITSupervisor);
        firstConsultant.setBusinessUnit(ITBusinessUnit);
        createUser(firstConsultant);
        firstConsultant.getUser().getRoles().add(entityManager.find(Role.class, PROJECT_MANAGER_ROLE_ID));
        firstConsultant.getUser().getRoles().add(entityManager.find(Role.class, CONSULTANT_ROLE_ID));
        entityManager.persist(firstConsultant);
        FIRST_CONSULTANT_ID = firstConsultant.getId();
        
        Consultant secondConsultant = new Consultant();
        secondConsultant.setUserName("SecondConsultantUserName");
        secondConsultant.setSupervisor(ITSupervisor);
        secondConsultant.setBusinessUnit(ITBusinessUnit);
        createUser(secondConsultant);
        secondConsultant.getUser().getRoles().add(entityManager.find(Role.class, PROJECT_MANAGER_ROLE_ID));
        secondConsultant.getUser().getRoles().add(entityManager.find(Role.class, CONSULTANT_ROLE_ID));
        entityManager.persist(secondConsultant);
        SECOND_CONSULTANT_ID = secondConsultant.getId();

        Consultant thirdConsultant = new Consultant();
        thirdConsultant.setUserName("ThirdConsultantUserName");
        thirdConsultant.setSupervisor(ITSupervisor);
        thirdConsultant.setBusinessUnit(ITBusinessUnit);
        createUser(thirdConsultant);
        thirdConsultant.getUser().getRoles().add(entityManager.find(Role.class, PROJECT_MANAGER_ROLE_ID));
        thirdConsultant.getUser().getRoles().add(entityManager.find(Role.class, CONSULTANT_ROLE_ID));
        entityManager.persist(thirdConsultant);
        THIRD_CONSULTANT_ID = thirdConsultant.getId();

        BusinessEmployee firstBusinessEmployee = new BusinessEmployee();
        firstBusinessEmployee.setUserName("FirstBusinessEmployeeUserName");
        firstBusinessEmployee.setBusinessUnit(entityManager.find(BusinessUnit.class, FIRST_OPERATION_BUSINESS_UNIT_ID));
        createUser(firstBusinessEmployee);
        firstBusinessEmployee.getUser().getRoles().add(entityManager.find(Role.class, BUSINESS_EMPLOYEE_ROLE_ID));
        entityManager.persist(firstBusinessEmployee);
        FIRST_BUSINESS_EMPLOYEE_ID = firstBusinessEmployee.getId();

    };

    private Consumer<EntityManager> addBRMToBusinessUnits = entityManager -> {

      BusinessUnit firstBusinessUnit = simpleFind(entityManager, BusinessUnit.class, FIRST_OPERATION_BUSINESS_UNIT_ID);
      firstBusinessUnit.setBusinessRelationManager(simpleFind(entityManager, BusinessRelationManager.class, BRM_ID));

      BusinessUnit secondBusinessUnit = simpleFind(entityManager, BusinessUnit.class, FIRST_OPERATION_BUSINESS_UNIT_ID);
      secondBusinessUnit.setBusinessRelationManager(simpleFind(entityManager, BusinessRelationManager.class, BRM_ID));

    };



    private Consumer<EntityManager> addProjectRoles = entityManager -> {

        ResourceManager resourceManager = new ResourceManager();
        resourceManager.setEmployee(simpleFind(entityManager, Supervisor.class, SUPERVISOR_ID));
        entityManager.persist(resourceManager);
        FIRST_RESOURCE_MANAGER_ID = resourceManager.getId();

        ProjectManager firstProjectManager = new ProjectManager();
        firstProjectManager.setEmployee(entityManager.find(Consultant.class, FIRST_CONSULTANT_ID));
        entityManager.persist(firstProjectManager);
        FIRST_PROJECT_MANAGER_ID = firstProjectManager.getId();

        ProjectManager secondProjectManager = new ProjectManager();
        secondProjectManager.setEmployee(entityManager.find(Consultant.class, SECOND_CONSULTANT_ID));
        entityManager.persist(secondProjectManager);
        SECOND_PROJECT_MANAGER_ID = secondProjectManager.getId();

        ProjectManager thirdProjectManager = new ProjectManager();
        thirdProjectManager.setEmployee(entityManager.find(Supervisor.class, SUPERVISOR_ID));
        entityManager.persist(thirdProjectManager);
        THIRD_PROJECT_MANAGER_ID = thirdProjectManager.getId();

        BusinessLeader businessLeader = new BusinessLeader();
        businessLeader.setEmployee(simpleFind(entityManager, BusinessEmployee.class, FIRST_BUSINESS_EMPLOYEE_ID));
        entityManager.persist(businessLeader);
        FIRST_BUSINESS_LEADER_ID = businessLeader.getId();

        SolutionArchitect firstSolutionArchitect = new SolutionArchitect();
        firstSolutionArchitect.setEmployee(simpleFind(entityManager, Consultant.class, FIRST_CONSULTANT_ID));
        entityManager.persist(firstSolutionArchitect);
        FIRST_SOLUTION_ARCHITECT_ID = firstSolutionArchitect.getId();

        SolutionArchitect secondSolutionArchitect = new SolutionArchitect();
        secondSolutionArchitect.setEmployee(simpleFind(entityManager, Consultant.class, SECOND_CONSULTANT_ID));
        entityManager.persist(secondSolutionArchitect);
        SECOND_SOLUTION_ARCHITECT_ID = secondSolutionArchitect.getId();

        SolutionArchitect thirdSolutionArchitect = new SolutionArchitect();
        thirdSolutionArchitect.setEmployee(simpleFind(entityManager, Consultant.class, THIRD_CONSULTANT_ID));
        entityManager.persist(thirdSolutionArchitect);
        THIRD_SOLUTION_ARCHITECT_ID = thirdSolutionArchitect.getId();
    };



    private Consumer<EntityManager> addProject = entityManager -> {
        Project firstProject = new Project();
        firstProject.setProjectName("FirstProjectName");
        Project secondProject = new Project();
        secondProject.setProjectName("SecondProjectName");
        Project thirdProject = new Project();
        thirdProject.setProjectName("ThirdProjectName");

        firstProject.setProjectClass(ProjectClass.I);
        secondProject.setProjectClass(ProjectClass.I);
        thirdProject.setProjectClass(ProjectClass.II);
        
//        firstProject.setProjectStatus(ProjectStatus.ANALYSIS);
//        secondProject.setProjectStatus(ProjectStatus.ANALYSIS);
//        thirdProject.setProjectStatus(ProjectStatus.IN_PROGRESS);

        firstProject.setResourceManager(simpleFind(entityManager, ResourceManager.class, FIRST_RESOURCE_MANAGER_ID));
        firstProject.setProjectManager(simpleFind(entityManager, ProjectManager.class, FIRST_PROJECT_MANAGER_ID));
        firstProject.addSolutionArchitect(simpleFind(entityManager, SolutionArchitect.class, FIRST_SOLUTION_ARCHITECT_ID));
        firstProject.addSolutionArchitect(simpleFind(entityManager, SolutionArchitect.class, SECOND_SOLUTION_ARCHITECT_ID));
        firstProject.setPrimaryBusinessUnit(simpleFind(entityManager, BusinessUnit.class, FIRST_OPERATION_BUSINESS_UNIT_ID));
        firstProject.addSecondaryBusinessUnit(simpleFind(entityManager, BusinessUnit.class, SECOND_OPERATION_BUSINESS_UNIT_ID));

        secondProject.addSolutionArchitect(simpleFind(entityManager, SolutionArchitect.class, THIRD_SOLUTION_ARCHITECT_ID));
        secondProject.setResourceManager(simpleFind(entityManager, ResourceManager.class, FIRST_RESOURCE_MANAGER_ID));
        secondProject.setProjectManager(simpleFind(entityManager, ProjectManager.class, SECOND_PROJECT_MANAGER_ID));
        secondProject.addSolutionArchitect(simpleFind(entityManager, SolutionArchitect.class, SECOND_SOLUTION_ARCHITECT_ID));
        secondProject.setPrimaryBusinessUnit(simpleFind(entityManager, BusinessUnit.class, FIRST_OPERATION_BUSINESS_UNIT_ID));

        thirdProject.setProjectManager(simpleFind(entityManager, ProjectManager.class, FIRST_PROJECT_MANAGER_ID));
        thirdProject.addSolutionArchitect(simpleFind(entityManager, SolutionArchitect.class, SECOND_SOLUTION_ARCHITECT_ID));
        thirdProject.setResourceManager(simpleFind(entityManager, ResourceManager.class, FIRST_RESOURCE_MANAGER_ID));
        thirdProject.setPrimaryBusinessUnit(simpleFind(entityManager, BusinessUnit.class, SECOND_OPERATION_BUSINESS_UNIT_ID));

        entityManager.persist(firstProject);
        FIRST_PROJECT_ID = firstProject.getId();

        entityManager.persist(secondProject);
        SECOND_PROJECT_ID = secondProject.getId();

        entityManager.persist(thirdProject);
        THIRD_PROJECT_ID = thirdProject.getId();

    };

    private void createUser(Employee employee) {
        User user = new User();
        user.setPassword(passwordEncoder.encode("password"));
        employee.setUser(user);
    }

    private <T> T simpleFind(EntityManager entityManager, Class<T> clazz, Long id){
         return entityManager.find(clazz, id);
    }


}
