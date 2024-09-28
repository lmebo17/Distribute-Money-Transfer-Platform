package com.azry.dmtp.z.service;

import com.azry.dmtp.z.model.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public interface MakeTransferService {
    void makeTransfer(Transaction transaction);
}
