package com.azry.dmtp.z.mappers;

import com.azry.dmtp.z.model.api.ClientCreationRequest;
import com.azry.dmtp.z.model.api.ClientUpdateRequest;
import com.azry.dmtp.z.model.api.dto.ClientDTO;
import com.azry.dmtp.z.model.entity.Client;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ClientMapper {

    private final AccountMapper accountMapper;


    public ClientDTO toModel(Client clientEntity) {
        if (clientEntity == null) {
            return null;
        }
        return ClientDTO.builder()
                .id(clientEntity.getClientId())
                .personalNumber(clientEntity.getPersonalNumber())
                .firstName(clientEntity.getFirstName())
                .lastName(clientEntity.getLastName())
                .email(clientEntity.getEmail())
                .phone(clientEntity.getPhone())
                .address(clientEntity.getAddress())
                .citizenship(clientEntity.getCitizenship())
                .clientStatus(clientEntity.getClientStatus())
                .dateOfBirth(clientEntity.getDateOfBirth())
                .accountDTOList(accountMapper.accountsToDTO(clientEntity.getListOfAccounts()))
                .build();
    }

    public Client clientRequestToClient(ClientCreationRequest clientCreationRequest) {
        if (clientCreationRequest == null) {
            return null;
        }
        return Client.builder()
                .firstName(clientCreationRequest.getFirstName())
                .lastName(clientCreationRequest.getLastName())
                .personalNumber(clientCreationRequest.getPersonalNumber())
                .email(clientCreationRequest.getEmail())
                .phone(clientCreationRequest.getPhone())
                .address(clientCreationRequest.getAddress())
                .citizenship(clientCreationRequest.getCitizenship())
                .clientStatus(clientCreationRequest.getClientStatus())
                .dateOfBirth(clientCreationRequest.getDateOfBirth())
                .listOfAccounts(new ArrayList<>())
                .build();
    }

    public Client clientUpdateRequestToClient(ClientUpdateRequest clientRequest) {
        if (clientRequest == null) {
            return null;
        }
        return Client.builder()
                .firstName(clientRequest.getFirstName())
                .lastName(clientRequest.getLastName())
                .email(clientRequest.getEmail())
                .phone(clientRequest.getPhone())
                .address(clientRequest.getAddress())
                .citizenship(clientRequest.getCitizenship())
                .build();
    }
}