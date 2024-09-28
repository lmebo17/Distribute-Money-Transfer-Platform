package com.azry.dmtp.fiintegration.mapper;

import com.azry.dmtp.model.AccountModel;
import com.azry.dmtp.model.BalanceModel;
import com.azry.dmtp.z.bankservice.AccountDTO;
import com.azry.dmtp.z.bankservice.BalanceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipantAccountMapper {

    @Mapping(target = "accountId", source = "id")
    AccountModel accountToAccountModel(AccountDTO accountDTO);

    List<AccountModel> accountsToParticipantAccountDTOs(List<AccountDTO> accountDTOs);

    BalanceModel balanceToBalanceModel(BalanceDTO accountBalance);

}
