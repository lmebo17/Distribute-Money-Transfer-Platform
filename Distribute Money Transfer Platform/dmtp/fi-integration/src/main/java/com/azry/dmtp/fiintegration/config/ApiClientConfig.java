package com.azry.dmtp.fiintegration.config;

import com.azry.dmtp.z.bankservice.ApiClient;
import com.azry.dmtp.z.bankservice.api.AccountsApi;
import com.azry.dmtp.z.bankservice.api.ClientsApi;
import com.azry.dmtp.z.bankservice.api.TransactionsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApiClientConfig {

    private final BankServiceProperties bankServiceProperties;

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(bankServiceProperties.getBankServiceUrl());
        apiClient.setUsername(bankServiceProperties.getBankServiceUsername());
        apiClient.setPassword(bankServiceProperties.getBankServicePassword());
        return apiClient;
    }

    @Bean
    public ClientsApi ClientsApi(ApiClient apiClient) {
        return new ClientsApi(apiClient);
    }

    @Bean
    public TransactionsApi TransactionsApi(ApiClient apiClient) {
        return new TransactionsApi(apiClient);
    }

    @Bean
    public AccountsApi AccountsApi(ApiClient apiClient) {
        return new AccountsApi(apiClient);
    }

}
