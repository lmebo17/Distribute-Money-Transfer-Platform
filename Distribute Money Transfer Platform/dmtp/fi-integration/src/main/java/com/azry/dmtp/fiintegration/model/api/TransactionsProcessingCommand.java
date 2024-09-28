package com.azry.dmtp.fiintegration.model.api;

import com.azry.dmtp.fiintegration.model.enums.TransactionStatus;
import com.azry.dmtp.model.TransferModel;
import com.azry.dmtp.model.TransferStatusModel;
import lombok.Data;

@Data
public class TransactionsProcessingCommand {

    private int eventSchemaVersion;
    private TransferStatusModel transferStatusModel;
    private TransactionStatus bankStatus;
    private TransferModel transfer;
    private int retryCount = 1;
}
