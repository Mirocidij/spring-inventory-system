package com.gh.mirocodij.inventory.system.springinventorysystem.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gh.mirocodij.inventory.system.springinventorysystem.fixtures.BaseIntegrationTest;
import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModelDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class DemoControllerTest extends BaseIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper mapper;

    public DemoControllerTest() {
        super("/demo");
    }

    @BeforeEach
    public void beforeEach() throws Exception {
        for (int i = 0; i < 10; i++) {
            DemoModelDto demoModelDto = new DemoModelDto();
            demoModelDto.setDemoField("field" + i);
            sendPost(demoModelDto);
        }
    }

    @Test
    public void postMethodShouldSavedModelAndReturnIt() throws Exception {
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

    @Test
    public void postMethodShouldHaveBadRequestWhenTrySavedModel() throws Exception {
        DemoModelDto savedDtoModel = new DemoModelDto();
        savedDtoModel.setDemoField("value which will repeat");
        sendPost(savedDtoModel);
        DemoModelDto notSavedDtoModel = new DemoModelDto();
        notSavedDtoModel.setDemoField("value which will repeat");
        var response = mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(notSavedDtoModel)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();
    }

    @Test
    public void getMethodShouldReturnAllModels() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andReturn().getResponse();
        var demoModelsFromResponse = mapper.readValue(response.getContentAsString(),
                new TypeReference<List<DemoModelDto>>() {});
        for (int i = 0; i < 10; i++) {
            var demoModel = demoModelsFromResponse.get(i);
            assertThat(demoModel).isNotNull();
            assertThat(demoModel.getDemoField()).isEqualTo("field" + i);
            assertThat(demoModel.getId()).isNotNull();
        }
    }

    @Test
    public void getMethodShouldReturnModelById() throws Exception {
        DemoModelDto demoModelDto = new DemoModelDto();
        demoModelDto.setDemoField("test field by id");
        var postResponse = mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(demoModelDto)))
                .andReturn().getResponse();
        var demoModelFromPostResponse = mapper.readValue(postResponse.getContentAsString(), DemoModelDto.class);
        var id = demoModelFromPostResponse.getId();
        var getResponse = mockMvc.perform(get(baseUrl + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andReturn().getResponse();
        var demoModelFromGetResponse = mapper.readValue(getResponse.getContentAsString(), DemoModelDto.class);
        assertThat(id).isNotNull();
        assertThat(demoModelFromPostResponse.getDemoField()).isNotNull();
        assertThat(demoModelFromGetResponse.getDemoField()).isNotNull();
        assertThat(demoModelFromGetResponse).isNotNull();
        assertThat(demoModelFromGetResponse.getId()).isNotNull();
        assertThat(demoModelFromGetResponse.getId()).isEqualTo(id);
        assertThat(demoModelFromGetResponse.getDemoField()).isEqualTo(demoModelFromPostResponse.getDemoField());
    }

    @Test
    public void getMethodShouldHaveNotFoundStatusIfIdNotExist() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(get(baseUrl + "/" + uuid))
                .andExpect(status().isNotFound());
    }

    @Test


    private void sendPost(DemoModelDto demoModelDto) throws Exception {
        mockMvc.perform(post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(demoModelDto)));
    }
}
