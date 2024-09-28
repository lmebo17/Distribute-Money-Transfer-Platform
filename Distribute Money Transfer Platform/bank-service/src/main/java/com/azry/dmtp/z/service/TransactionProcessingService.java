package com.azry.dmtp.z.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public interface TransactionProcessingService {
    @Scheduled(initialDelayString = "#{@bankServiceProperties.transactionSchedulingInitialDelay}", fixedDelayString = "#{@bankServiceProperties.transactionSchedulingFixedRate}")
    void process();
}
