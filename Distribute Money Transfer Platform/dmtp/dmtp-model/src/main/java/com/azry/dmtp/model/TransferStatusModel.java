package com.azry.dmtp.model;

public enum TransferStatusModel {
    // send transfer statuses
    SEND_CREATED,
    SEND_VALIDATION_REQUESTED,
    SEND_VALIDATED,
    SEND_VALIDATION_REJECTED,
    SEND_MONEY_COLLECTION_REQUESTED,
    SEND_MONEY_COLLECTED,
    SEND_MONEY_COLLECTION_REJECTED,
    SEND_REQUESTED,
    SEND_SENT,
    SEND_REJECTED,
    // receive transfer statuses
    RECEIVE_AVAILABLE,
    RECEIVE_CREATED,
    RECEIVE_VALIDATION_REQUESTED,
    RECEIVE_VALIDATED,
    RECEIVE_VALIDATION_REJECTED,
    RECEIVE_REQUESTED,
    RECEIVE_RECEIVED,
    RECEIVE_REJECTED,
    RECEIVE_PAY_REQUESTED,
    RECEIVE_PAID,
    RECEIVE_PAY_REJECTED,
}
