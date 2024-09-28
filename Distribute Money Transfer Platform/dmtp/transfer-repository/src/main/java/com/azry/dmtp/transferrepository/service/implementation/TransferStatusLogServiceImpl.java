package com.azry.dmtp.transferrepository.service.implementation;

import com.azry.dmtp.transferrepository.model.api.TransferStatusLogCreationRequest;
import com.azry.dmtp.transferrepository.model.entities.Transfer;
import com.azry.dmtp.transferrepository.model.entities.TransferStatusLog;
import com.azry.dmtp.transferrepository.repository.TransferRepository;
import com.azry.dmtp.transferrepository.repository.TransferStatusLogRepository;
import com.azry.dmtp.transferrepository.service.TransferStatusLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferStatusLogServiceImpl implements TransferStatusLogService {

    private final TransferStatusLogRepository transferStatusLogRepository;

    private final TransferRepository transferRepository;

    public TransferStatusLog getTransferStatusLogById(Long id) {
        return transferStatusLogRepository.findById(id).orElse(null);
    }

    public TransferStatusLog createTransferStatusLog(
            TransferStatusLogCreationRequest transferStatusLogCreationRequest) {

        Optional<Transfer> transfer = transferRepository.findById(transferStatusLogCreationRequest.getTransferId());
        if (transfer.isPresent()) {
            TransferStatusLog statusLog = new TransferStatusLog();
            statusLog.setStatus(transferStatusLogCreationRequest.getStatus());
            statusLog.setStatusMessage(transferStatusLogCreationRequest.getStatusMessage());
            statusLog.setStatusTime(LocalDateTime.parse(transferStatusLogCreationRequest.getStatusTime()));
            statusLog.setTransfer(transfer.get());
            statusLog = transferStatusLogRepository.save(statusLog);
            return statusLog;
        } else {
            throw new RuntimeException("Transfer not found with id: " + transferStatusLogCreationRequest.getTransferId());
        }
    }
}
