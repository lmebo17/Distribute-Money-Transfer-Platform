package com.azry.dmtp.z.repository;

import com.azry.dmtp.z.model.api.filter.AccountFilter;
import com.azry.dmtp.z.model.entity.Account;
import com.azry.dmtp.z.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByAccountNumberAndStatus(String accountNumber, Status status);

    Optional<Account> findAccountByAccountNumber(String accountNumber);

    @Query("SELECT A FROM Account A " +
            "WHERE (:#{#accountFilter.status} is NULL OR A.status = :#{#accountFilter.status})" +
            "AND (:#{#accountFilter.clientId} IS NULL OR A.client.clientId = :#{#accountFilter.clientId})" +
            "AND (:#{#accountFilter.currency} IS NULL OR A.currency = :#{#accountFilter.currency})" +
            "AND (:#{#accountFilter.accountNumber} IS NULL OR A.accountNumber = :#{#accountFilter.accountNumber})")
    Page<Account> search(AccountFilter accountFilter, PageRequest pagingRequest);

    List<Account> findAccountsByClientClientIdAndStatus(Long clientId, Status status);

}
