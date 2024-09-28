package com.azry.dmtp.z.controller;

import com.azry.dmtp.z.model.api.dto.ClientDTO;
import com.azry.dmtp.z.model.entity.Client;
import com.azry.dmtp.z.model.enums.Status;
import com.azry.dmtp.z.repository.ClientRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "test")
public class TestClientController {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ClientRepository clientRepository;

    private ClientDTO initializeClientDTO() {
        return ClientDTO.builder()
                .personalNumber(19001111111L)
                .firstName("Luka")
                .lastName("Mebonia")
                .email("mebonia6@gmail.com")
                .phone("599582879")
                .address("Tbilisi")
                .citizenship("Georgia")
                .clientStatus(Status.ACTIVE)
                .dateOfBirth(LocalDate.of(2003, 10, 17))
                .accountDTOList(new ArrayList<>()).build();
    }

    @BeforeEach
    public void init() {
        clientRepository.deleteAll();
    }


    @Test
    public void testCreateClient() throws Exception {
        ClientDTO client = initializeClientDTO();

        mockMvc.perform(post("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.personalNumber").value(19001111111L))
                .andExpect(jsonPath("$.firstName").value("Luka"))
                .andExpect(jsonPath("$.lastName").value("Mebonia"))
                .andExpect(jsonPath("$.email").value("mebonia6@gmail.com"))
                .andExpect(jsonPath("$.phone").value("599582879"))
                .andExpect(jsonPath("$.address").value("Tbilisi"))
                .andExpect(jsonPath("$.citizenship").value("Georgia"))
                .andExpect(jsonPath("$.clientStatus").value("ACTIVE"))
                .andExpect(jsonPath("$.dateOfBirth[0]").value(2003))
                .andExpect(jsonPath("$.dateOfBirth[1]").value(10))
                .andExpect(jsonPath("$.dateOfBirth[2]").value(17));
    }

    @Test
    public void testCreateClientWithInvalidEmail() throws Exception {
        ClientDTO client = initializeClientDTO();
        client.setEmail("invalidEmail");

        mockMvc.perform(post("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateClientWithSamePersonalNumber() throws Exception {
        ClientDTO client = initializeClientDTO();

        mockMvc.perform(post("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated());


        mockMvc.perform(post("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetClient() throws Exception {
        ClientDTO client = initializeClientDTO();

        mockMvc.perform(post("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated());

        Client clientEntity = clientRepository.findByFirstName("Luka");
        long id = clientEntity.getClientId();

        mockMvc.perform(get("/api/v1/client/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personalNumber").value(19001111111L))
                .andExpect(jsonPath("$.firstName").value("Luka"))
                .andExpect(jsonPath("$.lastName").value("Mebonia"))
                .andExpect(jsonPath("$.email").value("mebonia6@gmail.com"))
                .andExpect(jsonPath("$.phone").value("599582879"))
                .andExpect(jsonPath("$.address").value("Tbilisi"))
                .andExpect(jsonPath("$.citizenship").value("Georgia"))
                .andExpect(jsonPath("$.clientStatus").value("ACTIVE"))
                .andExpect(jsonPath("$.dateOfBirth[0]").value(2003))
                .andExpect(jsonPath("$.dateOfBirth[1]").value(10))
                .andExpect(jsonPath("$.dateOfBirth[2]").value(17));
    }

    @Test
    public void testGetClientWithInvalidId() throws Exception {
        mockMvc.perform(get("/api/v1/client/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateClient() throws Exception {
        ClientDTO client = initializeClientDTO();

        mockMvc.perform(post("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated());

        client.setFirstName("Giorgi");
        client.setLastName("Mebonia");

        Client clientEntity = clientRepository.findByFirstName("Luka");
        long id = clientEntity.getClientId();

        mockMvc.perform(put("/api/v1/client/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/client/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personalNumber").value(19001111111L))
                .andExpect(jsonPath("$.firstName").value("Giorgi"))
                .andExpect(jsonPath("$.lastName").value("Mebonia"))
                .andExpect(jsonPath("$.email").value("mebonia6@gmail.com"))
                .andExpect(jsonPath("$.phone").value("599582879"))
                .andExpect(jsonPath("$.address").value("Tbilisi"))
                .andExpect(jsonPath("$.citizenship").value("Georgia"))
                .andExpect(jsonPath("$.clientStatus").value("ACTIVE"))
                .andExpect(jsonPath("$.dateOfBirth[0]").value(2003))
                .andExpect(jsonPath("$.dateOfBirth[1]").value(10))
                .andExpect(jsonPath("$.dateOfBirth[2]").value(17));
    }

    @Test
    public void testUpdateClientWithInvalidId() throws Exception {
        ClientDTO client = initializeClientDTO();

        mockMvc.perform(put("/api/v1/client/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeactivateClient() throws Exception {
        ClientDTO client = initializeClientDTO();

        mockMvc.perform(post("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated());

        Client clientEntity = clientRepository.findByFirstName("Luka");
        long id = clientEntity.getClientId();

        mockMvc.perform(put("/api/v1/client/deactivate/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/client/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearch() throws Exception {
        ClientDTO client = initializeClientDTO();

        mockMvc.perform(post("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated());


        mockMvc.perform(get("/api/v1/client/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].firstName").value("Luka"));
    }

    @Test
    public void testSearchNotFound() throws Exception {
        ClientDTO client = initializeClientDTO();

        mockMvc.perform(post("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/client/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10")
                        .param("firstName", "Giorgi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));

    }

}
