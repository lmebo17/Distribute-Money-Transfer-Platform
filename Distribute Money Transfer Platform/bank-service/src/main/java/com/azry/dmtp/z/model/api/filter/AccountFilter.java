package com.azry.dmtp.z.model.api.filter;

import com.azry.dmtp.z.model.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountFilter {

    @NotNull
    private Status status;

    @NotNull
    private String currency;

    @NotNull
    private Long clientId;

    @NotNull
    private String accountNumber;

}
