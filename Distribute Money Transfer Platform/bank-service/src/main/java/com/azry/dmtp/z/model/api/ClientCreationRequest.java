package com.azry.dmtp.z.model.api;

import com.azry.dmtp.z.model.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientCreationRequest {
    @NotNull
    private Long personalNumber;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

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
    private Status clientStatus;

    @NotNull
    private LocalDate dateOfBirth;

}
