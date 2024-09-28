package com.azry.dmtp.fiintegration.controller;


import com.azry.dmtp.fiintegration.mapper.ParticipantAccountMapper;
import com.azry.dmtp.fiintegration.service.ParticipantAccountService;
import com.azry.dmtp.model.AccountModel;
import com.azry.dmtp.model.BalanceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class ParticipantAccountController {

    private final ParticipantAccountService participantAccountService;

    private final ParticipantAccountMapper participantAccountMapper;

    @GetMapping("/{participantPersonalNumber}")
    public ResponseEntity<List<AccountModel>> getAccounts(@PathVariable Long participantPersonalNumber) {
        return ResponseEntity.ok(participantAccountMapper
                .accountsToParticipantAccountDTOs(participantAccountService.getAccounts(participantPersonalNumber)));
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BalanceModel> getAccountBalance(@PathVariable String accountNumber) {
        return ResponseEntity.ok(participantAccountMapper.balanceToBalanceModel(participantAccountService.getAccountBalance(accountNumber)));
    }
}
