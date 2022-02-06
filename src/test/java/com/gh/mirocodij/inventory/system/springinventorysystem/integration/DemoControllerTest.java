package com.gh.mirocodij.inventory.system.springinventorysystem.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gh.mirocodij.inventory.system.springinventorysystem.fixtures.BaseIntegrationTest;
import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModelDto;
import com.gh.mirocodij.inventory.system.springinventorysystem.repository.DemoModelRepository;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class DemoControllerTest extends BaseIntegrationTest {

    @Autowired
    public DemoModelRepository demoModelRepository;

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper mapper;

    public DemoControllerTest() {
        super("/demo");
    }

    @RepeatedTest(3)
    public void getMethodShouldReturnAllTheDemoModels() throws Exception {
        DemoModelDto demoModelDto = new DemoModelDto();
        demoModelDto.setDemoField("another value");
        MockHttpServletResponse response = mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(demoModelDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andReturn().getResponse();

        var demoModelFromResponse = mapper.readValue(response.getContentAsString(), DemoModelDto.class);
        assertThat(demoModelFromResponse).isNotNull();
        assertThat(demoModelFromResponse.getDemoField()).isEqualTo("another value");
        assertThat(demoModelFromResponse.getId()).isNotNull();
    }
}
