package io.github.bty834.controller;

import io.github.bty834.controller.api.SampleController;
import io.github.bty834.domain.service.SampleService;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SampleController.class)
// 如果用org.junit.Test注解，则需要添加该RunWith
// @RunWith(SpringRunner.class)
class ControllerSlicingTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SampleService sampleService;

    // 这里是 org.junit.jupiter.api.Test
    @Test
    void shouldReturnEmptyList() throws Exception {

        Mockito
            .doNothing()
            .when(sampleService)
            .doSth();

        Mockito
            .when(sampleService.listSamples(Mockito.any()))
            .thenReturn(new ArrayList<>());

        mockMvc
            .perform(MockMvcRequestBuilders
                         .get("/sample")
                         .param("id", String.valueOf(1)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()").value(0));

    }

}
