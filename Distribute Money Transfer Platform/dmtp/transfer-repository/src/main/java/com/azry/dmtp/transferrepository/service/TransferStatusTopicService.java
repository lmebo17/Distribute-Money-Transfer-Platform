package com.azry.dmtp.transferrepository.service;

import com.azry.dmtp.model.TransferStatusUpdateEvent;

public interface TransferStatusTopicService {

    void updateDatabase(TransferStatusUpdateEvent event);
}
