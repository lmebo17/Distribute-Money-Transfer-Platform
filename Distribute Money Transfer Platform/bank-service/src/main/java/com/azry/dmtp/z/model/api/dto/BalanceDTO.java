package com.azry.dmtp.z.model.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDTO {
    @NotNull
    private BigDecimal balance;

    @NotNull
    private String currency;

    @NotNull
    private String accountNumber;
}
