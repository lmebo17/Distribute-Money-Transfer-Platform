package com.azry.dmtp.transferrepository.model.api;

import lombok.Data;

@Data
public class TransferStatusLogCreationRequest {

    private String status;
    private String statusMessage;
    private String statusTime;
    private String transferId;
}
