package com.mbor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbor.model.*;
import com.mbor.model.assignment.EmployeeAssignDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.model.projectaspect.*;
import com.mbor.model.search.ResourceManagerSearchProjectDTO;
import com.mbor.spring.ServiceConfiguration;
import com.mbor.spring.WebConfiguration;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.mbor.dataloader.DevDataLoader.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfiguration.class, ServiceConfiguration.class})
@TestPropertySource(locations = "classpath:security-client-test.properties")
@ActiveProfiles(profiles = {"test", "controller-integration"})
class ProjectControllerTest {

    private static MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private Environment env;

    @BeforeAll
    static void init(@Autowired WebApplicationContext webApplicationContext, @Autowired FilterChainProxy springSecurityFilterChain) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void obtainAccessTokenThenSuccess() throws Exception {
        Assertions.assertNotNull(obtainAccessToken(env.getProperty("user.brm.name"), env.getProperty("user.brm.password")));
        Assertions.assertNotNull(obtainAccessToken(env.getProperty("user.supervisor.name"), env.getProperty("user.supervisor.password")));
    }


    void createProjectThenSuccess() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.brm.name"), env.getProperty("user.brm.password"));

        mockMvc.perform(post("/projects")
                .header("Authorization", "Bearer " + accessToken)
                .content(prepareProjectCreationDto())
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isCreated());
    }


    @Test
    public void findResourceManagerProjectsThenSuccess() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.supervisor.name"), env.getProperty("user.supervisor.password"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("searchingEmployee", "resource-manager");

        mockMvc.perform(post("/projects")
                .header("Authorization", "Bearer " + accessToken)
                .params(params)
                .content(prepareResourceManagerSearchProjectDto())
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isOk());
    }

    @Test
    public void findSupervisorProjectsThenSuccess() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.supervisor.name"), env.getProperty("user.supervisor.password"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("searchingEmployee", "supervisor");

        mockMvc.perform(post("/projects")
                .header("Authorization", "Bearer " + accessToken)
                .params(params)
                .content(prepareResourceManagerSearchProjectDto())
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isOk());
    }

    @Test
    public void assignEmployeeThenSuccess() throws Exception {

        String accessToken = obtainAccessToken(env.getProperty("user.brm.name"), env.getProperty("user.brm.password"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("action", "assign-employee");

        mockMvc.perform(patch("/projects/" + FIRST_PROJECT_ID)
                .header("Authorization", "Bearer " + accessToken)
                .params(params)
                .content(prepareEmployeeAssignDto())
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isOk());
    }

    @Test
    public void addProjectRealEndDateThenSuccess() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.firstconsultant.name"), env.getProperty("user.firstconsultant.password"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("action", "add-real-end-date");

        mockMvc.perform(patch("/projects/" + FIRST_PROJECT_ID)
                .header("Authorization", "Bearer " + accessToken)
                .params(params)
                .content(prepareRealEndDateDTO(10, "Some Reason"))
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isOk());
    }

    @Test
    public void addProjectAspectLineThenSuccess() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.firstconsultant.name"), env.getProperty("user.firstconsultant.password"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("action", "add-project-aspect-line");

        mockMvc.perform(patch("/projects/" + FIRST_PROJECT_ID)
                .header("Authorization", "Bearer " + accessToken)
                .params(params)
                .content(prepareProjectAspectLineDTO())
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isOk());
    }

    @Test
    public void findConsultantProjectsThenSuccess() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.firstconsultant.name"), env.getProperty("user.firstconsultant.password"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("searchingEmployee", "consultant");

        mockMvc.perform(get("/projects")
                .header("Authorization", "Bearer " + accessToken)
                .params(params)
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isOk());
    }

    @Test
    @Ignore
    void openProjectThenSuccess() {
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", env.getProperty("client.name"));
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(env.getProperty("client.name"), env.getProperty("client.secret")))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    private String prepareEmployeeAssignDto() throws JsonProcessingException {
        EmployeeAssignDTO employeeAssignDTO = new EmployeeAssignDTO();

        return mapper.writeValueAsString(employeeAssignDTO);
    }

    private String prepareProjectCreationDto() throws JsonProcessingException {
        ProjectCreationDTO projectCreationDTO = new ProjectCreationDTO();
        projectCreationDTO.setProjectName("Created Project Name");
        projectCreationDTO.setProjectClass(ProjectClassDTO.I);

        BusinessRelationManagerDTO businessRelationManagerDTO = new BusinessRelationManagerDTO();
        businessRelationManagerDTO.setId(BRM_ID);

        projectCreationDTO.setBusinessRelationManagerId(businessRelationManagerDTO.getId());

        BusinessLeaderDTO businessLeaderDTO = new BusinessLeaderDTO();
        businessLeaderDTO.setId(FIRST_BUSINESS_LEADER_ID);

        projectCreationDTO.setBusinessLeaderId(businessLeaderDTO.getId());

        BusinessUnitDTO businessUnitDTOFirst = new BusinessUnitDTO();
        businessUnitDTOFirst.setId(FIRST_OPERATION_BUSINESS_UNIT_ID);

        projectCreationDTO.setPrimaryBusinessUnitId(businessUnitDTOFirst.getId());

        BusinessUnitDTO businessUnitDTOSecond = new BusinessUnitDTO();
        businessUnitDTOSecond.setId(SECOND_OPERATION_BUSINESS_UNIT_ID);

        return mapper.writeValueAsString(projectCreationDTO);
    }

    private String prepareResourceManagerSearchProjectDto() throws JsonProcessingException {
        ResourceManagerSearchProjectDTO resourceManagerSearchProjectDTO = new ResourceManagerSearchProjectDTO();
        resourceManagerSearchProjectDTO.setProjectId(FIRST_PROJECT_ID);
        resourceManagerSearchProjectDTO.setProjectClassDTOList(Collections.singletonList(ProjectClassDTO.I));
        resourceManagerSearchProjectDTO.setProjectStatusDTOList(Collections.singletonList(ProjectStatusDTO.ANALYSIS));

        return mapper.writeValueAsString(resourceManagerSearchProjectDTO);
    }

    private String prepareProjectAspectLineDTO() throws JsonProcessingException {
        ProjectAspectLineDTO projectAspectLineDTO = new ProjectAspectLineDTO();

        BudgetAspectDTO budgetAspectDTO = new BudgetAspectDTO();
        budgetAspectDTO.setAspectStatus(AspectStatusDTO.GREEN);
        budgetAspectDTO.setDescription("Budget Description");
        projectAspectLineDTO.setBudgetAspect(budgetAspectDTO);

        ResourcesAspectDTO resourcesAspectDTO = new ResourcesAspectDTO();
        resourcesAspectDTO.setAspectStatus(AspectStatusDTO.YELLOW);
        resourcesAspectDTO.setDescription("Resources Description");
        projectAspectLineDTO.setResourcesAspect(resourcesAspectDTO);

        ScopeAspectDTO ScopeAspectDTO = new ScopeAspectDTO();
        ScopeAspectDTO.setAspectStatus(AspectStatusDTO.RED);
        ScopeAspectDTO.setDescription("Scope Aspect");
        projectAspectLineDTO.setScopeAspect(ScopeAspectDTO);

        DeadlineAspectDTO deadlineAspectDTO = new DeadlineAspectDTO();
        deadlineAspectDTO.setAspectStatus(AspectStatusDTO.GREEN);
        deadlineAspectDTO.setDescription("Deadline Description");
        projectAspectLineDTO.setDeadlineAspect(deadlineAspectDTO);

        return mapper.writeValueAsString(projectAspectLineDTO);
    }

    private String prepareRealEndDateDTO(int offset, String reason) throws JsonProcessingException {
        RealEndDateDTO realEndDateDTO = new RealEndDateDTO();
        realEndDateDTO.setEndDate(LocalDateTime.now().plusDays(offset));
        realEndDateDTO.setReason(reason);
        return mapper.writeValueAsString(realEndDateDTO);
    }


}