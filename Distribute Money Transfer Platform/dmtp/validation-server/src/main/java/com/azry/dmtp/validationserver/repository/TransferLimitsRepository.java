package com.azry.dmtp.validationserver.repository;

import com.azry.dmtp.validationserver.model.TransferLimitsFilter;
import com.azry.dmtp.validationserver.model.TransferLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferLimitsRepository extends JpaRepository<TransferLimit, Long> {

    @Query("SELECT t FROM TransferLimit t " +
            "WHERE (:#{#transferLimitsFilter.transferType} = t.transferType OR t.transferType = '*' )" +
            "AND (:#{#transferLimitsFilter.mts} = t.mts OR  t.mts = '*')" +
            "AND (:#{#transferLimitsFilter.country} = t.country OR  t.country = '*')" +
            "AND (:#{#transferLimitsFilter.currency} = t.currency OR  t.currency = '*')"
    )
    List<TransferLimit> search(TransferLimitsFilter transferLimitsFilter);

}
