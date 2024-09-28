package com.azry.dmtp.fiintegration.service.implementation;

import com.azry.dmtp.fiintegration.config.handler.exception.BadRequestException;
import com.azry.dmtp.fiintegration.config.handler.exception.NotFoundException;
import com.azry.dmtp.fiintegration.config.handler.exception.ServiceUnableException;
import com.azry.dmtp.fiintegration.service.ParticipantAccountService;
import com.azry.dmtp.z.bankservice.AccountDTO;
import com.azry.dmtp.z.bankservice.BalanceDTO;
import com.azry.dmtp.z.bankservice.ClientDTO;
import com.azry.dmtp.z.bankservice.api.AccountsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantAccountServiceImpl implements ParticipantAccountService {

    private final AccountsApi accountsApi;
    private final ParticipantServiceImpl participantService;

    public List<AccountDTO> getAccounts(Long participantPersonalNumber) {
        ClientDTO participant = participantService.getParticipant(participantPersonalNumber, true);
        try {
            return accountsApi.getAccounts(participant.getId());
        } catch (ServiceUnableException | NotFoundException exception) {
            throw exception;
        } catch (Exception ex) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), "Invalid request data");
        }
    }

    public BalanceDTO getAccountBalance(String accountNumber) {
        try {

            return accountsApi.checkBalance(accountNumber);
        } catch (ResourceAccessException rae) {
            throw new ServiceUnableException(HttpStatus.SERVICE_UNAVAILABLE.value(), rae.getMessage(), "BankService is not responding");
        } catch (Exception ex) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), ex.getMessage(), "Bank Service can't find participant");
        }
    }


}
