package com.azry.dmtp.transferrepository.repository;

import com.azry.dmtp.transferrepository.model.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    Optional<Transfer> findById(String id);
}
