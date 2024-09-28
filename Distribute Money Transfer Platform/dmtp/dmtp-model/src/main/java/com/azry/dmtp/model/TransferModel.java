package com.azry.dmtp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferModel {
    String id;
    String mts;
    TransferTypeModel type;
    TransferStatusInfo status = new TransferStatusInfo();
    String transferNumber;
    BigDecimal amount;
    String currency;
    LocalDateTime creationTime;
    String sendCountry;
    String receiveCountry;
    ParticipantModel sender;
    ParticipantModel receiver;
}
