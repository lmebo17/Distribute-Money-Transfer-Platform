package com.azry.dmtp.transferrepository.mapper;


import com.azry.dmtp.model.ParticipantModel;
import com.azry.dmtp.model.TransferModel;
import com.azry.dmtp.model.TransferStatusInfo;
import com.azry.dmtp.model.TransferStatusLogModel;
import com.azry.dmtp.model.TransferStatusModel;
import com.azry.dmtp.model.TransferTypeModel;
import com.azry.dmtp.transferrepository.model.entities.Transfer;
import com.azry.dmtp.transferrepository.model.entities.TransferStatusLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    @Mapping(target = "type", qualifiedByName = "transferToTransferType")
    @Mapping(target = "status", source = "transfer", qualifiedByName = "transferToTransferStatusInfo")
    @Mapping(target = "sender", source = "transfer", qualifiedByName = "transferToSenderParticipantDTO")
    @Mapping(target = "receiver", source = "transfer", qualifiedByName = "transferToReceiverParticipantDTO")
    @Mapping(target = "creationTime", source = "transactionTime")
    TransferModel transferToTransferDTO(Transfer transfer);

    @Named("transferToTransferStatusInfo")
    @Mapping(target = "status", qualifiedByName = "transferToTransferStatus")
    TransferStatusInfo transferToTransferStatusInfo(Transfer transfer);

    @Named("transferToTransferStatus")
    default TransferStatusModel transferToTransferStatus(String status) {
        return TransferStatusModel.valueOf(status);
    }

    @Named("transferToTransferType")
    default TransferTypeModel transferTypeFromString(String type) {
        return TransferTypeModel.valueOf(type);
    }

    @Named("transferToSenderParticipantDTO")
    default ParticipantModel transferToSenderParticipantDTO(Transfer transfer) {
        return ParticipantMapper.transferToSenderParticipant(transfer);
    }

    @Named("transferToReceiverParticipantDTO")
    default ParticipantModel transferToReceiverParticipantDTO(Transfer transfer) {
        return ParticipantMapper.transferToReceiverParticipant(transfer);
    }

    @Mapping(source = "transferStatusLog", target = "status", qualifiedByName = "transferStatusLogToTransferStatusInfo")
    TransferStatusLogModel transferStatusLogToTransferStatusLogDTO(TransferStatusLog transferStatusLog);

    @Named("transferStatusLogToTransferStatus")
    default TransferStatusModel transferStatusLogToTransferStatus(TransferStatusLog transferStatusLog) {
        return TransferStatusModel.valueOf(transferStatusLog.getStatus());
    }

    @Named("transferStatusLogToTransferStatusInfo")
    @Mapping(source = "transferStatusLog", target = "status", qualifiedByName = "transferStatusLogToTransferStatus")
    TransferStatusInfo transferStatusLogToTransferStatusInfo(TransferStatusLog transferStatusLog);

    TransferStatusLog transferStatusInfoToTransferStatusLog(TransferStatusInfo transferStatusInfo);

}
