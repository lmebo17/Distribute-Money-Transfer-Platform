package com.azry.dmtp.z.model.entity;


import com.azry.dmtp.z.model.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "ACCOUNTS", schema = "BANK_SERVICE_SCHEMA")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    @SequenceGenerator(name = "seq_generator", schema = "BANK_SERVICE_SCHEMA", sequenceName = "BANK_SERVICE_GLOBAL_SEQUENCE", allocationSize = 1)
    @Column(name = "ID")
    private Long accountId;

    @NotNull
    @Column(unique = true)
    private String accountNumber;

    @NotNull
    private String currency;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private BigDecimal balance;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID", nullable = false)
    private Client client;
}