package com.mbor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbor.configuration.WebApplicationSecurityConfig;
import com.mbor.dataloader.TestDataLoader;
import com.mbor.model.BusinessEmployeeDTO;
import com.mbor.model.BusinessLeaderDTO;
import com.mbor.model.BusinessRelationManagerDTO;
import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.creation.ProjectCreationDTO;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfiguration.class, ServiceConfiguration.class, WebApplicationSecurityConfig.class})
@TestPropertySource(locations = "classpath:security-client-test.properties")
@ActiveProfiles(profiles = {"test", "controller-integration"})
class ProjectControllerTest {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TestDataLoader testDataLoader;

    @Autowired
    private  Environment env;

    private static MockMvc mockMvc;

    @BeforeAll
    static void init(@Autowired WebApplicationContext webApplicationContext,   @Autowired FilterChainProxy springSecurityFilterChain ){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void obtainAccessTokenThenSuccess() throws Exception {
        Assertions.assertNotNull(obtainAccessToken(env.getProperty("user.name"), env.getProperty("user.password")));
    }

    @Test
    void save() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.name"), env.getProperty("user.password"));
        mockMvc.perform(post("/projects")
                .header("Authorization", "Bearer " + accessToken)
                .content(prepareProjectCreationDto())
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isCreated());
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

    private String prepareProjectCreationDto() throws JsonProcessingException {
        Random random = new Random();
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

}