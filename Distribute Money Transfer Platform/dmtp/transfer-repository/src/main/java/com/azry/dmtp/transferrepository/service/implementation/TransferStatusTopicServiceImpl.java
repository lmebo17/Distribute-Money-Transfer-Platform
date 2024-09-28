package com.azry.dmtp.transferrepository.service.implementation;

import com.azry.dmtp.messaging.EventSchemaVersion;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import com.azry.dmtp.transferrepository.mapper.TransferMapper;
import com.azry.dmtp.transferrepository.mapper.TransferModelToTransferMapper;
import com.azry.dmtp.transferrepository.model.entities.Transfer;
import com.azry.dmtp.transferrepository.model.entities.TransferStatusLog;
import com.azry.dmtp.transferrepository.repository.TransferRepository;
import com.azry.dmtp.transferrepository.repository.TransferStatusLogRepository;
import com.azry.dmtp.transferrepository.service.TransferStatusTopicService;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TransferStatusTopicServiceImpl implements TransferStatusTopicService {

    private final TransferMapper transferMapper;
    private final TransferStatusLogRepository transferStatusLogRepository;
    private final TransferModelToTransferMapper transferModelToTransferMapper;
    private final TransferRepository transferRepository;

    @WithSpan
    @Transactional
    public void updateDatabase(TransferStatusUpdateEvent event) {
        if (event.getEventSchemaVersion() == EventSchemaVersion.eventSchemaVersion) {
            Transfer updatedTransfer = transferModelToTransferMapper.transferModelToTransfer(event.getTransfer());
            TransferStatusLog log = transferMapper.transferStatusInfoToTransferStatusLog(event.getStatus());
            log.setTransfer(updatedTransfer);
            transferRepository.save(updatedTransfer);
            transferStatusLogRepository.save(log);
        }
    }
}
