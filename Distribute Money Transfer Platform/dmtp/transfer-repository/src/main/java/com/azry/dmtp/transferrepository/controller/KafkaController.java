package com.azry.dmtp.transferrepository.controller;

import com.azry.dmtp.model.TransferStatusUpdateEvent;
import com.azry.dmtp.transferrepository.messaging.TransferRepositoryProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final TransferRepositoryProducer transferRepositoryProducer;

    @PostMapping("/post")
    public String sendMessage(@RequestBody TransferStatusUpdateEvent message) {
        transferRepositoryProducer.sendMessage(message);
        return "Message sent successfully";
    }
}