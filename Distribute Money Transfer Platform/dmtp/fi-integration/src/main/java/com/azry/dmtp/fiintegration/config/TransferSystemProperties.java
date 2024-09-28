package com.azry.dmtp.fiintegration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mts")
@EnableConfigurationProperties(TransferSystemProperties.class)
public class TransferSystemProperties {

    private MoneyTransferSystem moneygram;
    private MoneyTransferSystem westernUnion;

    @Data
    public static class MoneyTransferSystem {
        private CurrencyAccounts currency;
    }

    @Data
    public static class CurrencyAccounts {
        private AccountNumber gel;
        private AccountNumber usd;
        private AccountNumber eur;
    }

    @Data
    public static class AccountNumber {
        private String accountNumber;
    }
}