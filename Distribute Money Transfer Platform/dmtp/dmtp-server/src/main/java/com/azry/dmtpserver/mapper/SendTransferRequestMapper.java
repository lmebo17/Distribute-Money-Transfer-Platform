package com.azry.dmtpserver.mapper;

import com.azry.dmtp.model.TransferModel;
import com.azry.dmtpserver.model.SendTransferRequest;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface SendTransferRequestMapper {

    TransferModel toTransferModel(SendTransferRequest transferRequest);
}
