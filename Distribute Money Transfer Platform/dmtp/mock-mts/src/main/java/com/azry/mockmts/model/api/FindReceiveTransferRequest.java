package com.azry.mockmts.model.api;

import lombok.Data;

@Data
public class FindReceiveTransferRequest {
    String transferNumber;
    String mts;
}
