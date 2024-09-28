package com.azry.dmtp.transferrepository.mapper;

import com.azry.dmtp.model.ParticipantModel;
import com.azry.dmtp.transferrepository.model.entities.Transfer;
import org.mapstruct.Mapper;

@Mapper
public class ParticipantMapper {

    static ParticipantModel transferToSenderParticipant(Transfer transfer) {
        if (transfer == null) {
            return null;
        }
        return ParticipantModel.builder()
                .firstName(transfer.getSenderFirstName())
                .middleName(transfer.getSenderMiddleName())
                .lastName(transfer.getSenderLastName())
                .birthDate(transfer.getSenderBirthDate())
                .birthCountry(transfer.getSenderBirthCountry())
                .country(transfer.getSenderCountry())
                .address(transfer.getSenderAddress())
                .mobilePhone(transfer.getSenderMobilePhone())
                .email(transfer.getSenderEmail())
                .zipCode(transfer.getSenderZipCode())
                .personalNumber(transfer.getSenderPersonalNumber())
                .citizenship(transfer.getSenderCitizenship())
                .build();

    }

    static ParticipantModel transferToReceiverParticipant(Transfer transfer) {
        if (transfer == null) {
            return null;
        }
        return ParticipantModel.builder()
                .firstName(transfer.getReceiverFirstName())
                .middleName(transfer.getReceiverMiddleName())
                .lastName(transfer.getReceiverLastName())
                .birthDate(transfer.getReceiverBirthDate())
                .birthCountry(transfer.getReceiverBirthCountry())
                .country(transfer.getReceiverCountry())
                .address(transfer.getReceiverAddress())
                .mobilePhone(transfer.getReceiverMobilePhone())
                .email(transfer.getReceiverEmail())
                .zipCode(transfer.getReceiverZipCode())
                .personalNumber(transfer.getReceiverPersonalNumber())
                .citizenship(transfer.getReceiverCitizenship())
                .build();
    }
}
