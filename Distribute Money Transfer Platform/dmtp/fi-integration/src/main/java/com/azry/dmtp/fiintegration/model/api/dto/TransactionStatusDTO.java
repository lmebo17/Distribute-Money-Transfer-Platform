package com.azry.dmtp.fiintegration.model.api.dto;

import com.azry.dmtp.fiintegration.model.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatusDTO {

    @NotNull
    private Long transactionId;

    @NotNull
    private TransactionStatus transactionStatus;

}
