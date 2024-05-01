package io.github.bty834.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class SampleControllerE2ETest extends MysqlContainerSpringTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSample() throws Exception {
        this.mockMvc
            .perform(MockMvcRequestBuilders
                         .get("/sample")
                         .param("id","1"))
            .andExpect(status().isOk())
            .andExpect(content().json("[{\"id\":1,\"username\":\"bty\"}]"))
            .andReturn();
    }


}
