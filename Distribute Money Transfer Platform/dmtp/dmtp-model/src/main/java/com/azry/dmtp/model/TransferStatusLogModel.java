package com.azry.dmtp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransferStatusLogModel {
    private long id;
    private TransferModel transfer;
    private TransferStatusInfo status;
}
