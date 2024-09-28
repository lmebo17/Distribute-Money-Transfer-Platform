package com.azry.dmtp.z.service.implementation;

import com.azry.dmtp.z.config.BankServiceProperties;
import com.azry.dmtp.z.config.handler.exception.NotFoundException;
import com.azry.dmtp.z.config.handler.exception.UnsupportedCurrencyException;
import com.azry.dmtp.z.model.api.PagingRequest;
import com.azry.dmtp.z.model.api.filter.TransactionFilter;
import com.azry.dmtp.z.model.entity.Transaction;
import com.azry.dmtp.z.model.enums.TransactionStatus;
import com.azry.dmtp.z.repository.TransactionRepository;
import com.azry.dmtp.z.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankServiceProperties bankServiceProperties;

    public Transaction createTransaction(Transaction transaction) {
        if (!bankServiceProperties.getCurrencies().contains(transaction.getCurrency())) {
            throw new UnsupportedCurrencyException(transaction.getCurrency() + " is not supported");
        }

        transaction.setStatus(TransactionStatus.CREATED);
        transaction.setCreationDate(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transaction created, transactionId {}", savedTransaction.getTransactionId());
        return savedTransaction;
    }

    public Transaction getTransaction(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            log.info("Transaction found, transactionId: {}", transaction.get().getTransactionId());
            return transaction.get();
        } else {
            throw new NotFoundException("Transaction with id " + id + " does not exist.");
        }
    }

    public Page<Transaction> search(PagingRequest pagingRequest, TransactionFilter transactionFilter) {
        Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize());
        Page<Transaction> transactions = transactionRepository.filterTransactions(pageable, transactionFilter);
        log.info("Search transactions : page={}, size={}, totalElements={}", pagingRequest.getPage(), pagingRequest.getSize(), transactions.getTotalElements());
        return transactions;
    }
}
