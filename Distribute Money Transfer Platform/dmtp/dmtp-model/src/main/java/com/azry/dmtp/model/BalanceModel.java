package com.azry.dmtp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BalanceModel {

    private BigDecimal balance;
    private String currency;
    private String accountNumber;
}
