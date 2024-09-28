package com.azry.dmtpserver.mapper;

import com.azry.dmtp.model.TransferModel;
import com.azry.dmtpserver.model.ReceiveTransferRequest;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ReceiveTransferRequestMapper {

    TransferModel toTransferModel(ReceiveTransferRequest receiveTransferRequest);
}
