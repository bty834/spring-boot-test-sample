package io.github.bty834.controller;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {}
)
public class BaseSpringControllerIntegrationTest {

    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest");

    static {
        mysqlContainer
            .withExposedPorts(3306)
            .withUsername("root")
            .withPassword("root")
            .withDatabaseName("test")
            .withUrlParam("useSSL", "false")
            .withUrlParam("serverTimezone", "Asia/Shanghai");
    }

    @DynamicPropertySource
    public static void setupThings(DynamicPropertyRegistry registry) {
        Startables.deepStart(mysqlContainer).join();

        registry.add("ds.jdbcUrl", () -> mysqlContainer.getJdbcUrl());
        registry.add("ds.username", () -> mysqlContainer.getUsername());
        registry.add("ds.password", () -> mysqlContainer.getPassword());
    }


    @LocalServerPort
    protected int serverPort;

}
