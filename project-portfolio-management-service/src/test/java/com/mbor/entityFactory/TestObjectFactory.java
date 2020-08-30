package com.mbor.entityFactory;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.*;
import com.mbor.domain.projectaspect.*;
import com.mbor.model.*;
import com.mbor.model.ProjectRequestDTO;
import com.mbor.model.creation.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class TestObjectFactory {

    private static Random random = new Random();

    public Project prepareProject() {
        Project project = new Project();
        project.setProjectName("ProjectName" + random.nextLong());
        return project;
    }

    public ProjectDTO prepareProjectDTO() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectName("ProjectName" + random.nextLong());
        return projectDTO;
    }

    public ProjectDTO prepareProjectDTOFromEntity(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectName(project.getProjectName());
        return projectDTO;
    }

    public Project prepareProjectFromProjectCreationDTO(ProjectCreationDTO projectCreationDTO) {
        Project project = new Project();
        project.setProjectName(projectCreationDTO.getProjectName());
        project.setProjectClass(ProjectClass.valueOf(projectCreationDTO.getProjectClass().name()));
        return project;
    }

    public BusinessUnit prepareBusinessUnit() {
       return prepareBusinessUnit("BusinessUnit" + random.nextLong());
    }

    public BusinessUnit prepareBusinessUnit(String name) {
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName(name);
        return businessUnit;
    }

    public BusinessUnitCreationDTO prepareBusinessUnitCreationDto() {
        BusinessUnitCreationDTO businessUnitCreationDTO = new BusinessUnitCreationDTO();
        businessUnitCreationDTO.setName("BusinessUnitName" + random.nextLong());
        return businessUnitCreationDTO;
    }

    public BusinessUnitDTO prepareBusinessUnitDTOFromEntity(BusinessUnit businessUnit){
        BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
        businessUnitDTO.setId(businessUnit.getId());
        businessUnitDTO.setName(businessUnit.getName());
        return businessUnitDTO;
    }
    public BusinessUnit prepareBusinessUnitFromCreationDTO(BusinessUnitCreationDTO businessUnitCreationDTO){
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName(businessUnitCreationDTO.getName());
        return businessUnit;
    }

    public BusinessUnitCreatedDTO prepareBusinessUnitCreatedDTOFromEntity(BusinessUnit businessUnit){
        BusinessUnitCreatedDTO businessUnitCreatedDTO = new BusinessUnitCreatedDTO();
        businessUnitCreatedDTO.setName(businessUnit.getName());
        return businessUnitCreatedDTO;
    }

    public EmployeeCreationDTO prepareEmployeeCreationDTO(EmployeeType employeeType, String firstName, String lastName){
        EmployeeCreationDTO employeeCreationDTO = new EmployeeCreationDTO();
        employeeCreationDTO.setFirstName(firstName);
        employeeCreationDTO.setLastName(lastName);
        employeeCreationDTO.setEmployeeType(employeeType);
        return employeeCreationDTO;
    }

    public BusinessRelationManager prepareBusinessRelationManagerFromEmployeeCreationDTO(EmployeeCreationDTO employeeCreationDTO){
        if (employeeCreationDTO.getEmployeeType() != EmployeeType.BusinessRelationManager) {
            throw new RuntimeException("Bad Employee Type");
        }
        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setFirstName(employeeCreationDTO.getFirstName());
        businessRelationManager.setLastName(employeeCreationDTO.getLastName());
        return businessRelationManager;
    }

    public BusinessRelationManager prepareBusinessRelationManager(){
        return prepareBusinessRelationManager("BRM UserName" + random.nextLong());
    }

    public BusinessRelationManager prepareBusinessRelationManager(String name) {
        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setUserName(name);
        return businessRelationManager;
    }

    public BusinessRelationManagerDTO prepareBusinessRelationManagerDTOFromEntity(BusinessRelationManager businessRelationManager) {
        BusinessRelationManagerDTO businessRelationManagerDTO = new BusinessRelationManagerDTO();
        if (businessRelationManager.getId() != null) {
            businessRelationManagerDTO.setId(businessRelationManager.getId());
        }
        businessRelationManagerDTO.setUserName(businessRelationManager.getUserName());
        return businessRelationManagerDTO;
    }

    public Director prepareDirector(){
        return prepareDirector("Director" + random.nextLong());
    }

    public Director prepareDirector(String name){
        Director director = new Director();
        director.setUserName(name);
        return director;
    }

    public Supervisor prepareSupervisor() {
        return prepareSupervisor("Supervisor" + random.nextLong());
    }

    public Supervisor prepareSupervisor(String name) {
        Supervisor supervisor = new Supervisor();
        supervisor.setUserName(name);
        return supervisor;
    }

    public Consultant prepareConsultant() {
        return prepareConsultant("Consultant" + random.nextLong());
    }

    public Consultant prepareConsultant(String name) {
        Consultant consultant = new Consultant();
        consultant.setUserName(name);
        return consultant;
    }

    public BusinessEmployee prepareBusinessEmployee() {
       return prepareBusinessEmployee("BusinessEmployee" + random.nextLong());
    }

    public BusinessEmployee prepareBusinessEmployee(String name) {
        BusinessEmployee businessEmployee = new BusinessEmployee();
        businessEmployee.setUserName(name);
        return businessEmployee;
    }

    public BusinessEmployeeDTO prepareBusinessEmployeeDTOFromEntity(BusinessEmployee businessEmployee) {
        BusinessEmployeeDTO businessEmployeeDTO = new BusinessEmployeeDTO();
        if(businessEmployee.getId() != null){
            businessEmployeeDTO.setId(businessEmployee.getId());
        }
        businessEmployeeDTO.setUserName(businessEmployee.getUserName());
        return businessEmployeeDTO;
    }

    public ResourceManager prepareResourceManager(Supervisor supervisor){
        ResourceManager resourceManager = prepareResourceManager();
        resourceManager.setEmployee(supervisor);
        return resourceManager;
    }
    public ResourceManager prepareResourceManager(){
        return new ResourceManager();
    }

    public ProjectManager prepareProjectManger(IProjectManager employee){
        ProjectManager projectManager = prepareProjectManager();
        projectManager.setEmployee(employee);
        return projectManager;
    }

    public ProjectManager prepareProjectManager(){
        return new ProjectManager();
    }

    public SolutionArchitect prepareSolutionArchitect(Consultant consultant){
        SolutionArchitect solutionArchitect = prepareSolutionArchitect();
        solutionArchitect.setEmployee(consultant);
        return solutionArchitect;
    }

    public SolutionArchitect prepareSolutionArchitect(){
        return new SolutionArchitect();
    }

    public BusinessLeaderDTO prepareBusinessLeaderDTOFromEntity(BusinessLeader businessLeader){
        BusinessLeaderDTO businessLeaderDTO = new BusinessLeaderDTO();
        businessLeader.setId(businessLeader.getId());
        return businessLeaderDTO;
    }

    public ProjectAspectLine prepareProjectAspectLine() {
        ProjectAspectLine projectAspectLine = new ProjectAspectLine();

        BudgetAspect budgetAspect = new BudgetAspect();
        budgetAspect.setAspectStatus(AspectStatus.GREEN);
        budgetAspect.setDescription("Budget Description");
        projectAspectLine.setBudgetAspect(budgetAspect);

        ResourcesAspect resourcesAspect = new ResourcesAspect();
        resourcesAspect.setAspectStatus(AspectStatus.RED);
        resourcesAspect.setDescription("Resources Description");
        projectAspectLine.setResourcesAspect(resourcesAspect);

        ScopeAspect scopeAspect = new ScopeAspect();
        scopeAspect.setAspectStatus(AspectStatus.YELLOW);
        scopeAspect.setDescription("Scope Description");
        projectAspectLine.setScopeAspect(scopeAspect);

        DeadlineAspect deadlineAspect = new DeadlineAspect();
        deadlineAspect.setAspectStatus(AspectStatus.GREEN);
        deadlineAspect.setDescription("Deadline Description");
        projectAspectLine.setDeadlineAspect(deadlineAspect);

        return projectAspectLine;
    }

    public ProjectRequest prepareProjectRequest() {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setProjectName("Project Name" + random.nextLong());
        projectRequest.setDescription("Project Description" + random.nextLong());
        return projectRequest;
    }

    public ProjectRequestDTO prepareProjectRequestDTO(){
        return new ProjectRequestDTO();
    }

    public ProjectRequestCreationDTO prepareProjectRequestCreationDTO(String projectName, String projectDescription, Long businessUnitId) {
        ProjectRequestCreationDTO projectRequestCreationDTO = new ProjectRequestCreationDTO();
        projectRequestCreationDTO.setProjectName(projectName);
        projectRequestCreationDTO.setDescription(projectDescription);
        projectRequestCreationDTO.setBusinessUnitId(businessUnitId);
        return projectRequestCreationDTO;
    }

    public ProjectRequest prepareProjectRequestFromCreationDTO(ProjectRequestCreationDTO projectRequestCreationDTO){
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setProjectName(projectRequestCreationDTO.getProjectName());
        projectRequest.setDescription(projectRequestCreationDTO.getDescription());
        return projectRequest;
    }

    public com.mbor.model.creation.ProjectRequestDTO prepareProjectRequestCreatedDTOFromProjectRequest(ProjectRequest projectRequest){
        com.mbor.model.creation.ProjectRequestDTO projectRequestDTO = new com.mbor.model.creation.ProjectRequestDTO();
        projectRequestDTO.setProjectName(projectRequest.getProjectName());
        projectRequestDTO.setDescription(projectRequest.getDescription());
        BusinessRelationManagerDTO businessRelationManagerDTO = prepareBusinessRelationManagerDTOFromEntity(projectRequest.getBusinessRelationManager());
        BusinessUnitDTO businessUnitDTO = prepareBusinessUnitDTOFromEntity(projectRequest.getBusinessUnit());
        projectRequestDTO.setBusinessRelationManager(businessRelationManagerDTO);
        projectRequestDTO.setBusinessUnit(businessUnitDTO);
        return projectRequestDTO;
    }

    public ProjectCreationDTO prepareProjectCreationDTO(String projectName, ProjectClassDTO projectClassDTO, Long businessRelationManagerId, Long businessLeaderId, Long primaryBusinessUnitId, List<Long> secondaryBusinessUnitIds) {

        ProjectCreationDTO projectCreationDTO = new ProjectCreationDTO();
        projectCreationDTO.setProjectName(projectName);

        projectCreationDTO.setProjectClass(projectClassDTO);

        projectCreationDTO.setBusinessRelationManagerId(businessRelationManagerId);
        projectCreationDTO.setBusinessLeaderId(businessLeaderId);
        projectCreationDTO.setPrimaryBusinessUnitId(primaryBusinessUnitId);
        if (!secondaryBusinessUnitIds.isEmpty()) {
            projectCreationDTO.setSecondaryBusinessUnitIds(secondaryBusinessUnitIds);
        }
        return projectCreationDTO;
    }

    public ProjectCreatedDTO prepareProjectCreatedDTO(ProjectCreationDTO projectCreationDTO, Project projectFromCreationDTO, BusinessRelationManagerDTO businessRelationManagerDTO, BusinessLeaderDTO businessLeaderDTO, BusinessUnitDTO primaryBusinessUnitDTO) {
        ProjectCreatedDTO projectCreatedDTO = new ProjectCreatedDTO();
        projectCreatedDTO.setId(projectFromCreationDTO.getId());
        projectCreatedDTO.setProjectName(projectCreationDTO.getProjectName());
        List<ProjectStatusHistoryLineDTO> projectStatusHistoryLines = new ArrayList<>();
        projectStatusHistoryLines.add(prepareOpenProjectStatusHistoryLine());
        projectCreatedDTO.setProjectStatusHistoryLines(projectStatusHistoryLines);
        projectCreatedDTO.setBusinessRelationManager(businessRelationManagerDTO);
        projectCreatedDTO.setBusinessLeader(businessLeaderDTO);
        projectCreatedDTO.setPrimaryBusinessUnit(primaryBusinessUnitDTO);
        return projectCreatedDTO;
    }

    public ProjectStatusHistoryLineDTO prepareOpenProjectStatusHistoryLine() {
        ProjectStatusHistoryLineDTO openProjectStatusHistoryLine = new ProjectStatusHistoryLineDTO();
        openProjectStatusHistoryLine.setPreviousStatus(ProjectStatusDTO.ANALYSIS);
        openProjectStatusHistoryLine.setCurrentStatus(ProjectStatusDTO.ANALYSIS);
        openProjectStatusHistoryLine.setDescription("Project opened");
        return openProjectStatusHistoryLine;
    }
}
