package io.github.bty834.controller;


import com.alibaba.fastjson.JSON;
import io.github.bty834.controller.api.SampleController;
import io.github.bty834.domain.service.SampleService;
import javax.sql.DataSource;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;


public class SpringSampleControllerIntegrationTest extends BaseSpringControllerIntegrationTest {


    @Autowired
    private SampleController sampleController;

    @Autowired
    private DataSource dataSource;
    @Test
    public void testSample() throws JSONException {
        System.out.println("port : "+serverPort);

        JSONAssert.assertEquals(JSON.toJSONString(sampleController.getSamples("1").getBody()),"[{\"id\":1,\"username\":\"bty\"}]",true);
    }


}
