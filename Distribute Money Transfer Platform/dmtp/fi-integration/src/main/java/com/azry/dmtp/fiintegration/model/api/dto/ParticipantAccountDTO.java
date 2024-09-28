package com.azry.dmtp.fiintegration.model.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipantAccountDTO {

    @NotNull
    private String accountNumber;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private String currency;
}

