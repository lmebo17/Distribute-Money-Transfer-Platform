package com.azry.dmtp.transferrepository.service;

import com.azry.dmtp.transferrepository.model.api.TransferStatusLogCreationRequest;
import com.azry.dmtp.transferrepository.model.entities.TransferStatusLog;


public interface TransferStatusLogService {

    TransferStatusLog getTransferStatusLogById(Long id);

    TransferStatusLog createTransferStatusLog(
            TransferStatusLogCreationRequest transferStatusLogCreationRequest);
}
