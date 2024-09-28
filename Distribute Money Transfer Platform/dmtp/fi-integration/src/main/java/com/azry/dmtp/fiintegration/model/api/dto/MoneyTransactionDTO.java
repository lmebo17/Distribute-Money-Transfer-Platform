package com.azry.dmtp.fiintegration.model.api.dto;

import com.azry.dmtp.fiintegration.model.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MoneyTransactionDTO {

    @NotNull
    private Long id;

    @NotNull
    private String senderAccountNumber;

    @NotNull
    private String receiverAccountNumber;

    @NotNull
    private String currency;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDateTime creationDate;

    @NotNull
    private TransactionStatus status;
}
