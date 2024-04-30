package com.notetakingappca.api.key;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientKeyRepository extends JpaRepository<ClientKey, String> {

    @Query("SELECT ck FROM ClientKey ck WHERE ck.clientId = ?1")
    Optional<ClientKey> findByClientId(String clientId);

}
