package com.azry.dmtp.fiintegration.service.implementation;

import com.azry.dmtp.fiintegration.config.TransferSystemProperties;
import com.azry.dmtp.fiintegration.config.handler.exception.TransactionNotCreatedException;
import com.azry.dmtp.fiintegration.mapper.MoneyTransactionMapper;
import com.azry.dmtp.fiintegration.model.api.TransactionsProcessingCommand;
import com.azry.dmtp.fiintegration.model.api.dto.MoneyTransactionDTO;
import com.azry.dmtp.fiintegration.service.BankClientService;
import com.azry.dmtp.model.TransferTypeModel;
import com.azry.dmtp.z.bankservice.TransactionCreationRequest;
import com.azry.dmtp.z.bankservice.TransactionDTO;
import com.azry.dmtp.z.bankservice.api.TransactionsApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankClientServiceImpl implements BankClientService {

    private final TransactionsApi transactionsApi;
    private final MoneyTransactionMapper mapper;
    private final TransferSystemProperties transferSystemProperties;

    public MoneyTransactionDTO sendCommandToBank(TransactionsProcessingCommand transferCommand) {
        try {
            log.info("Calling bank-service TransactionsApi for transferId: {}", transferCommand.getTransfer().getId());
            TransactionCreationRequest transactionRequest = mapCommandToTransactionRequest(transferCommand);
            TransactionDTO transactionResponse = transactionsApi.createTransaction(transactionRequest);
            return mapper.transactionDtoToMoneyTransactionDto(transactionResponse);
        } catch (Exception e) {
            log.error("Error processing transaction with bank-service: {}", e.getMessage());
            throw new TransactionNotCreatedException(HttpStatus.BAD_REQUEST.value(), "Transaction cant be created", e.getMessage());
        }
    }

    private TransactionCreationRequest mapCommandToTransactionRequest(TransactionsProcessingCommand transferCommand) {
        TransactionCreationRequest request = new TransactionCreationRequest();

        String mts = transferCommand.getTransfer().getMts();
        String currency = transferCommand.getTransfer().getCurrency();

        boolean isSendType = TransferTypeModel.SEND.equals(transferCommand.getTransfer().getType());
        String accountNumber = getAccountNumber(transferCommand, isSendType);
        String systemAccountNumber = getMtsAccountNumber(mts, currency);

        if (isSendType) {
            request.setSenderAccountNumber(accountNumber);
            request.setReceiverAccountNumber(systemAccountNumber);
        } else {
            request.setSenderAccountNumber(systemAccountNumber);
            request.setReceiverAccountNumber(accountNumber);
        }

        request.setCurrency(transferCommand.getTransfer().getCurrency());
        request.setAmount(transferCommand.getTransfer().getAmount());

        return request;
    }

    private String getAccountNumber(TransactionsProcessingCommand transferCommand, boolean isSendType) {
        return isSendType
                ? transferCommand.getTransfer().getSender().getAccounts().get(0).getAccountNumber()
                : transferCommand.getTransfer().getReceiver().getAccounts().get(0).getAccountNumber();
    }

    private String getMtsAccountNumber(String mts, String currency) {
        return Optional.ofNullable(getMoneyTransferSystem(mts))
                .flatMap(system -> getAccountNumber(system, currency))
                .orElseGet(() -> {
                    log.error("No account number found for MTS: {} and currency: {}", mts, currency);
                    return null;
                });
    }

    private TransferSystemProperties.MoneyTransferSystem getMoneyTransferSystem(String mts) {
        return switch (mts.toLowerCase()) {
            case "moneygram" -> transferSystemProperties.getMoneygram();
            case "western-union" -> transferSystemProperties.getWesternUnion();
            default -> {
                log.error("Unknown money transfer system: {}", mts);
                yield null;
            }
        };
    }

    private Optional<String> getAccountNumber(TransferSystemProperties.MoneyTransferSystem system, String currency) {
        return Optional.ofNullable(system.getCurrency())
                .map(currencyAccounts -> switch (currency.toUpperCase()) {
                    case "GEL" -> currencyAccounts.getGel();
                    case "USD" -> currencyAccounts.getUsd();
                    case "EUR" -> currencyAccounts.getEur();
                    default -> {
                        log.error("Unsupported currency: {}", currency);
                        yield null;
                    }
                })
                .map(TransferSystemProperties.AccountNumber::getAccountNumber);
    }
}