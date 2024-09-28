package com.azry.dmtp.fiintegration.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@RequiredArgsConstructor
public class SchedulerConfig {

    @Value("${scheduler.config.schedule-reprocessing-delay}")
    private long reprocessingDelayInMillis;

}
