package com.azry.dmtp.z.model.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private Long Id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
