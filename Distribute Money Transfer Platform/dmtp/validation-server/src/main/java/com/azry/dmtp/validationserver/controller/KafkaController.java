package com.azry.dmtp.validationserver.controller;

import com.azry.dmtp.model.TransferStatusUpdateEvent;
import com.azry.dmtp.validationserver.messaging.ValidationServerProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final ValidationServerProducer producer;

    @PostMapping("/post")
    public String sendMessage(@RequestBody TransferStatusUpdateEvent message) {
        producer.sendMessage(message);
        return "Message sent successfully";
    }
}