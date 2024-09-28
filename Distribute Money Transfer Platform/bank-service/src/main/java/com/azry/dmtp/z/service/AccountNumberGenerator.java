package com.azry.dmtp.z.service;

import com.azry.dmtp.z.model.entity.Account;
import com.azry.dmtp.z.repository.AccountRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.random.RandomGenerator;

@Data
@Service
@RequiredArgsConstructor
public class AccountNumberGenerator {
    private final RandomGenerator random = RandomGenerator.getDefault();
    private final AccountRepository accountRepository;

    public String generateAccountNumber(String currency, int digits) {
        int lowerBound = (int) Math.pow(10, digits - 1);
        int upperBound = (int) (Math.pow(10, digits) - 1);

        while (true) {
            long randomNumber = random.nextInt(lowerBound, upperBound);
            String accountNumber = currency + randomNumber;
            Optional<Account> existingAcc = accountRepository.findAccountByAccountNumber(accountNumber);
            if (existingAcc.isEmpty()) {
                return accountNumber;
            }
        }
    }
}
