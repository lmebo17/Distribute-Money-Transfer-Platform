package com.azry.dmtp.z.model.entity;

import com.azry.dmtp.z.converter.MethodArgumentsConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LOG_ENTRIES", schema = "BANK_SERVICE_SCHEMA")
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    @SequenceGenerator(name = "seq_generator", schema = "BANK_SERVICE_SCHEMA", sequenceName = "BANK_SERVICE_GLOBAL_SEQUENCE", allocationSize = 1)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String methodName;

    @NotNull
    @Convert(converter = MethodArgumentsConverter.class)
    private Object[] methodArguments;

    @NotNull
    private LocalDateTime callTime;
}
