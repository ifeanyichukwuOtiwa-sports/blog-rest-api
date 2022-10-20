package io.regent.blogrestapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 19/10/2022
 */
@Configuration
public class BlogConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
