package com.azry.dmtp.z.repository;

import com.azry.dmtp.z.model.api.filter.ClientFilter;
import com.azry.dmtp.z.model.entity.Client;
import com.azry.dmtp.z.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT C FROM Client C WHERE " +
            "(:#{#clientFilter.id} IS NULL OR C.clientId = :#{#clientFilter.id}) " +
            "AND (:#{#clientFilter.personalNumber} IS NULL OR C.personalNumber = :#{#clientFilter.personalNumber}) " +
            "AND (:#{#clientFilter.firstName} IS NULL OR C.firstName = :#{#clientFilter.firstName}) " +
            "AND (:#{#clientFilter.lastName} IS NULL OR C.lastName = :#{#clientFilter.lastName}) " +
            "AND (:#{#clientFilter.email} IS NULL OR C.email = :#{#clientFilter.email}) " +
            "AND (:#{#clientFilter.phone} IS NULL OR C.phone = :#{#clientFilter.phone}) " +
            "AND (:#{#clientFilter.address} IS NULL OR C.address = :#{#clientFilter.address}) " +
            "AND (:#{#clientFilter.citizenship} IS NULL OR C.citizenship = :#{#clientFilter.citizenship}) " +
            "AND (:#{#clientFilter.clientStatus} IS NULL OR C.clientStatus = :#{#clientFilter.clientStatus}) " +
            "AND (:#{#clientFilter.dateOfBirth} IS NULL OR C.dateOfBirth = :#{#clientFilter.dateOfBirth})")
    Page<Client> search(Pageable pageable, ClientFilter clientFilter);

    Optional<Client> findByClientIdAndClientStatus(Long id, Status clientStatus);

    Client findByFirstName(String firstName);

    Optional<Client> findByPersonalNumberAndClientStatus(Long personalNumber, Status clientStatus);

    Optional<Client> findByPersonalNumber(Long personalNumber);
}
