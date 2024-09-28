package com.azry.dmtp.validationserver.service;

import com.azry.dmtp.model.TransferModel;
import com.azry.dmtp.model.TransferTypeModel;
import com.azry.dmtp.validationserver.model.TransferLimit;
import com.azry.dmtp.validationserver.model.TransferLimitsFilter;
import com.azry.dmtp.validationserver.model.ValidationResult;
import com.azry.dmtp.validationserver.repository.TransferLimitsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimitsValidator extends AbstractValidationProcessor {

    private final TransferLimitsRepository transferLimitsRepository;
    private final TransferLimitComparator transferLimitComparator;

    @Override
    public void process(TransferModel transfer, ValidationResult result) {
        String country = transfer.getSendCountry();
        if(transfer.getType() == TransferTypeModel.SEND) {
            country = transfer.getReceiveCountry();
        }

        TransferLimitsFilter filter = TransferLimitsFilter.builder()
                .transferType(transfer.getType().name())
                .mts(transfer.getMts())
                .country(country)
                .currency(transfer.getCurrency())
                .build();

        List<TransferLimit> applicableLimits = transferLimitsRepository.search(filter);

        Optional<TransferLimit> chosenLimitOpt = getSuitableLimit(applicableLimits);

        chosenLimitOpt.ifPresent(limit -> {
            TransferLimit chosenLimit = chosenLimitOpt.get();
            int compareMinValue = transfer.getAmount().compareTo(chosenLimit.getMinAmount());
            int compareMaxValue = transfer.getAmount().compareTo(chosenLimit.getMaxAmount());
            boolean isOutOfLimits = compareMinValue < 0 || compareMaxValue > 0;

            if(isOutOfLimits){
                log.info("Money amount for transfer with id: {} is out of bounds", transfer.getId());
                result.getMessages().add("Money amount for transfer with id: " + transfer.getId() + " is out of bounds");
            }
            result.setTransferValid(result.isTransferValid() && !isOutOfLimits);
        });
    }

    private Optional<TransferLimit> getSuitableLimit(List<TransferLimit> applicableLimits) {
        return applicableLimits.stream()
                .reduce((prev, curr) -> transferLimitComparator.compare(prev, curr) <= 0 ? prev : curr);
    }

}
