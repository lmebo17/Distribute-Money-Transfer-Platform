package com.azry.dmtp.fiintegration.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "fi-integration")
@EnableConfigurationProperties(BankServiceProperties.class)
public class BankServiceProperties {

    @Value("${bank.service.url}")
    private String bankServiceUrl;

    @Value("${bank.service.username}")
    private String bankServiceUsername;

    @Value("${bank.service.password}")
    private String bankServicePassword;

    @Value("${bank.service.bank-transactions-retry-count}")
    private int bankServiceRetryCount;


}
