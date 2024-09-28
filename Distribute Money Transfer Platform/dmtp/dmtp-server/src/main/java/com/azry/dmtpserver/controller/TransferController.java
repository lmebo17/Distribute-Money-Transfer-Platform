package com.azry.dmtpserver.controller;

import com.azry.dmtp.model.TransferModel;
import com.azry.dmtpserver.model.ReceiveTransferRequest;
import com.azry.dmtpserver.model.SendTransferRequest;
import com.azry.dmtpserver.service.TransferRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TransferController {

    private final TransferRequestService transferRequestService;

    @PostMapping("/create-receive-transfer")
    public ResponseEntity<TransferModel> createReceiveTransferRequest(@RequestBody ReceiveTransferRequest receiveTransferRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transferRequestService.createReceiveTransferRequest(receiveTransferRequest));
    }

    @PostMapping("/create-send-transfer")
    public ResponseEntity<TransferModel> createSendTransferRequest(@RequestBody SendTransferRequest sendTransferRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transferRequestService.createSendTransfer(sendTransferRequest));
    }

}
