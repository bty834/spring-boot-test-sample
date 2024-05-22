package io.github.bty834.controller;

import io.github.bty834.controller.api.SampleController;
import io.github.bty834.controller.converter.SampleConverter;
import io.github.bty834.domain.service.SampleService;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
@ContextConfiguration(classes = ControllerSliceTest.SampleControllerSliceTestContext.class)
public class ControllerSliceTest {


    @ComponentScan(basePackages = "io.github.bty834.controller.api",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SampleController.class})})
    static class SampleControllerSliceTestContext {

    }

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SampleService sampleService;
    @SpyBean
    private SampleConverter sampleConverter;

    @Autowired
    ApplicationContext ac;

    @Test
    public void shouldReturnSuccess() throws Exception {
        Mockito.when(sampleService.listSamples(Mockito.any())).thenReturn(Collections.emptyList());


        mockMvc.perform(MockMvcRequestBuilders
                            .post("/sample/test")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content("{}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.content().json("{\"body\":[]}"));

    }


}
