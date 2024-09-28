package com.azry.dmtp.z.model.entity;

import com.azry.dmtp.z.model.enums.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRANSACTIONS", schema = "BANK_SERVICE_SCHEMA")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    @SequenceGenerator(name = "seq_generator", schema = "BANK_SERVICE_SCHEMA", sequenceName = "BANK_SERVICE_GLOBAL_SEQUENCE", allocationSize = 1)
    @Column(name = "ID")
    private Long transactionId;

    @NotNull
    @ManyToOne
    private Account senderAccount;

    @NotNull
    @ManyToOne
    private Account receiverAccount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @NotNull
    private LocalDateTime creationDate;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String currency;

}