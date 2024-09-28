package com.azry.dmtp.z.model.api.filter;

import com.azry.dmtp.z.model.enums.Status;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientFilter {

    private Long id;
    private Long personalNumber;
    private String firstName;
    private String lastName;

    @Email
    private String email;

    private String phone;
    private String address;
    private String citizenship;
    private Status clientStatus;

    private LocalDate dateOfBirth;

}
