package com.azry.dmtpserver.model;


import com.azry.dmtp.model.ParticipantModel;
import com.azry.dmtp.model.TransferTypeModel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SendTransferRequest {
    @NotNull
    String mts;

    @NotNull
    TransferTypeModel type;

    @NotNull
    BigDecimal amount;

    @NotNull
    String currency;

    @NotNull
    String sendCountry;

    @NotNull
    String receiveCountry;

    @NotNull
    ParticipantModel sender;

    @NotNull
    ParticipantModel receiver;
}
