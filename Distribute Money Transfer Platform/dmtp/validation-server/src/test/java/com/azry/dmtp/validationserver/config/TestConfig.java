package com.azry.dmtp.validationserver.config;

import com.azry.dmtp.validationserver.messaging.ValidationServerProducer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootTest
public class TestConfig {

    @MockBean
    public ValidationServerProducer validationServerProducer;

}
