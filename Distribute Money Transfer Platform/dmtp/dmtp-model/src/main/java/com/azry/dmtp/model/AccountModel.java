package com.azry.dmtp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountModel {
    long accountId;
    String accountNumber;
    String currency;
    String name;
    BigDecimal balance;
}
