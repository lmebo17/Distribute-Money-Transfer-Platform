package com.azry.dmtp.z.controller;

import com.azry.dmtp.z.annotation.LogControllerUsage;
import com.azry.dmtp.z.mappers.AccountMapper;
import com.azry.dmtp.z.model.api.AccountCreationRequest;
import com.azry.dmtp.z.model.api.PagingRequest;
import com.azry.dmtp.z.model.api.dto.AccountDTO;
import com.azry.dmtp.z.model.api.dto.BalanceDTO;
import com.azry.dmtp.z.model.api.filter.AccountFilter;
import com.azry.dmtp.z.model.entity.Account;
import com.azry.dmtp.z.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "accounts", description = "The accounts API")
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @LogControllerUsage
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountCreationRequest accountCreationRequest) {
        Account account = accountMapper.creationRequestToAccount(accountCreationRequest);
        account = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.accountToDTO(account));
    }

    @LogControllerUsage
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountMapper.accountToDTO(accountService.getAccount(accountNumber)));
    }

    @LogControllerUsage
    @GetMapping("/client-accounts/{clientId}")
    public ResponseEntity<List<AccountDTO>> getAccounts(@PathVariable Long clientId) {
        return ResponseEntity.ok(accountMapper.accountsToDTOs(accountService.getAccounts(clientId)));
    }

    @LogControllerUsage
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BalanceDTO> checkBalance(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountMapper.toBalanceDTO(accountService.checkBalance(accountNumber)));
    }

    @LogControllerUsage
    @PutMapping("/{accountNumber}/deposit")
    public ResponseEntity<BalanceDTO> deposit(@PathVariable String accountNumber,
                                              @RequestParam @DecimalMin(value = "0.0", inclusive = false) BigDecimal amountOfMoney) {
        return ResponseEntity.ok(accountMapper.toBalanceDTO(accountService.deposit(accountNumber, amountOfMoney)));
    }

    @LogControllerUsage
    @PutMapping("/{accountNumber}/withdraw")
    public ResponseEntity<BalanceDTO> withdraw(@PathVariable String accountNumber,
                                               @RequestParam @DecimalMin(value = "0.0", inclusive = false) BigDecimal amountOfMoney) {
        return ResponseEntity.ok(accountMapper.toBalanceDTO(accountService.withdraw(accountNumber, amountOfMoney)));

    }

    @LogControllerUsage
    @PutMapping("/deactivate/{accountNumber}")
    public ResponseEntity<AccountDTO> deactivateAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountMapper.toDeactivateDTO(accountService.deactivateAccount(accountNumber)));
    }

    @LogControllerUsage
    @GetMapping
    public ResponseEntity<Page<AccountDTO>> search(PagingRequest pagingRequest, AccountFilter accountFilter) {
        Page<AccountDTO> accountsPage = accountService.searchAccounts(pagingRequest, accountFilter)
                .map(accountMapper::accountToDTO);
        return ResponseEntity.ok(accountsPage);
    }

}

