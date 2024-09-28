package com.azry.dmtp.z.model.entity;

import com.azry.dmtp.z.model.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENTS", schema = "BANK_SERVICE_SCHEMA")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    @SequenceGenerator(name = "seq_generator", schema = "BANK_SERVICE_SCHEMA", sequenceName = "BANK_SERVICE_GLOBAL_SEQUENCE", allocationSize = 1)
    @Column(name = "ID")
    private Long clientId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Column(unique = true)
    private Long personalNumber;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private String address;

    @NotNull
    private String citizenship;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status clientStatus;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @OneToMany(mappedBy = "client")
    @Cascade(CascadeType.ALL)
    @ToString.Exclude
    private List<Account> listOfAccounts;
}
