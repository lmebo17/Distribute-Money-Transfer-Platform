package com.azry.dmtp.validationserver.world;

import com.azry.dmtp.model.TransferStatusUpdateEvent;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
@Data
public class CucumberWorld {
    private TransferStatusUpdateEvent transferStatusUpdateEvent;
    private Boolean valid;
}
