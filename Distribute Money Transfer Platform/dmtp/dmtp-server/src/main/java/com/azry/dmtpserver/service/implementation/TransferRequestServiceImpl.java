package com.azry.dmtpserver.service.implementation;

import com.azry.dmtp.messaging.EventSchemaVersion;
import com.azry.dmtp.messaging.StatusMessages;
import com.azry.dmtp.messaging.Topics;
import com.azry.dmtp.model.TransferModel;
import com.azry.dmtp.model.TransferStatusInfo;
import com.azry.dmtp.model.TransferStatusModel;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import com.azry.dmtpserver.mapper.ReceiveTransferRequestMapper;
import com.azry.dmtpserver.mapper.SendTransferRequestMapper;
import com.azry.dmtpserver.messaging.DmtpServerProducer;
import com.azry.dmtpserver.model.ReceiveTransferRequest;
import com.azry.dmtpserver.model.SendTransferRequest;
import com.azry.dmtpserver.service.TransferRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferRequestServiceImpl implements TransferRequestService {

    private final DmtpServerProducer dmtpServerProducer;
    private final ReceiveTransferRequestMapper receiveTransferRequestMapper;
    private final SendTransferRequestMapper sendTransferRequestMapper;

    @Override
    public TransferModel createSendTransfer(SendTransferRequest sendTransferRequest) {

        TransferModel transferModel = sendTransferRequestMapper.toTransferModel(sendTransferRequest);
        TransferStatusInfo transferStatusInfo = initializeTransferInfo(TransferStatusModel.SEND_CREATED);

        transferModel.setId(UUID.randomUUID().toString());
        transferModel.setCreationTime(LocalDateTime.now());
        transferModel.setStatus(transferStatusInfo);

        log.info("Send transfer request: {}", transferModel);

        TransferStatusUpdateEvent event = new TransferStatusUpdateEvent(EventSchemaVersion.eventSchemaVersion, transferStatusInfo, transferModel);
        dmtpServerProducer.sendMessage(event, Topics.TRANSFER_STATUS_TOPIC);
        return transferModel;
    }

    @Override
    public TransferModel createReceiveTransferRequest(ReceiveTransferRequest receiveTransferRequest) {

        TransferModel transferModel = receiveTransferRequestMapper.toTransferModel(receiveTransferRequest);
        TransferStatusInfo transferStatusInfo = initializeTransferInfo(TransferStatusModel.RECEIVE_CREATED);

        transferModel.setStatus(transferStatusInfo);
        transferModel.setId(UUID.randomUUID().toString());
        transferModel.setCreationTime(LocalDateTime.now());

        log.info("Receive transfer request: {}", transferModel);

        TransferStatusUpdateEvent event = new TransferStatusUpdateEvent(EventSchemaVersion.eventSchemaVersion, transferStatusInfo, transferModel);
        dmtpServerProducer.sendMessage(event, Topics.TRANSFER_STATUS_TOPIC);
        return transferModel;
    }

    private TransferStatusInfo initializeTransferInfo(TransferStatusModel status) {
        TransferStatusInfo transferStatusInfo = new TransferStatusInfo();
        transferStatusInfo.setStatusMessage(StatusMessages.statusMessages.get(status));
        transferStatusInfo.setStatusTime(LocalDateTime.now());
        transferStatusInfo.setStatus(status);
        return transferStatusInfo;
    }

}
