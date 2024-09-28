package com.azry.dmtp.z.service.implementation;


import com.azry.dmtp.z.config.handler.exception.ClientAlreadyExistException;
import com.azry.dmtp.z.config.handler.exception.InactiveClientException;
import com.azry.dmtp.z.config.handler.exception.NotFoundException;
import com.azry.dmtp.z.model.api.PagingRequest;
import com.azry.dmtp.z.model.api.filter.ClientFilter;
import com.azry.dmtp.z.model.entity.Account;
import com.azry.dmtp.z.model.entity.Client;
import com.azry.dmtp.z.model.enums.Status;
import com.azry.dmtp.z.repository.ClientRepository;
import com.azry.dmtp.z.service.AccountService;
import com.azry.dmtp.z.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final AccountService accountService;

    public Client getClient(Long id) {

        Client client = clientRepository.findByClientIdAndClientStatus(id, Status.ACTIVE).orElseThrow(() -> new NotFoundException("Client not found: id=" + id));
        log.info("Client is found: id= {}", id);
        return client;
    }

    public Client createClient(Client client) {

        Optional<Client> beforeCreation = clientRepository.findByPersonalNumber(client.getPersonalNumber());
        if (beforeCreation.isPresent()) {
            if (beforeCreation.get().getClientStatus().equals(Status.INACTIVE)) {
                throw new InactiveClientException(client.getPersonalNumber());
            }
            throw new ClientAlreadyExistException(client.getPersonalNumber());
        }
        Long clientId = client.getClientId();
        log.info("Client created, clientId : {}", clientId);
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client client) {
        Client toUpdate = getClient(id);

        Optional.ofNullable(client.getFirstName()).ifPresent(newFirstName -> {
            log.info("updateClient: id={}, field=firstName, oldValue={}, newValue={}", id, toUpdate.getFirstName(), newFirstName);
            toUpdate.setFirstName(newFirstName);
        });

        Optional.ofNullable(client.getLastName()).ifPresent(newLastName -> {
            log.info("updateClient: id={}, field=lastName, oldValue={}, newValue={}", id, toUpdate.getLastName(), newLastName);
            toUpdate.setLastName(newLastName);
        });

        Optional.ofNullable(client.getEmail()).ifPresent(newEmail -> {
            log.info("updateClient: id={}, field=email, oldValue={}, newValue={}", id, toUpdate.getEmail(), newEmail);
            toUpdate.setEmail(newEmail);
        });

        Optional.ofNullable(client.getPhone()).ifPresent(newPhone -> {
            log.info("updateClient: id={}, field=phone, oldValue={}, newValue={}", id, toUpdate.getPhone(), newPhone);
            toUpdate.setPhone(newPhone);
        });

        Optional.ofNullable(client.getAddress()).ifPresent(newAddress -> {
            log.info("updateClient: id={}, field=address, oldValue={}, newValue={}", id, toUpdate.getAddress(), newAddress);
            toUpdate.setAddress(newAddress);
        });

        Optional.ofNullable(client.getCitizenship()).ifPresent(newCitizenship -> {
            log.info("updateClient: id={}, field=citizenship, oldValue={}, newValue={}", id, toUpdate.getCitizenship(), newCitizenship);
            toUpdate.setCitizenship(newCitizenship);
        });

        return clientRepository.save(toUpdate);
    }

    public Client deactivateClient(Long id) {

        Client client = getClient(id);
        client.setClientStatus(Status.INACTIVE);
        List<Account> accountList = client.getListOfAccounts();

        for (Account account : accountList) {
            accountService.deactivateAccount(account.getAccountNumber());
        }
        log.info("Client deactivated, clientId : {}", id);
        return clientRepository.save(client);
    }

    public Client activateClient(Long id) {
        Client client = clientRepository.findByClientIdAndClientStatus(id, Status.INACTIVE).orElseThrow(() -> new NotFoundException("Client not found"));
        client.setClientStatus(Status.ACTIVE);
        log.info("Client activated, clientId : {}", id);
        return clientRepository.save(client);
    }

    public Page<Client> search(PagingRequest pagingRequest, ClientFilter clientFilter) {

        Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize());
        Page<Client> resultPage = clientRepository.search(pageable, clientFilter);
        log.info("search: page={}, size={}, totalElements={}", pagingRequest.getPage(), pagingRequest.getSize(), resultPage.getTotalElements());
        return resultPage;
    }

    public Client getClientByPersonalNumber(Long personalNumber) {

        Client client = clientRepository.findByPersonalNumberAndClientStatus(personalNumber, Status.ACTIVE).orElseThrow(() -> new NotFoundException("Client not found"));
        log.info("Client is found: id={}, personalNumber={}", client.getClientId(), personalNumber);
        return client;
    }
}