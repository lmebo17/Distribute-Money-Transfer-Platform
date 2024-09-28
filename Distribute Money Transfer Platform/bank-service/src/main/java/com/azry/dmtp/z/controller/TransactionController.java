package com.azry.dmtp.z.controller;

import com.azry.dmtp.z.annotation.LogControllerUsage;
import com.azry.dmtp.z.mappers.TransactionMapper;
import com.azry.dmtp.z.model.api.PagingRequest;
import com.azry.dmtp.z.model.api.TransactionCreationRequest;
import com.azry.dmtp.z.model.api.dto.TransactionDTO;
import com.azry.dmtp.z.model.api.filter.TransactionFilter;
import com.azry.dmtp.z.model.entity.Transaction;
import com.azry.dmtp.z.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "transactions", description = "The transactions API")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @LogControllerUsage
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody @Valid TransactionCreationRequest transactionCreationRequest) {
        Transaction transaction = transactionMapper.toTransaction(transactionCreationRequest);
        Transaction newTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionMapper.toTransactionDTO(newTransaction));
    }

    @LogControllerUsage
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransaction(id);
        return ResponseEntity.ok(transactionMapper.toTransactionDTO(transaction));
    }

    @LogControllerUsage
    @GetMapping("/filter")
    public ResponseEntity<Page<TransactionDTO>> search(PagingRequest pagingRequest, TransactionFilter transactionFilter) {
        Page<Transaction> transactions = transactionService.search(pagingRequest, transactionFilter);
        return ResponseEntity.ok(transactions.map(transactionMapper::toTransactionDTO));
    }

}