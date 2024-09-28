package com.azry.dmtp.z.service.implementation;

import com.azry.dmtp.z.config.BankServiceProperties;
import com.azry.dmtp.z.model.entity.Transaction;
import com.azry.dmtp.z.model.enums.Status;
import com.azry.dmtp.z.model.enums.TransactionStatus;
import com.azry.dmtp.z.repository.TransactionRepository;
import com.azry.dmtp.z.service.MakeTransferService;
import com.azry.dmtp.z.service.TransactionProcessingService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionProcessingServiceImpl implements TransactionProcessingService {

    private static final List<TransactionStatus> TRANSACTION_STATUSES_TO_PROCESS = List.of(TransactionStatus.CREATED, TransactionStatus.PENDING);
    private final List<Check> checks = new ArrayList<>();

    private static final Random random = new Random();

    private final TransactionRepository transactionRepository;
    private final BankServiceProperties bankServiceProperties;
    private final MakeTransferService makeTransferService;


    public static int generateRandomNumber(int max) {
        return random.nextInt(max + 1);
    }

    @AllArgsConstructor
    private static class Check {
        private Predicate<Transaction> predicate;
        private String message;
        private TransactionStatus status;
    }

    @PostConstruct
    public void TransactionProcessor() {
        checks.add(new Check(this::isBlackListedCitizen, "Transaction was rejected due to blacklisted country citizenship.", TransactionStatus.REJECTED));
        checks.add(new Check(this::isAccountSame, "Sender and receiver accounts shouldn't be the same.", TransactionStatus.REJECTED));
        checks.add(new Check(t -> !isAccountActive(t), "Transaction was rejected due to inactive account.", TransactionStatus.REJECTED));
        checks.add(new Check(t -> !checkAccountsCurrency(t), "Transaction was rejected due to invalid currency.", TransactionStatus.REJECTED));
        checks.add(new Check(t -> !isBalanceSufficient(t), "Transaction was rejected because of insufficient balance.", TransactionStatus.REJECTED));
        checks.add(new Check(t -> moveToPending(), "Transaction was moved in pending status according success rate.", TransactionStatus.PENDING));
    }

    public boolean checkTransactionChecksPassed(Transaction transaction) {
        for (Check check : checks) {
            if (check.predicate.test(transaction)) {
                transaction.setStatus(check.status);
                transactionRepository.save(transaction);
                log.info("transactionId: {}, {}", transaction.getTransactionId(), check.message);
                return false;
            }
        }
        return true;
    }

    @Scheduled(initialDelayString = "#{@bankServiceProperties.transactionSchedulingInitialDelay}", fixedDelayString = "#{@bankServiceProperties.transactionSchedulingFixedRate}")
    public void process() {
        List<Transaction> transactions = transactionRepository.findAllByStatusInSortedByDate(TRANSACTION_STATUSES_TO_PROCESS);
        for (Transaction transaction : transactions) {
            if (!checkTransactionChecksPassed(transaction)) continue;
            try {
                makeTransferService.makeTransfer(transaction);
            } catch (Exception e) {
                transaction.setStatus(TransactionStatus.REJECTED);
                log.error("Transaction failed during transfer. transactionId: {}, error: {}", transaction.getTransactionId(), e.getMessage());
                transactionRepository.save(transaction);
            }
        }
    }

    private boolean moveToPending() {
        return generateRandomNumber(100) > bankServiceProperties.getTransactionSuccessPercentage();
    }

    private boolean isAccountSame(Transaction transaction) {
        return transaction.getSenderAccount().getAccountNumber().equals(
                transaction.getReceiverAccount().getAccountNumber());
    }

    private boolean isAccountActive(Transaction transaction) {
        Status senderAccountStatus = transaction.getSenderAccount().getStatus();
        Status receiverAccountStatus = transaction.getReceiverAccount().getStatus();

        return senderAccountStatus == Status.ACTIVE && receiverAccountStatus == Status.ACTIVE;
    }


    private boolean checkAccountsCurrency(Transaction transaction) {
        String senderCurrency = transaction.getSenderAccount().getCurrency();
        String receiverCurrency = transaction.getReceiverAccount().getCurrency();

        return senderCurrency.equals(receiverCurrency) && senderCurrency.equals(transaction.getCurrency());
    }

    private boolean isBalanceSufficient(Transaction transaction) {
        BigDecimal senderBalance = transaction.getSenderAccount().getBalance();
        BigDecimal amount = transaction.getAmount();

        if (senderBalance == null || amount == null) {
            throw new IllegalArgumentException("Balance or amount cannot be null");
        } else {
            return senderBalance.compareTo(amount) >= 0;
        }
    }

    private boolean isBlackListedCitizen(Transaction transaction) {
        String senderCitizenship = transaction.getSenderAccount().getClient().getCitizenship();
        String receiverCitizenship = transaction.getReceiverAccount().getClient().getCitizenship();

        return bankServiceProperties.getBlacklistedCountries().contains(senderCitizenship) ||
                bankServiceProperties.getBlacklistedCountries().contains(receiverCitizenship);
    }

}
