package com.azry.dmtp.transferrepository.controller;


import com.azry.dmtp.model.TransferStatusLogModel;
import com.azry.dmtp.transferrepository.mapper.TransferMapper;
import com.azry.dmtp.transferrepository.model.api.TransferStatusLogCreationRequest;
import com.azry.dmtp.transferrepository.service.TransferStatusLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TransferStatusLogController {

    private final TransferStatusLogService transferStatusLogService;

    private final TransferMapper transferMapper;

    @QueryMapping
    public TransferStatusLogModel getTransferStatusLogById(@Argument Long id) {
        return transferMapper.transferStatusLogToTransferStatusLogDTO(transferStatusLogService.getTransferStatusLogById(id));
    }

    @MutationMapping
    public TransferStatusLogModel createTransferStatusLog(
            @Argument("request") TransferStatusLogCreationRequest transferStatusLogCreationRequest) {
        return transferMapper.transferStatusLogToTransferStatusLogDTO(transferStatusLogService.createTransferStatusLog(transferStatusLogCreationRequest));
    }

}
