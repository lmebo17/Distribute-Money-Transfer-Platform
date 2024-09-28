package com.azry.dmtp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferStatusInfo {
    private TransferStatusModel status;
    private String statusMessage;
    private LocalDateTime statusTime;
}
