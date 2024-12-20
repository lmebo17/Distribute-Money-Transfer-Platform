package com.azry.dmtp.validationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ValidationServerApplication extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(ValidationServerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(ValidationServerApplication.class);
    }
}
