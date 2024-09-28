package com.azry.dmtp.z.model.api.filter;

import com.azry.dmtp.z.model.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFilter {

    private Long id;

    private String receiverAccountNumber;

    private String senderAccountNumber;

    private String currency;

    private LocalDateTime searchStartDate;

    private LocalDateTime searchEndDate;

    private BigDecimal amountLowerBound;

    private BigDecimal amountUpperBound;

    private TransactionStatus status;

}
