package com.azry.dmtp.transferrepository.mapper;

import com.azry.dmtp.model.ParticipantModel;
import com.azry.dmtp.model.TransferModel;
import com.azry.dmtp.transferrepository.model.entities.Transfer;
import org.springframework.stereotype.Service;

@Service
public class TransferModelToTransferMapper {

    public Transfer transferModelToTransfer(TransferModel transferModel) {
        Transfer transfer = new Transfer();
        transfer.setId(transferModel.getId());
        transfer.setMts(transferModel.getMts());
        if (transferModel.getType() != null) {
            transfer.setType(transferModel.getType().name());
        }

        if (transferModel.getStatus().getStatus() != null) {
            transfer.setStatus(transferModel.getStatus().getStatus().name());
        }
        transfer.setStatusMessage(transferModel.getStatus().getStatusMessage());
        transfer.setStatusTime(transferModel.getStatus().getStatusTime());

        transfer.setTransferNumber(transferModel.getTransferNumber());
        transfer.setAmount(transferModel.getAmount());
        transfer.setCurrency(transferModel.getCurrency());
        transfer.setTransactionTime(transferModel.getCreationTime());
        transfer.setSendCountry(transferModel.getSendCountry());
        transfer.setReceiveCountry(transferModel.getReceiveCountry());

        mapSenderToTransfer(transfer, transferModel.getSender());
        mapReceiverToTransfer(transfer, transferModel.getReceiver());

        return transfer;
    }

    private void mapSenderToTransfer(Transfer transfer, ParticipantModel sender) {
        transfer.setSenderFirstName(sender.getFirstName());
        transfer.setSenderMiddleName(sender.getMiddleName());
        transfer.setSenderLastName(sender.getLastName());
        transfer.setSenderBirthDate(sender.getBirthDate());
        transfer.setSenderBirthCountry(sender.getBirthCountry());
        transfer.setSenderCountry(sender.getCountry());
        transfer.setSenderAddress(sender.getAddress());
        transfer.setSenderMobilePhone(sender.getMobilePhone());
        transfer.setSenderEmail(sender.getEmail());
        transfer.setSenderZipCode(sender.getZipCode());
        transfer.setSenderPersonalNumber(sender.getPersonalNumber());
        transfer.setSenderCitizenship(sender.getCitizenship());
    }

    private void mapReceiverToTransfer(Transfer transfer, ParticipantModel receiver) {
        transfer.setReceiverFirstName(receiver.getFirstName());
        transfer.setReceiverMiddleName(receiver.getMiddleName());
        transfer.setReceiverLastName(receiver.getLastName());
        transfer.setReceiverBirthDate(receiver.getBirthDate());
        transfer.setReceiverBirthCountry(receiver.getBirthCountry());
        transfer.setReceiverCountry(receiver.getCountry());
        transfer.setReceiverAddress(receiver.getAddress());
        transfer.setReceiverMobilePhone(receiver.getMobilePhone());
        transfer.setReceiverEmail(receiver.getEmail());
        transfer.setReceiverZipCode(receiver.getZipCode());
        transfer.setReceiverPersonalNumber(receiver.getPersonalNumber());
        transfer.setReceiverCitizenship(receiver.getCitizenship());
    }
}
