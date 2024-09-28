package com.azry.dmtp.fiintegration.controller;

import com.azry.dmtp.fiintegration.messaging.FiIntegrationProducer;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final FiIntegrationProducer fiIntegrationProducer;

    @PostMapping("/post")
    public ResponseEntity<String> sendMessage(@RequestBody TransferStatusUpdateEvent message) {
        fiIntegrationProducer.sendTransferStatusMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }
}
