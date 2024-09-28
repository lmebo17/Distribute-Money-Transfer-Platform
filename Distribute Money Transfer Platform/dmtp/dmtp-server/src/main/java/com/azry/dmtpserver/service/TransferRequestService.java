package com.azry.dmtpserver.service;

import com.azry.dmtp.model.TransferModel;
import com.azry.dmtpserver.model.ReceiveTransferRequest;
import com.azry.dmtpserver.model.SendTransferRequest;

public interface TransferRequestService {
    TransferModel createReceiveTransferRequest(ReceiveTransferRequest receiveTransferRequest);

    TransferModel createSendTransfer(SendTransferRequest sendTransferRequest);
}
