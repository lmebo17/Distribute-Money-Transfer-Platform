package com.azry.mockmts.controller;

import com.azry.dmtp.model.TransferModel;
import com.azry.mockmts.model.api.FindReceiveTransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {

    @GetMapping("/transfer")
    public ResponseEntity<TransferModel> getTransfer(FindReceiveTransferRequest transferRequest) {
        return null;
    }

}
