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
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * https://stackoverflow.com/questions/46343782/whats-the-difference-between-autoconfigurewebmvc-and-autoconfiguremockmvc
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
@ContextConfiguration(classes = ControllerSliceTest.SampleControllerSliceTestContext.class)
public class ControllerSliceTest {

    @Configuration
    @Import(SampleController.class)
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
                            .get("/sample/test")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .param("id", String.valueOf(1)))
            .andExpect(MockMvcResultMatchers.status().isOk());

    }


}
