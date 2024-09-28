package com.azry.dmtp.validationserver.service;

import com.azry.dmtp.validationserver.model.TransferLimit;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class TransferLimitComparator implements Comparator<TransferLimit> {

    @Override
    public int compare(TransferLimit limit1, TransferLimit limit2) {

        int result = compareField(limit1.getTransferType(), limit2.getTransferType());
        if (result != 0) return result;

        result = compareField(limit1.getMts(), limit2.getMts());
        if (result != 0) return result;

        result = compareField(limit1.getCountry(), limit2.getCountry());
        if (result != 0) return result;

        return compareField(limit1.getCurrency(), limit2.getCurrency());
    }

    private int compareField(String value1, String value2) {
        boolean isSpecific1 = isSpecific(value1);
        boolean isSpecific2 = isSpecific(value2);

        if (isSpecific1 && !isSpecific2) {
            return -1;
        } else if (!isSpecific1 && isSpecific2) {
            return 1;
        }
        return 0;
    }

    private boolean isSpecific(String value) {
        return !"*".equals(value);
    }
}
