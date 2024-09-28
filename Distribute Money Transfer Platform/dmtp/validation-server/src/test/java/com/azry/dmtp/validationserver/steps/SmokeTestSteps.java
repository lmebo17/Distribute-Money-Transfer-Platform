package com.azry.dmtp.validationserver.steps;

import com.azry.dmtp.validationserver.SpringIntegrationTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmokeTestSteps extends SpringIntegrationTest {

    @Given("a test")
    public void aTest() {
        log.info("a test");
    }

    @When("a test is called")
    public void aTestIsCalled() {
        log.info("a test is called");
    }


    @Then("we get a test called")
    public void weGetATestCalled() {
        log.info("we got a test called");
    }

}

