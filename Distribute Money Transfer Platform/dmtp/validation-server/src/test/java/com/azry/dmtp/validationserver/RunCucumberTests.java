package com.azry.dmtp.validationserver;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        plugin = {"pretty", "html:build/reports/cucumber/cucumber-reports/Cucumber.html"}
)
@ActiveProfiles("test")
public class RunCucumberTests {


}
