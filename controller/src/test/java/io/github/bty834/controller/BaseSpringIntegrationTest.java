package io.github.bty834.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {}
)
public class BaseSpringIntegrationTest {


    @LocalServerPort
    protected int serverPort;

    @Test
    public void test() {

    }
}
