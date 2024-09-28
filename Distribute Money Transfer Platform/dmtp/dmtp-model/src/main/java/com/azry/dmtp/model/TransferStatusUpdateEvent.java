package com.azry.dmtp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransferStatusUpdateEvent {

    private int eventSchemaVersion;

    private TransferStatusInfo status;

    private TransferModel transfer;

}
