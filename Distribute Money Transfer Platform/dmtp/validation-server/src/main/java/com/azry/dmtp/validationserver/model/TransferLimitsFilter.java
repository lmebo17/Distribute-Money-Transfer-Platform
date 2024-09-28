package com.azry.dmtp.validationserver.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferLimitsFilter {

    @NotNull
    private String transferType;

    @NotNull
    private String mts;

    @NotNull
    private String country;

    @NotNull
    private String currency;

    @NotNull
    private BigDecimal minAmount;

    @NotNull
    private BigDecimal maxAmount;
}

