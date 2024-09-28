package com.azry.dmtp.transferrepository.controller;

import com.azry.dmtp.model.TransferModel;
import com.azry.dmtp.transferrepository.mapper.TransferMapper;
import com.azry.dmtp.transferrepository.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    private final TransferMapper transferMapper;

    @QueryMapping
    public TransferModel getTransferById(@Argument String id) {
        return transferMapper.transferToTransferDTO(transferService.getTransferById(id));
    }

}
