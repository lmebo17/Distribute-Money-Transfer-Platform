package com.azry.dmtp.z.service;

import com.azry.dmtp.z.model.api.PagingRequest;
import com.azry.dmtp.z.model.api.filter.ClientFilter;
import com.azry.dmtp.z.model.entity.Client;
import org.springframework.data.domain.Page;

public interface ClientService {

    Client getClient(Long id);

    Client createClient(Client client);

    Client updateClient(Long id, Client client);

    Client deactivateClient(Long id);

    Page<Client> search(PagingRequest pagingRequest, ClientFilter clientFilter);

    Client getClientByPersonalNumber(Long personalNumber);

    Client activateClient(Long id);
}
