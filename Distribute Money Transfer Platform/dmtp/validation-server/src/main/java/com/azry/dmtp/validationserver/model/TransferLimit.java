package com.azry.dmtp.validationserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRANSFER_LIMITS", schema = "VALIDATION_SCHEMA")
public class TransferLimit {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String transferType;

    @NotNull
    private String mts;

    @NotNull
    private String country;

    @NotNull
    private String currency;

    @NotNull
    private BigDecimal minAmount;

    @NotNull
    private BigDecimal maxAmount;
}
