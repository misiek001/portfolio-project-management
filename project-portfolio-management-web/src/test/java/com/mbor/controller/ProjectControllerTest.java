package com.mbor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbor.domain.employeeinproject.IProjectManager;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.model.*;
import com.mbor.model.assignment.EmployeeAssignDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.model.search.ResourceManagerSearchProjectDTO;
import com.mbor.service.IEmployeeService;
import com.mbor.spring.ServiceConfiguration;
import com.mbor.spring.WebConfiguration;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfiguration.class, ServiceConfiguration.class})
@TestPropertySource(locations = "classpath:security-client-test.properties")
@ActiveProfiles(profiles = {"test", "controller-integration"})
class ProjectControllerTest {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private  Environment env;

    private static MockMvc mockMvc;

    @BeforeAll
    static void init(@Autowired WebApplicationContext webApplicationContext,   @Autowired FilterChainProxy springSecurityFilterChain ){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void obtainAccessTokenThenSuccess() throws Exception {
        Assertions.assertNotNull(obtainAccessToken(env.getProperty("user.brm.name"), env.getProperty("user.brm.password")));
        Assertions.assertNotNull(obtainAccessToken(env.getProperty("user.supervisor.name"), env.getProperty("user.supervisor.password")));
    }

    @Test
    void createProject() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.brm.name"), env.getProperty("user.brm.password"));
        mockMvc.perform(post("/projects")
                .header("Authorization", "Bearer " + accessToken)
                .content(prepareProjectCreationDto())
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isCreated());
    }

    @Test
    public void assignEmployeeThenSuccess() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.brm.name"), env.getProperty("user.brm.password"));
        mockMvc.perform(post("/projects")
                .header("Authorization", "Bearer " + accessToken)
                .content(mapper.writeValueAsString(prepareEmployeeAssignDto()))
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isOk());
    }

    @Test
    public void assignEmployeeWithAttemptToCreateNewRoleWhenRoleExistThenBadRequest(@Autowired EntityManagerFactory entityManagerFactory) throws Exception {
        loadProjectManager(entityManagerFactory);

        String accessToken = obtainAccessToken(env.getProperty("user.brm.name"), env.getProperty("user.brm.password"));
        mockMvc.perform(put("/projects")
                .header("Authorization", "Bearer " + accessToken)
                .content(prepareEmployeeAssignDto())
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isBadRequest());
    }
    @Test
    public void findResourceManagerProjectsThenSuccess() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.supervisor.name"), env.getProperty("user.supervisor.password"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("searchingEmployee", "resource-manager");

        MvcResult mvcResult = mockMvc.perform(post("/projects")
                .header("Authorization", "Bearer " + accessToken)
                .params(params)
                .content(prepareResourceManagerSearchProjectDto())
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void findSupervisorProjectsThenSuccess() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.supervisor.name"), env.getProperty("user.supervisor.password"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("searchingEmployee", "supervisor");

        MvcResult mvcResult = mockMvc.perform(post("/projects")
                .header("Authorization", "Bearer " + accessToken)
                .params(params)
                .content(prepareResourceManagerSearchProjectDto())
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isOk()).andReturn();

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
        employeeAssignDTO.setProjectId(1l);

        ConsultantDTO consultantDTO = new ConsultantDTO();
        consultantDTO.setId(4l);
        ProjectManagerDTO projectManagerDTO = new ProjectManagerDTO();
        projectManagerDTO.setEmployee(consultantDTO);

        employeeAssignDTO.setProjectManagerDTO(projectManagerDTO);

        BusinessRelationManagerDTO businessRelationManagerDTO = new BusinessRelationManagerDTO();
        businessRelationManagerDTO.setId(3l);
        employeeAssignDTO.setBusinessRelationManagerDTO(businessRelationManagerDTO);

        SupervisorDTO supervisorDTO = new SupervisorDTO();
        supervisorDTO.setId(2l);
        ResourceManagerDTO resourceManagerDTO = new ResourceManagerDTO();
        resourceManagerDTO.setEmployee(supervisorDTO);
        employeeAssignDTO.setResourceManagerDTO(resourceManagerDTO);

        Set<SolutionArchitectDTO> solutionArchitectDTOS = new HashSet<>();

        SolutionArchitectDTO firstSolutionArchitect = new SolutionArchitectDTO();
        firstSolutionArchitect.setEmployee(consultantDTO);
        solutionArchitectDTOS.add(firstSolutionArchitect);
        SolutionArchitectDTO secondSolutionArchitect  = new SolutionArchitectDTO();
        secondSolutionArchitect.setEmployee(supervisorDTO);
        solutionArchitectDTOS.add(secondSolutionArchitect);
        employeeAssignDTO.setSolutionArchitectDTOS(solutionArchitectDTOS);

        BusinessLeaderDTO businessLeaderDTO = new BusinessLeaderDTO();
        BusinessEmployeeDTO businessEmployeeDTO = new BusinessEmployeeDTO();
        businessEmployeeDTO.setId(5l);

        businessLeaderDTO.setEmployee(businessEmployeeDTO);
        employeeAssignDTO.setBusinessLeaderDTO(businessLeaderDTO);

        return mapper.writeValueAsString(employeeAssignDTO);
    }

    private String prepareProjectCreationDto() throws JsonProcessingException {
        ProjectCreationDTO projectCreationDTO = new ProjectCreationDTO();
        projectCreationDTO.setProjectName("Project Name");

        BusinessRelationManagerDTO businessRelationManagerDTO = new BusinessRelationManagerDTO();
        businessRelationManagerDTO.setId(3l);

        projectCreationDTO.setBusinessRelationManager(businessRelationManagerDTO);

        BusinessLeaderDTO businessLeaderDTO = new BusinessLeaderDTO();
        BusinessEmployeeDTO businessEmployeeDTO = new BusinessEmployeeDTO();
        businessEmployeeDTO.setId(5l);

        businessLeaderDTO.setEmployee(businessEmployeeDTO);
        projectCreationDTO.setBusinessLeader(businessLeaderDTO);

        BusinessUnitDTO businessUnitDTOFirst = new BusinessUnitDTO();
        businessUnitDTOFirst.setId(1l);

        projectCreationDTO.addBusinessUnit(businessUnitDTOFirst);

        BusinessUnitDTO businessUnitDTOSecond = new BusinessUnitDTO();
        businessUnitDTOSecond.setId(2l);

        projectCreationDTO.addBusinessUnit(businessUnitDTOSecond);

        return  mapper.writeValueAsString(projectCreationDTO);
    }

    private String prepareResourceManagerSearchProjectDto() throws JsonProcessingException {
        ResourceManagerSearchProjectDTO resourceManagerSearchProjectDTO = new ResourceManagerSearchProjectDTO();
        resourceManagerSearchProjectDTO.setProjectId(1l);
        resourceManagerSearchProjectDTO.setProjectClassDTOList(Collections.singletonList(ProjectClassDTO.I));
        resourceManagerSearchProjectDTO.setProjectStatusDTOList(Collections.singletonList(ProjectStatusDTO.ANALYSIS));

        return mapper.writeValueAsString(resourceManagerSearchProjectDTO);
    }

    private void loadProjectManager(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        ProjectManager projectManager = new ProjectManager();
        projectManager.setEmployee((IProjectManager) employeeService.find(4l));
        entityManager.persist(projectManager);
        transaction.commit();
    }

}