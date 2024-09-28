package com.azry.dmtp.z.controller;

import com.azry.dmtp.z.controller.helper.AccountHelper;
import com.azry.dmtp.z.controller.helper.ClientHelper;
import com.azry.dmtp.z.model.api.AccountCreationRequest;
import com.azry.dmtp.z.model.api.PagingRequest;
import com.azry.dmtp.z.model.api.dto.AccountDTO;
import com.azry.dmtp.z.model.api.filter.AccountFilter;
import com.azry.dmtp.z.model.entity.Account;
import com.azry.dmtp.z.model.entity.Client;
import com.azry.dmtp.z.model.enums.Status;
import com.azry.dmtp.z.repository.AccountRepository;
import com.azry.dmtp.z.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "test")
public class TestAccountController {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    public void successTestAddAccount() throws Exception {

        Client client = ClientHelper.client();

        clientRepository.save(client);

        AccountCreationRequest accCreationReq = AccountCreationRequest.builder()
                .clientId(client.getClientId())
                .balance(BigDecimal.valueOf(100))
                .currency("GEL")
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accCreationReq)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.balance", is(100)))
                .andExpect(jsonPath("$.currency", is("GEL")))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        AccountDTO accountDTO = objectMapper.readValue(responseContent, AccountDTO.class);

        assertEquals(accCreationReq.getClientId(), accountDTO.getClientId());
        assertEquals(accCreationReq.getBalance(), accountDTO.getBalance());
        assertEquals(accCreationReq.getCurrency(), accountDTO.getCurrency());
    }

    @Test
    public void successTestGetAccount() throws Exception {

        Client client = ClientHelper.client();
        Account account = AccountHelper.account2();
        account.setClient(client);

        clientRepository.save(client);
        accountRepository.save(account);

        String accountNumber = "1122334455";
        mockMvc.perform(get("/api/v1/accounts/1122334455")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountNumber)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.accountNumber", is("1122334455")))
                .andExpect(jsonPath("$.balance", is(333)))
                .andExpect(jsonPath("$.currency", is("GEL")));
    }

    @Test
    public void successTestCheckBalance() throws Exception {
        Client client = ClientHelper.client();
        Account account = AccountHelper.account();
        account.setClient(client);
        clientRepository.save(client);
        accountRepository.save(account);

        String accountNumber = "123456789";

        mockMvc.perform(get("/api/v1/accounts/123456789/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountNumber)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.balance", is(222)))
                .andExpect(jsonPath("$.currency", is("GEL")));
    }

    @Test
    public void successTestDeposit() throws Exception {
        Client client = ClientHelper.client();
        clientRepository.save(client);
        Account account = AccountHelper.account();
        account.setClient(client);
        accountRepository.save(account);

        BigDecimal depositAmount = BigDecimal.valueOf(111);

        mockMvc.perform(put("/api/v1/accounts/123456789/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("amountOfMoney", depositAmount.toString()))

                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.balance", is(333)))
                .andExpect(jsonPath("$.currency", is("GEL")));
    }

    @Test
    public void successTestWithdraw() throws Exception {
        Client client = ClientHelper.client();
        clientRepository.save(client);
        Account account = AccountHelper.account();
        account.setClient(client);
        accountRepository.save(account);

        BigDecimal withdrawAmount = BigDecimal.valueOf(111);
        mockMvc.perform(put("/api/v1/accounts/123456789/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("amountOfMoney", withdrawAmount.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(BigDecimal.valueOf(111)))
                .andExpect(jsonPath("$.currency").value("GEL"));
    }

    @Test
    public void successTestDeactivate() throws Exception {
        Client client = ClientHelper.client();
        clientRepository.save(client);
        Account account = AccountHelper.account2();
        account.setClient(client);
        accountRepository.save(account);

        String accountNumber = "1122334455";

        MvcResult result = mockMvc.perform(put("/api/v1/accounts/deactivate/1122334455")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountNumber))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        AccountDTO accountDTO = objectMapper.readValue(responseContent, AccountDTO.class);

        assertEquals(account.getAccountNumber(), accountDTO.getAccountNumber());
        assertNotEquals(account.getStatus(), accountDTO.getAccountStatus());
    }

    @Test
    public void successTestSearch() throws Exception {

        Client client = ClientHelper.client();
        clientRepository.save(client);
        Account account = AccountHelper.account();
        account.setClient(client);
        accountRepository.save(account);

        AccountFilter accountFilter = new AccountFilter();
        accountFilter.setStatus(Status.ACTIVE);
        accountFilter.setClientId(client.getClientId());
        accountFilter.setCurrency("GEL");

        PagingRequest pagingRequest = AccountHelper.pagingRequest();

        mockMvc.perform(get("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("status", accountFilter.getStatus().toString())
                        .param("clientId", accountFilter.getClientId().toString())
                        .param("currency", accountFilter.getCurrency())
                        .param("page", String.valueOf(pagingRequest.getPage()))
                        .param("size", String.valueOf(pagingRequest.getSize()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].balance").value(BigDecimal.valueOf(222)))
                .andExpect(jsonPath("$.content[0].currency").value("GEL"))
                .andExpect(jsonPath("$.content[0].accountStatus").value(Status.ACTIVE.toString()))
                .andExpect(jsonPath("$.content.length()").value(1));
    }
}
