package io.regent.blogrestapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import io.regent.blogrestapi.config.BlogConfig;
import io.regent.blogrestapi.config.TestConfig;

@SpringBootTest
@ContextConfiguration(
        classes = {
                BlogRestApiApplication.class,
                BlogConfig.class
        },
        initializers = {
                TestConfig.Initializer.class
        }
)
class BlogRestApiApplicationTests {

    @Autowired
    protected BlogRestApiApplication application;

    @Test
    void contextLoads() {
        assertThat(application).isNotNull();
    }

}
