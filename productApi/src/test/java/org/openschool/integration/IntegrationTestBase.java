package org.openschool.integration;

import org.junit.jupiter.api.BeforeAll;
import org.openschool.ProductApplication;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;


@ActiveProfiles("test")
@Transactional
@Rollback
@SpringBootTest(classes = {ProductApplication.class})

public abstract class IntegrationTestBase {
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1");

    @BeforeAll
    static void runContainer(){
        container.start();
    }
    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);

    }

}
