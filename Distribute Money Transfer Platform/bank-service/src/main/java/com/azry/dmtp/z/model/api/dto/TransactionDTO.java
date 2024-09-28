package com.azry.dmtp.z.model.api.dto;

import com.azry.dmtp.z.model.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionDTO {

    @NotNull
    private Long id;

    @NotNull
    private String receiverAccountNumber;

    @NotNull
    private String senderAccountNumber;

    @NotNull
    private String currency;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDateTime creationDate;

    @NotNull
    private TransactionStatus status;
}
