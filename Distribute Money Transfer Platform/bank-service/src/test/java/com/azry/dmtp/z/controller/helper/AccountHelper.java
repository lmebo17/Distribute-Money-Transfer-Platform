package com.azry.dmtp.z.controller.helper;

import com.azry.dmtp.z.model.api.PagingRequest;
import com.azry.dmtp.z.model.entity.Account;
import com.azry.dmtp.z.model.enums.Status;

import java.math.BigDecimal;

public class AccountHelper {


    public static Account account() {
        return Account.builder()
                .accountNumber("123456789")
                .currency("GEL")
                .status(Status.ACTIVE)
                .balance(BigDecimal.valueOf(222))
                .build();
    }

    public static Account account2() {
        return Account.builder()
                .accountNumber("1122334455")
                .currency("GEL")
                .status(Status.ACTIVE)
                .balance(BigDecimal.valueOf(333))
                .build();
    }

    public static PagingRequest pagingRequest() {
        return PagingRequest.builder()
                .page(0)
                .size(10)
                .build();
    }
}