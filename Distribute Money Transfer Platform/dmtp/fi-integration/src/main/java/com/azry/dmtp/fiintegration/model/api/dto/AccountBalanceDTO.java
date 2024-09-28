package com.azry.dmtp.fiintegration.model.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceDTO {

    @NotNull
    private BigDecimal balance;

    @NotNull
    private String currency;

    @NotNull
    private String accountNumber;
}
