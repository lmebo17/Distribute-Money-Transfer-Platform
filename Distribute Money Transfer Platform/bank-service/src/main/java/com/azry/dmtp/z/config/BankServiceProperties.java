package com.azry.dmtp.z.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "bank-service")
public class BankServiceProperties {

    private List<String> currencies;
    private List<String> blacklistedCountries;

    private Integer transactionSuccessPercentage;
    private String transactionSchedulingFixedRate;
    private String transactionSchedulingInitialDelay;
}
