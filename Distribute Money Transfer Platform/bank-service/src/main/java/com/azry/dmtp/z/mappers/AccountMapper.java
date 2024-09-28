package com.azry.dmtp.z.mappers;

import com.azry.dmtp.z.model.api.AccountCreationRequest;
import com.azry.dmtp.z.model.api.dto.AccountDTO;
import com.azry.dmtp.z.model.api.dto.BalanceDTO;
import com.azry.dmtp.z.model.entity.Account;
import com.azry.dmtp.z.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountMapper {


    private final ClientService clientService;

    public AccountDTO accountToDTO(Account account) {
        if (account == null) {
            return null;
        }
        return AccountDTO.builder()
                .id(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountStatus(account.getStatus())
                .currency(account.getCurrency())
                .clientId(account.getClient().getClientId())
                .build();
    }

    public Account creationRequestToAccount(AccountCreationRequest creationRequest) {
        if (creationRequest == null) {
            return null;
        }

        return Account.builder()
                .balance(creationRequest.getBalance())
                .currency(creationRequest.getCurrency())
                .client(clientService.getClient(creationRequest.getClientId()))
                .build();
    }

    public BalanceDTO toBalanceDTO(Account account) {
        if (account == null) {
            return null;
        }
        return BalanceDTO.builder()
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .accountNumber(account.getAccountNumber())
                .build();
    }

    public List<AccountDTO> accountsToDTO(List<Account> accounts) {
        if (accounts == null) {
            return null;
        }
        return accounts.stream()
                .map(this::accountToDTO)
                .collect(Collectors.toList());
    }

    public AccountDTO toDeactivateDTO(Account account) {
        if (account == null) {
            return null;
        }
        return AccountDTO.builder()
                .accountNumber(account.getAccountNumber())
                .id(account.getAccountId())
                .accountStatus(account.getStatus())
                .build();
    }

    public List<AccountDTO> accountsToDTOs(List<Account> accounts) {
        if (accounts == null) {
            return null;
        }
        return accounts.stream()
                .map(this::accountToDTO)
                .collect(Collectors.toList());
    }
}