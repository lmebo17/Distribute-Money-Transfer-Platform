package com.azry.dmtp.fiintegration.service.implementation;

import com.azry.dmtp.fiintegration.config.handler.exception.NotFoundException;
import com.azry.dmtp.fiintegration.config.handler.exception.ServiceUnableException;
import com.azry.dmtp.fiintegration.config.handler.exception.TransactionNotCreatedException;
import com.azry.dmtp.fiintegration.config.handler.exception.UnknownException;
import com.azry.dmtp.fiintegration.service.MoneyTransactionService;
import com.azry.dmtp.z.bankservice.TransactionCreationRequest;
import com.azry.dmtp.z.bankservice.TransactionDTO;
import com.azry.dmtp.z.bankservice.api.TransactionsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

@Service
@RequiredArgsConstructor
public class MoneyTransactionServiceImpl implements MoneyTransactionService {

    private final TransactionsApi transactionsApi;

    public TransactionDTO createTransaction(TransactionCreationRequest transactionCreationRequest) {
        try {
            return transactionsApi.createTransaction(transactionCreationRequest);
        } catch (ResourceAccessException rae) {
            throw new ServiceUnableException(HttpStatus.SERVICE_UNAVAILABLE.value(), "BankService is not responding", rae.getMessage());
        } catch (RestClientException rce) {
            throw new TransactionNotCreatedException(HttpStatus.BAD_REQUEST.value(), "Transaction cant be created", rce.getMessage());
        } catch (Exception ex) {
            throw new UnknownException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        }
    }

    public String getTransactionStatus(Long id) {
        try {
            TransactionDTO transactionDTO = transactionsApi.getTransaction(id);
            return transactionDTO.getStatus().name();
        } catch (ResourceAccessException rae) {
            throw new ServiceUnableException(HttpStatus.SERVICE_UNAVAILABLE.value(), rae.getMessage(), "BankService is not responding");
        } catch (Exception ex) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), ex.getMessage(), "Bank Service can't find participant");
        }
    }
}
