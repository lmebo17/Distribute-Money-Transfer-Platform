package com.azry.dmtp.z.service.implementation;

import com.azry.dmtp.z.model.entity.LogEntry;
import com.azry.dmtp.z.repository.LogEntryRepository;
import com.azry.dmtp.z.service.LogEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogEntryServiceImpl implements LogEntryService {

    private final LogEntryRepository logEntryRepository;

    public LogEntry addLogEntry(LogEntry logEntry) {
        return logEntryRepository.save(logEntry);
    }
}
