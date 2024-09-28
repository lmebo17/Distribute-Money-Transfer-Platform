package com.azry.dmtp.z.service.implementation;

import com.azry.dmtp.z.config.BankServiceProperties;
import com.azry.dmtp.z.config.handler.exception.InsufficientBalanceException;
import com.azry.dmtp.z.config.handler.exception.NotFoundException;
import com.azry.dmtp.z.config.handler.exception.UnsupportedCurrencyException;
import com.azry.dmtp.z.model.api.PagingRequest;
import com.azry.dmtp.z.model.api.filter.AccountFilter;
import com.azry.dmtp.z.model.entity.Account;
import com.azry.dmtp.z.model.enums.Status;
import com.azry.dmtp.z.repository.AccountRepository;
import com.azry.dmtp.z.service.AccountNumberGenerator;
import com.azry.dmtp.z.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final BankServiceProperties bankServiceProperties;

    public Account createAccount(Account account) {
        if (bankServiceProperties.getCurrencies().contains(account.getCurrency())) {
            account.setCurrency(account.getCurrency());
        } else {
            throw new UnsupportedCurrencyException(account.getCurrency() + " is not supported.");
        }
        String accNumber = accountNumberGenerator.generateAccountNumber(account.getCurrency(), 10);
        account.setAccountNumber(accNumber);
        account.setStatus(Status.ACTIVE);
        String acc = account.getAccountNumber();
        log.info("Account created, accountNumber : {}", acc);
        return accountRepository.save(account);
    }

    public Account getAccount(String accountNumber) {
        Optional<Account> account = accountRepository.findAccountByAccountNumberAndStatus(accountNumber, Status.ACTIVE);
        if (account.isPresent()) {
            String accNumber = account.get().getAccountNumber();
            log.info("Account found, accountNumber : {}", accNumber);
        }
        return account.orElseThrow(() -> new NotFoundException("Account with account number:" + accountNumber + " not found."));
    }

    public List<Account> getAccounts(Long clientId) {
        List<Account> accounts = accountRepository.findAccountsByClientClientIdAndStatus(clientId, Status.ACTIVE);
        if (accounts.isEmpty()) {
            throw new NotFoundException("No accounts found for client with id: " + clientId);
        }
        log.info("Accounts found for client: clientId={}, accounts={}", clientId, accounts);
        return accounts;
    }


    public Account checkBalance(String accountNumber) {
        Optional<Account> account = accountRepository.findAccountByAccountNumberAndStatus(accountNumber, Status.ACTIVE);
        if (account.isPresent()) {
            BigDecimal accountBalance = account.get().getBalance();
            log.info("Account with balance : {}", accountBalance);
        }
        return account.orElseThrow(() -> new NotFoundException("Account not found: " + accountNumber));
    }

    public Account deposit(String accountNumber, BigDecimal amount) {
        Optional<Account> account = accountRepository.findAccountByAccountNumberAndStatus(accountNumber, Status.ACTIVE);
        if (account.isPresent()) {
            Account currentAccount = account.get();
            BigDecimal accountBalance = currentAccount.getBalance();
            currentAccount.setBalance(currentAccount.getBalance().add(amount));
            log.info(" Account Balance has been increased by : {} \n Now, Account Balance is  : {} ", amount, accountBalance);
            return accountRepository.save(currentAccount);
        } else {
            throw new NotFoundException("Account not found: " + accountNumber);
        }
    }

    public Account withdraw(String accountNumber, BigDecimal amount) {
        Optional<Account> account = accountRepository.findAccountByAccountNumberAndStatus(accountNumber, Status.ACTIVE);
        if (account.isPresent()) {
            Account currentAccount = account.get();
            if (amount.compareTo(currentAccount.getBalance()) <= 0) {
                currentAccount.setBalance(currentAccount.getBalance().subtract(amount));
                BigDecimal accountBalance = currentAccount.getBalance();
                log.info(" account has been debited by : {} \n Account Balance is  : {}", amount, accountBalance);
            } else {
                throw new InsufficientBalanceException("Unable to withdraw " + amount + account.get().getCurrency() + ". " +
                        "Insufficient balance: " + currentAccount.getBalance());
            }
            return accountRepository.save(currentAccount);
        } else {
            throw new NotFoundException("Account not found: " + accountNumber);
        }
    }

    public Account deactivateAccount(String accountNumber) {
        Optional<Account> account = accountRepository.findAccountByAccountNumberAndStatus(accountNumber, Status.ACTIVE);
        if (account.isPresent()) {
            Account currentAccount = account.get();
            currentAccount.setStatus(Status.INACTIVE);
            log.info(" account has been deactivated by : {}", accountNumber);
            return accountRepository.save(currentAccount);
        } else {
            throw new NotFoundException("Account not found: " + accountNumber);
        }
    }

    public Page<Account> searchAccounts(PagingRequest pagingRequest, AccountFilter accountFilter) {
        Page<Account> resultPage = accountRepository.search(accountFilter, PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize()));
        log.info("search: page={}, size={}, totalElements={}", pagingRequest.getPage(), pagingRequest.getSize(), resultPage.getTotalElements());
        return resultPage;
    }

}
