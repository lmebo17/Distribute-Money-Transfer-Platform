package com.azry.dmtp.validationserver.service;

import com.azry.dmtp.model.TransferModel;
import com.azry.dmtp.validationserver.model.ValidationResult;
import com.azry.dmtp.validationserver.repository.BlacklistedUsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantValidator extends AbstractValidationProcessor {

    private final BlacklistedUsersRepository blacklistedUsersRepository;

    @Override
    public void process(TransferModel transfer, ValidationResult result) {
        Long senderPersonalNumber = Long.parseLong(transfer.getSender().getPersonalNumber());
        Long receiverPersonalNumber = Long.parseLong(transfer.getReceiver().getPersonalNumber());

        if (blacklistedUsersRepository.findByPersonalNumber(senderPersonalNumber).isPresent()) {
            result.setTransferValid(false);
            log.info("Sender with personal number: {} is blacklisted", transfer.getSender().getPersonalNumber());
            result.getMessages().add("Sender with personal number: " + transfer.getSender().getPersonalNumber() + " is blacklisted");
        }

        if (blacklistedUsersRepository.findByPersonalNumber(receiverPersonalNumber).isPresent()) {
            result.setTransferValid(false);
            log.info("Receiver {} is blacklisted", transfer.getSender().getPersonalNumber());
            result.getMessages().add("Receiver with personal number: " + transfer.getReceiver().getPersonalNumber() + " is blacklisted");
        }

    }
}
