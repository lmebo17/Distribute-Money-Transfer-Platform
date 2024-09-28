package com.azry.dmtp.messaging;

import com.azry.dmtp.model.TransferStatusModel;

import java.util.Map;

public interface StatusMessages {

    Map<TransferStatusModel, String> statusMessages = Map.ofEntries(
            Map.entry(TransferStatusModel.SEND_CREATED, "Type Send Transfer Created"),
            Map.entry(TransferStatusModel.SEND_VALIDATION_REQUESTED, "Type Send Transfer Validation Requested"),
            Map.entry(TransferStatusModel.SEND_VALIDATED, "Type Send Transfer Validated"),
            Map.entry(TransferStatusModel.SEND_VALIDATION_REJECTED, "Type Send Transfer Validation Rejected"),
            Map.entry(TransferStatusModel.SEND_MONEY_COLLECTION_REQUESTED, "Type Send Transfer Money Collection Requested"),
            Map.entry(TransferStatusModel.SEND_MONEY_COLLECTED, "Type Send Transfer Money Collected"),
            Map.entry(TransferStatusModel.SEND_MONEY_COLLECTION_REJECTED, "Type Send Transfer Money Collection Rejected"),
            Map.entry(TransferStatusModel.SEND_REQUESTED, "Type Send Transfer Sending Requested"),
            Map.entry(TransferStatusModel.SEND_SENT, "Type Send Transfer Sent"),
            Map.entry(TransferStatusModel.SEND_REJECTED, "Type Send Transfer Sending Rejected"),
            Map.entry(TransferStatusModel.RECEIVE_CREATED, "Type Receive Transfer Created"),
            Map.entry(TransferStatusModel.RECEIVE_VALIDATION_REQUESTED, "Type Receive Transfer Validation Requested"),
            Map.entry(TransferStatusModel.RECEIVE_VALIDATED, "Type Receive Transfer Validated"),
            Map.entry(TransferStatusModel.RECEIVE_VALIDATION_REJECTED, "Type Receive Transfer Validation Rejected"),
            Map.entry(TransferStatusModel.RECEIVE_PAY_REQUESTED, "Type Receive Transfer Paying Requested"),
            Map.entry(TransferStatusModel.RECEIVE_PAID, "Type Receive Transfer Paid"),
            Map.entry(TransferStatusModel.RECEIVE_PAY_REJECTED, "Type Receive Transfer Paying Rejected"),
            Map.entry(TransferStatusModel.RECEIVE_REQUESTED, "Type Receive Transfer Receiving Requested"),
            Map.entry(TransferStatusModel.RECEIVE_RECEIVED, "Type Receive Transfer Received"),
            Map.entry(TransferStatusModel.RECEIVE_REJECTED, "Type Receive Transfer Receiving Rejected")
    );
}
