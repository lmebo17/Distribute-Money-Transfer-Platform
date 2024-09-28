package com.azry.dmtp.z.controller;

import com.azry.dmtp.z.model.api.TransactionCreationRequest;
import com.azry.dmtp.z.model.api.dto.TransactionDTO;
import com.azry.dmtp.z.model.entity.Account;
import com.azry.dmtp.z.model.entity.Client;
import com.azry.dmtp.z.model.enums.Status;
import com.azry.dmtp.z.model.enums.TransactionStatus;
import com.azry.dmtp.z.repository.AccountRepository;
import com.azry.dmtp.z.repository.ClientRepository;
import com.azry.dmtp.z.repository.TransactionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "test")
public class TestTransactionController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void cleanTables() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    public void testCreateSuccessfulTransaction() throws Exception {
        Client newClient = createClient();
        Account senderAccount = createAccount("GBP7373970234", "GBP", 500, newClient);
        Account receiverAccount = createAccount("GBP3257511157", "GBP", 900, newClient);

        TransactionCreationRequest transactionCreationRequest = TransactionCreationRequest.builder()
                .receiverAccountNumber(receiverAccount.getAccountNumber())
                .senderAccountNumber(senderAccount.getAccountNumber())
                .amount(new BigDecimal(100))
                .currency("GBP")
                .build();

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .receiverAccountNumber(receiverAccount.getAccountNumber())
                .senderAccountNumber(senderAccount.getAccountNumber())
                .amount(new BigDecimal(100))
                .creationDate(LocalDateTime.now())
                .status(TransactionStatus.CREATED)
                .currency("GBP")
                .build();

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionCreationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.receiverAccountNumber", is(transactionDTO.getReceiverAccountNumber())))
                .andExpect(jsonPath("$.senderAccountNumber", is(transactionDTO.getSenderAccountNumber())))
                .andExpect(jsonPath("$.amount").value(transactionDTO.getAmount()))
                .andExpect(jsonPath("$.currency", is(transactionDTO.getCurrency())))
                .andExpect(jsonPath("$.status", is(transactionDTO.getStatus().toString())));
    }

    @Test
    public void testCreateTransactionUnsupportedCurrency() throws Exception {
        Client newClient = createClient();
        Account senderAccount = createAccount("GBP7373970234", "GBP", 500, newClient);
        Account receiverAccount = createAccount("GBP3257511157", "GBP", 900, newClient);

        TransactionCreationRequest transactionCreationRequest = TransactionCreationRequest.builder()
                .receiverAccountNumber(receiverAccount.getAccountNumber())
                .senderAccountNumber(senderAccount.getAccountNumber())
                .amount(new BigDecimal(100))
                .currency("CAD")
                .build();

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionCreationRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetTransaction() throws Exception {
        Client newClient = createClient();
        Account senderAccount = createAccount("GBP7373970234", "GBP", 500, newClient);
        Account receiverAccount = createAccount("GBP3257511157", "GBP", 900, newClient);

        TransactionCreationRequest transactionCreationRequest = TransactionCreationRequest.builder()
                .receiverAccountNumber(receiverAccount.getAccountNumber())
                .senderAccountNumber(senderAccount.getAccountNumber())
                .amount(new BigDecimal(100))
                .currency("GBP")
                .build();

        ResultActions resultActions = mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionCreationRequest)))
                .andExpect(status().isCreated());

        MvcResult result = resultActions.andReturn();
        String responseJson = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseJson);
        Long transactionId = jsonNode.get("id").asLong();

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .receiverAccountNumber(receiverAccount.getAccountNumber())
                .senderAccountNumber(senderAccount.getAccountNumber())
                .amount(new BigDecimal(100))
                .creationDate(LocalDateTime.now())
                .status(TransactionStatus.CREATED)
                .currency("GBP")
                .build();

        mockMvc.perform(get("/api/v1/transactions/{id}", transactionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transactionId))
                .andExpect(jsonPath("$.receiverAccountNumber", is(transactionDTO.getReceiverAccountNumber())))
                .andExpect(jsonPath("$.senderAccountNumber", is(transactionDTO.getSenderAccountNumber())))
                .andExpect(jsonPath("$.amount").value(transactionDTO.getAmount()))
                .andExpect(jsonPath("$.currency", is(transactionDTO.getCurrency())))
                .andExpect(jsonPath("$.status", is(transactionDTO.getStatus().toString())));
    }

    @Test
    public void testGetNonexistentTransaction() throws Exception {
        long transactionId = 1L;
        mockMvc.perform(get("/api/v1/transactions/{id}", transactionId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchAll() throws Exception {
        Client newClient = createClient();
        Account senderAccount = createAccount("GBP7373970234", "GBP", 500, newClient);
        Account receiverAccount = createAccount("GBP3257511157", "GBP", 900, newClient);
        Account senderAccountGEL = createAccount("GEL2450139240", "GEL", 600, newClient);
        Account receiverAccountGEL = createAccount("GEL8561871592", "GEL", 300, newClient);


        TransactionCreationRequest transactionCreationRequestOne = TransactionCreationRequest.builder()
                .receiverAccountNumber(receiverAccount.getAccountNumber())
                .senderAccountNumber(senderAccount.getAccountNumber())
                .amount(new BigDecimal(100))
                .currency("GBP")
                .build();

        TransactionCreationRequest transactionCreationRequestTwo = TransactionCreationRequest.builder()
                .receiverAccountNumber(receiverAccount.getAccountNumber())
                .senderAccountNumber(senderAccount.getAccountNumber())
                .amount(new BigDecimal(200))
                .currency("GBP")
                .build();

        TransactionCreationRequest transactionCreationRequestThree = TransactionCreationRequest.builder()
                .receiverAccountNumber(receiverAccountGEL.getAccountNumber())
                .senderAccountNumber(senderAccountGEL.getAccountNumber())
                .amount(new BigDecimal(20))
                .currency("GEL")
                .build();

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionCreationRequestOne)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionCreationRequestTwo)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionCreationRequestThree)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/transactions/filter")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)));
    }

    @Test
    public void testSearchOneField() throws Exception {
        Client newClient = createClient();
        Account senderAccount = createAccount("GBP7373970234", "GBP", 500, newClient);
        Account receiverAccount = createAccount("GBP3257511157", "GBP", 900, newClient);
        Account senderAccountGEL = createAccount("GEL2450139240", "GEL", 600, newClient);
        Account receiverAccountGEL = createAccount("GEL8561871592", "GEL", 300, newClient);

        TransactionCreationRequest transactionCreationRequestOne = TransactionCreationRequest.builder()
                .receiverAccountNumber(receiverAccount.getAccountNumber())
                .senderAccountNumber(senderAccount.getAccountNumber())
                .amount(new BigDecimal(100))
                .currency("GBP")
                .build();

        TransactionCreationRequest transactionCreationRequestTwo = TransactionCreationRequest.builder()
                .receiverAccountNumber(receiverAccount.getAccountNumber())
                .senderAccountNumber(senderAccount.getAccountNumber())
                .amount(new BigDecimal(200))
                .currency("GBP")
                .build();

        TransactionCreationRequest transactionCreationRequestThree = TransactionCreationRequest.builder()
                .receiverAccountNumber(receiverAccountGEL.getAccountNumber())
                .senderAccountNumber(senderAccountGEL.getAccountNumber())
                .amount(new BigDecimal(20))
                .currency("GEL")
                .build();

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionCreationRequestOne)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionCreationRequestTwo)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionCreationRequestThree)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/transactions/filter")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .param("currency", "GEL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].currency", is("GEL")));
    }

    @Test
    public void testSearchMultipleFields() throws Exception {
        Client newClient = createClient();
        Account senderAccount = createAccount("GBP7373970234", "GBP", 500, newClient);
        Account receiverAccount = createAccount("GBP3257511157", "GBP", 900, newClient);
        Account senderAccountGEL = createAccount("GEL2450139240", "GEL", 600, newClient);
        Account receiverAccountGEL = createAccount("GEL8561871592", "GEL", 300, newClient);
        Account receiverAccountTwo = createAccount("GBP7799329430", "GBP", 900, newClient);

        TransactionCreationRequest transactionCreationRequestOne = TransactionCreationRequest.builder()
                .receiverAccountNumber(receiverAccount.getAccountNumber())
                .senderAccountNumber(senderAccount.getAccountNumber())
                .amount(new BigDecimal(100))
                .currency("GBP")
                .build();

        TransactionCreationRequest transactionCreationRequestTwo = TransactionCreationRequest.builder()
                .receiverAccountNumber(receiverAccountTwo.getAccountNumber())
                .senderAccountNumber(senderAccount.getAccountNumber())
                .amount(new BigDecimal(200))
                .currency("GBP")
                .build();

        TransactionCreationRequest transactionCreationRequestThree = TransactionCreationRequest.builder()
                .receiverAccountNumber(receiverAccountGEL.getAccountNumber())
                .senderAccountNumber(senderAccountGEL.getAccountNumber())
                .amount(new BigDecimal(20))
                .currency("GEL")
                .build();

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionCreationRequestOne)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionCreationRequestTwo)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionCreationRequestThree)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/transactions/filter")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .param("currency", "GBP")
                        .param("senderAccountNumber", "GBP7373970234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].currency", is("GBP")))
                .andExpect(jsonPath("$.content[1].currency", is("GBP")))
                .andExpect(jsonPath("$.content[0].senderAccountNumber", is("GBP7373970234")))
                .andExpect(jsonPath("$.content[1].senderAccountNumber", is("GBP7373970234")));
    }

    private Client createClient() {
        long personalNumber = 123456789L;
        LocalDate parsedDate = LocalDate.parse("2002-07-09");

        Client newClient = Client.builder()
                .firstName("Tamta")
                .lastName("Giorgadze")
                .personalNumber(personalNumber)
                .email("tamta.giorgadze@gmail.com")
                .phone("595959595")
                .address("Rustaveli str")
                .citizenship("Georgia")
                .clientStatus(Status.ACTIVE)
                .dateOfBirth(parsedDate)
                .listOfAccounts(new ArrayList<>())
                .build();

        clientRepository.save(newClient);
        return newClient;
    }

    private Account createAccount(String accountNumber, String currency, int balance, Client client) {
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .currency(currency)
                .status(Status.ACTIVE)
                .balance(new BigDecimal(balance))
                .client(client)
                .build();

        accountRepository.save(account);
        return account;
    }

}
