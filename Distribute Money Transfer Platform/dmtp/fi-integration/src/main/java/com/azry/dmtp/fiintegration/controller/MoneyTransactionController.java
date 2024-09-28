package com.azry.dmtp.fiintegration.controller;

import com.azry.dmtp.fiintegration.mapper.MoneyTransactionMapper;
import com.azry.dmtp.fiintegration.model.api.TransactionInitializationRequest;
import com.azry.dmtp.fiintegration.model.api.dto.MoneyTransactionDTO;
import com.azry.dmtp.fiintegration.model.api.dto.TransactionStatusDTO;
import com.azry.dmtp.fiintegration.service.MoneyTransactionService;
import com.azry.dmtp.z.bankservice.TransactionCreationRequest;
import com.azry.dmtp.z.bankservice.TransactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class MoneyTransactionController {

    private final MoneyTransactionService moneyTransactionService;

    private final MoneyTransactionMapper moneyTransactionMapper;

    @PostMapping
    public ResponseEntity<MoneyTransactionDTO> createTransaction(@RequestBody @Valid TransactionInitializationRequest transactionInitializationRequest) {

        TransactionCreationRequest transactionCreationRequest = moneyTransactionMapper
                .transactionInitializationRequestToTransactionDTO(transactionInitializationRequest);

        TransactionDTO transactionDTO = moneyTransactionService.createTransaction(transactionCreationRequest);

        MoneyTransactionDTO moneyTransactionDTO = moneyTransactionMapper.transactionDtoToMoneyTransactionDto(transactionDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(moneyTransactionDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionStatusDTO> getTransactionStatus(@PathVariable Long id) {
        return ResponseEntity.ok(moneyTransactionMapper.toTransactionStatusDto(id, moneyTransactionService.getTransactionStatus(id)));
    }
}
