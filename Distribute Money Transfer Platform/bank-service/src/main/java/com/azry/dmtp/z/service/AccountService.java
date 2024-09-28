package com.azry.dmtp.z.service;

import com.azry.dmtp.z.model.api.PagingRequest;
import com.azry.dmtp.z.model.api.filter.AccountFilter;
import com.azry.dmtp.z.model.entity.Account;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;


public interface AccountService {

    Account createAccount(Account account);

    Account getAccount(String accountNumber);

    Account checkBalance(String accountNumber);

    Account deposit(String accountNumber, BigDecimal amount);

    Account withdraw(String accountNumber, BigDecimal amount);

    Account deactivateAccount(String accountNumber);

    Page<Account> searchAccounts(PagingRequest pagingRequest, AccountFilter accountFilter);

    List<Account> getAccounts(Long clientId);

}