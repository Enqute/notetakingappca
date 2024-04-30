package com.notetakingappca.api.certificate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, String> {

    @Query("SELECT c FROM Certificate c WHERE c.certificateId = ?1")
    Optional<Certificate> findByCertificateId(String certificateId);

    @Query("SELECT c FROM Certificate c WHERE c.clientId = ?1")
    Optional<Certificate> findByClientId(String clientId);

}
