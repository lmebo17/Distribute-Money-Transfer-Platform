package com.azry.dmtp.z.repository;

import com.azry.dmtp.z.model.api.filter.TransactionFilter;
import com.azry.dmtp.z.model.entity.Transaction;
import com.azry.dmtp.z.model.enums.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT T FROM Transaction T WHERE " +
            "(:#{#filter.id} IS NULL OR T.transactionId = :#{#filter.id}) " +
            "AND (:#{#filter.receiverAccountNumber} IS NULL OR T.receiverAccount.accountNumber = :#{#filter.receiverAccountNumber}) " +
            "AND (:#{#filter.senderAccountNumber} IS NULL OR T.senderAccount.accountNumber = :#{#filter.senderAccountNumber}) " +
            "AND (:#{#filter.currency} IS NULL OR T.currency = :#{#filter.currency}) " +
            "AND (:#{#filter.searchStartDate} IS NULL OR T.creationDate >= :#{#filter.searchStartDate}) " +
            "AND (:#{#filter.searchEndDate} IS NULL OR T.creationDate <= :#{#filter.searchEndDate}) " +
            "AND (:#{#filter.amountLowerBound} IS NULL OR T.amount >= :#{#filter.amountLowerBound}) " +
            "AND (:#{#filter.amountUpperBound} IS NULL OR T.amount <= :#{#filter.amountUpperBound}) " +
            "AND (:#{#filter.status} IS NULL OR T.status = :#{#filter.status})"
    )
    Page<Transaction> filterTransactions(Pageable pageable, TransactionFilter filter);

    @Query("SELECT t FROM Transaction t WHERE t.status IN :statuses ORDER BY t.creationDate ASC")
    List<Transaction> findAllByStatusInSortedByDate(List<TransactionStatus> statuses);
}
