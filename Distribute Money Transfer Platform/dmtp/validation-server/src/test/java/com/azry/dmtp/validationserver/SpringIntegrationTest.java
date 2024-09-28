package com.azry.dmtp.validationserver;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


@SpringBootTest(classes = ValidationServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@ContextConfiguration
@CucumberContextConfiguration
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class
})
@ActiveProfiles("test")
public abstract class SpringIntegrationTest {


}