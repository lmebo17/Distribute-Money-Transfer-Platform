package com.azry.dmtp.z.service.implementation;

import com.azry.dmtp.z.model.entity.Transaction;
import com.azry.dmtp.z.model.enums.TransactionStatus;
import com.azry.dmtp.z.repository.TransactionRepository;
import com.azry.dmtp.z.service.AccountService;
import com.azry.dmtp.z.service.MakeTransferService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MakeTransferServiceImpl implements MakeTransferService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Transactional
    public void makeTransfer(Transaction transaction) {
        makeTransferBetweenAccounts(transaction);
        transaction.setStatus(TransactionStatus.PROCESSED);
        transactionRepository.save(transaction);
    }

    private void makeTransferBetweenAccounts(Transaction transaction) {
        BigDecimal senderBalance = transaction.getSenderAccount().getBalance();
        BigDecimal receiverBalance = transaction.getReceiverAccount().getBalance();
        BigDecimal amount = transaction.getAmount();

        if (senderBalance != null && receiverBalance != null && amount != null) {
            String senderAccountNumber = transaction.getSenderAccount().getAccountNumber();
            String receiverAccountNumber = transaction.getReceiverAccount().getAccountNumber();
            accountService.withdraw(senderAccountNumber, amount);
            accountService.deposit(receiverAccountNumber, amount);
        } else {
            throw new IllegalArgumentException("Balance or amount cannot be null");
        }
    }
}
