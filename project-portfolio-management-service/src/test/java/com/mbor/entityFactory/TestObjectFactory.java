package com.mbor.entityFactory;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.*;
import com.mbor.domain.projectaspect.*;
import com.mbor.model.*;
import com.mbor.model.creation.DemandSheetCreatedDTO;
import com.mbor.model.creation.DemandSheetCreationDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
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


    public BusinessUnitDTO prepareBusinessUnitDTOFromEntity(BusinessUnit businessUnit){
        BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
        businessUnitDTO.setId(businessUnit.getId());
        businessUnitDTO.setName(businessUnit.getName());
        return businessUnitDTO;
    }

    public DemandSheet prepareDemandSheet() {
        DemandSheet demandSheet = new DemandSheet();
        demandSheet.setProjectName("Project Name" + random.nextLong());
        demandSheet.setDescription("Project Description" + random.nextLong());
        return demandSheet;
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

    public DemandSheetCreationDTO prepareDemandSheetCreationDTO(String projectName, String projectDescription, Long businessUnitId) {
        DemandSheetCreationDTO demandSheetCreationDTO = new DemandSheetCreationDTO();
        demandSheetCreationDTO.setProjectName(projectName);
        demandSheetCreationDTO.setDescription(projectDescription);
        demandSheetCreationDTO.setBusinessUnitId(businessUnitId);
        return demandSheetCreationDTO;
    }

    public DemandSheetDTO prepareDemandSheetDTO(){
        return new DemandSheetDTO();
    }

    public DemandSheet prepareDemandSheetFromCreationDTO(DemandSheetCreationDTO demandSheetCreationDTO){
        DemandSheet demandSheet = new DemandSheet();
        demandSheet.setProjectName(demandSheetCreationDTO.getProjectName());
        demandSheet.setDescription(demandSheetCreationDTO.getDescription());
        return demandSheet;
    }

    public DemandSheetCreatedDTO prepareDemandSheetCreatedDTOFromDemandSheet(DemandSheet demandSheet){
        DemandSheetCreatedDTO demandSheetCreatedDTO = new DemandSheetCreatedDTO();
        demandSheetCreatedDTO.setProjectName(demandSheet.getProjectName());
        demandSheetCreatedDTO.setDescription(demandSheet.getDescription());
        BusinessRelationManagerDTO businessRelationManagerDTO = prepareBusinessRelationManagerDTOFromEntity(demandSheet.getBusinessRelationManager());
        BusinessUnitDTO businessUnitDTO = prepareBusinessUnitDTOFromEntity(demandSheet.getBusinessUnit());
        demandSheetCreatedDTO.setBusinessRelationManager(businessRelationManagerDTO);
        demandSheetCreatedDTO.setBusinessUnit(businessUnitDTO);
        return demandSheetCreatedDTO;
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
