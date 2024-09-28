package com.azry.dmtp.transferrepository.service;

import com.azry.dmtp.transferrepository.model.entities.Transfer;


public interface TransferService {

    Transfer getTransferById(String id);
}
