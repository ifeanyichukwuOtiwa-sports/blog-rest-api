package io.regent.blogrestapi.config;

import javax.validation.constraints.NotNull;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 16/10/2022
 */

@Configuration
@Testcontainers
@SuppressWarnings("resource")
public class TestConfig {

    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>(
            DockerImageName.parse("mysql:8.0.30-oracle")
    ).withDatabaseName("regent_blog")
            .withUsername("root")
            .withPassword("123456")
            .withExposedPorts(3306);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        @SuppressWarnings("all")
        public void initialize(final ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + MY_SQL_CONTAINER.getJdbcUrl(),
                            "spring.datasource.username=" + MY_SQL_CONTAINER.getUsername(),
                            "spring.datasource.password=" + MY_SQL_CONTAINER.getPassword()
            );

        }
    }
}
