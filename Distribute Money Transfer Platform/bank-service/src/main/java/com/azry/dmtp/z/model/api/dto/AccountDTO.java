package com.azry.dmtp.z.model.api.dto;

import com.azry.dmtp.z.model.enums.Status;
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
public class AccountDTO {

    @NotNull
    private Long id;

    @NotNull
    private String accountNumber;

    @NotNull
    private Status accountStatus;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private String currency;

    @NotNull
    private Long clientId;

}
