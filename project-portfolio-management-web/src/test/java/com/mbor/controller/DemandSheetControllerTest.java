package com.mbor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbor.model.creation.DemandSheetCreationDTO;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfiguration.class, ServiceConfiguration.class})
@TestPropertySource(locations = "classpath:security-client-test.properties")
@ActiveProfiles(profiles = {"test", "controller-integration"})
class DemandSheetControllerTest {

    private static MockMvc mockMvc;

    @Autowired
    private Environment env;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void init(@Autowired WebApplicationContext webApplicationContext, @Autowired FilterChainProxy springSecurityFilterChain) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    @Test
    void createDemandSheetThenSuccess() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.businessemployee.name"), env.getProperty("user.businessemployee.password"));

        mockMvc.perform(post("/demandsheets")
                .header("Authorization", "Bearer " + accessToken)
                .content(prepareDemandSheetCreationDTO("ProjectName", "ProjectDescription", 2L))
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isCreated());
    }

    @Test
    void getAllDemandSheetsOfBrmWithNoProjectThenSuccess() throws Exception {
        String accessToken = obtainAccessToken(env.getProperty("user.brm.name"), env.getProperty("user.brm.password"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("searchingDemandSheets", "ofBrmNoProject");

        mockMvc.perform(get("/demandsheets")
                .header("Authorization", "Bearer " + accessToken)
                .params(params)
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
        ).andExpect(status().isOk());
    }



    @Test
    public void obtainAccessTokenThenSuccess() throws Exception {
        Assertions.assertNotNull(obtainAccessToken(env.getProperty("user.businessemployee.name"), env.getProperty("user.businessemployee.password")));
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

    private String prepareDemandSheetCreationDTO(String projectName, String projectDescription, Long businessUnitId) throws JsonProcessingException {
        DemandSheetCreationDTO demandSheetCreationDTO = new DemandSheetCreationDTO();
        demandSheetCreationDTO.setProjectName(projectName);
        demandSheetCreationDTO.setDescription(projectDescription);
        demandSheetCreationDTO.setBusinessUnitId(businessUnitId);
        return mapper.writeValueAsString(demandSheetCreationDTO);
    }
}