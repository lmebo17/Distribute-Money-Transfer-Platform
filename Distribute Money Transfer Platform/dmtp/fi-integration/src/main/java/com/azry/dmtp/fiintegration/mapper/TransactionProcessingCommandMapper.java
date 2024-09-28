package com.azry.dmtp.fiintegration.mapper;

import com.azry.dmtp.fiintegration.model.api.TransactionsProcessingCommand;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionProcessingCommandMapper {

    TransactionsProcessingCommand toTransactionProcessingCommand(TransferStatusUpdateEvent event);

    TransferStatusUpdateEvent toTransferStatusUpdateEvent(TransactionsProcessingCommand command);
}