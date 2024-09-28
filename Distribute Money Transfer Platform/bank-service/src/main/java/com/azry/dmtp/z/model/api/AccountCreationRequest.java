package com.azry.dmtp.z.model.api;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreationRequest {

    @NotNull
    private BigDecimal balance;

    @NotNull
    private String currency;

    @NotNull
    private Long clientId;
}
