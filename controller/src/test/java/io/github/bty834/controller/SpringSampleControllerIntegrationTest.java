package io.github.bty834.controller;


import io.github.bty834.controller.api.SampleController;
import io.github.bty834.domain.service.SampleService;
import javax.sql.DataSource;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class SpringSampleControllerIntegrationTest extends BaseSpringControllerIntegrationTest {


    @Autowired
    private SampleController sampleController;

    @Autowired
    private DataSource dataSource;
    @Test
    public void testSample(){
        System.out.println("port : "+serverPort);
        Assert.assertEquals(sampleController.getSamples("1").getStatusCodeValue(),200);
    }


}
