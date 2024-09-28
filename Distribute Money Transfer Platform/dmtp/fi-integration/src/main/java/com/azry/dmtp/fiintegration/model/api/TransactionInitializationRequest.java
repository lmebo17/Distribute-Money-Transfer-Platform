package com.azry.dmtp.fiintegration.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionInitializationRequest {

    @NotNull
    private String senderAccountNumber;

    @NotNull
    private String receiverAccountNumber;

    @NotNull
    private String currency;

    @NotNull
    private BigDecimal amount;

}
