package com.azry.dmtp.z.service;

import com.azry.dmtp.z.model.api.PagingRequest;
import com.azry.dmtp.z.model.api.filter.TransactionFilter;
import com.azry.dmtp.z.model.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    Transaction getTransaction(Long id);

    Page<Transaction> search(PagingRequest pagingRequest, TransactionFilter transactionFilter);
}
