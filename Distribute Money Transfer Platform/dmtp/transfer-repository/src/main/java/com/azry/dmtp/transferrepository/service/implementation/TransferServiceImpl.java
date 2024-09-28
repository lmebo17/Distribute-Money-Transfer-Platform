package com.azry.dmtp.transferrepository.service.implementation;

import com.azry.dmtp.transferrepository.model.entities.Transfer;
import com.azry.dmtp.transferrepository.repository.TransferRepository;
import com.azry.dmtp.transferrepository.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;

    public Transfer getTransferById(String id) {
        return transferRepository.findById(id).orElse(null);
    }
}
