package com.azry.dmtp.transferrepository.repository;

import com.azry.dmtp.transferrepository.model.entities.TransferStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferStatusLogRepository extends JpaRepository<TransferStatusLog, Long> {

}
