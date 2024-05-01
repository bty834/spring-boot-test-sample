package io.github.bty834.controller;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;


public class MysqlContainerSpringTest extends BaseSpringIntegrationTest {

    // install docker locally in your machine first
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest")
                                                  .withReuse(true)
                                                  .withCopyFileToContainer(MountableFile.forClasspathResource("/schema.sql"), "/docker-entrypoint-initdb.d/")
                                                  .withExposedPorts(3306)
                                                  .withUsername("root")
                                                  .withPassword("root")
                                                  .withDatabaseName("test")
                                                  .withUrlParam("useSSL", "false")
                                                  .withUrlParam("serverTimezone", "Asia/Shanghai");

    @DynamicPropertySource
    public static void setupDsProperties(DynamicPropertyRegistry registry) {
        Startables.deepStart(mysqlContainer).join();


        registry.add("ds.jdbcUrl", () -> mysqlContainer.getJdbcUrl());
        registry.add("ds.username", () -> mysqlContainer.getUsername());
        registry.add("ds.password", () -> mysqlContainer.getPassword());
    }


}
