package com.azry.dmtp.z.model.api;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientUpdateRequest {

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String phone;

    private String address;

    private String citizenship;

}
