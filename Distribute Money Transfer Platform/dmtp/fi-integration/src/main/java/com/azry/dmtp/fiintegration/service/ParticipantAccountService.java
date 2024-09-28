package com.azry.dmtp.fiintegration.service;

import com.azry.dmtp.z.bankservice.AccountDTO;
import com.azry.dmtp.z.bankservice.BalanceDTO;

import java.util.List;

public interface ParticipantAccountService {

    List<AccountDTO> getAccounts(Long participantPersonalNumber);

    BalanceDTO getAccountBalance(String accountNumber);
}
