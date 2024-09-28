package com.azry.dmtp.fiintegration.model.api;

import com.azry.dmtp.fiintegration.model.api.dto.ParticipantAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipantAccountsResponse {

    @NotNull
    private List<ParticipantAccountDTO> participantAccounts;
}
