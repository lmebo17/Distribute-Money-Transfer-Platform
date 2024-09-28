package com.azry.dmtp.z.controller;

import com.azry.dmtp.z.annotation.LogControllerUsage;
import com.azry.dmtp.z.mappers.ClientMapper;
import com.azry.dmtp.z.model.api.ClientCreationRequest;
import com.azry.dmtp.z.model.api.ClientUpdateRequest;
import com.azry.dmtp.z.model.api.PagingRequest;
import com.azry.dmtp.z.model.api.dto.ClientDTO;
import com.azry.dmtp.z.model.api.filter.ClientFilter;
import com.azry.dmtp.z.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "clients", description = "The clients API")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    @LogControllerUsage
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientMapper.toModel(clientService.getClient(id)));
    }

    @LogControllerUsage
    @GetMapping("/personal-number/{personalNumber}")
    public ResponseEntity<ClientDTO> getClientByPersonalNumber(@PathVariable Long personalNumber) {
        return ResponseEntity.ok(clientMapper.toModel(clientService.getClientByPersonalNumber(personalNumber)));
    }

    @LogControllerUsage
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid ClientCreationRequest clientCreationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.toModel(clientService.createClient(clientMapper.clientRequestToClient(clientCreationRequest))));
    }

    @LogControllerUsage
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody @Valid ClientUpdateRequest clientUpdateRequest) {
        return ResponseEntity.ok(clientMapper.toModel(clientService.updateClient(id, clientMapper.clientUpdateRequestToClient(clientUpdateRequest))));
    }

    @LogControllerUsage
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<ClientDTO> deactivateClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientMapper.toModel(clientService.deactivateClient(id)));
    }

    @LogControllerUsage
    @PutMapping("/activate/{id}")
    public ResponseEntity<ClientDTO> activateClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientMapper.toModel(clientService.activateClient(id)));
    }

    @LogControllerUsage
    @GetMapping("/filter")
    public ResponseEntity<Page<ClientDTO>> search(PagingRequest pagingRequest, ClientFilter clientFilter) {
        return ResponseEntity.ok(clientService.search(pagingRequest, clientFilter).map(clientMapper::toModel));
    }
}