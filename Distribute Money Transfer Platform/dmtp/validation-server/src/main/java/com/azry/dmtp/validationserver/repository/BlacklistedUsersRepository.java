package com.azry.dmtp.validationserver.repository;

import com.azry.dmtp.validationserver.model.BlacklistedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlacklistedUsersRepository extends JpaRepository<BlacklistedUser, Long> {

    Optional<BlacklistedUser> findByPersonalNumber(Long personalNumber);

}
