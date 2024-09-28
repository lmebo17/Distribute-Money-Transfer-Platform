package com.azry.dmtp.fiintegration.mapper;

import com.azry.dmtp.fiintegration.model.api.TransactionInitializationRequest;
import com.azry.dmtp.fiintegration.model.api.dto.MoneyTransactionDTO;
import com.azry.dmtp.fiintegration.model.api.dto.TransactionStatusDTO;
import com.azry.dmtp.fiintegration.model.enums.TransactionStatus;
import com.azry.dmtp.z.bankservice.TransactionCreationRequest;
import com.azry.dmtp.z.bankservice.TransactionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MoneyTransactionMapper {

    TransactionCreationRequest transactionInitializationRequestToTransactionDTO(TransactionInitializationRequest request);

    MoneyTransactionDTO transactionDtoToMoneyTransactionDto(TransactionDTO transactionDTO);

    default TransactionStatusDTO toTransactionStatusDto(Long id, String status) {
        return TransactionStatusDTO.builder()
                .transactionId(id)
                .transactionStatus(TransactionStatus.valueOf(status))
                .build();
    }
}
