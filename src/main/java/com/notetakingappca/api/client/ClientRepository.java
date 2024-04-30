package com.notetakingappca.api.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {

    @Query("SELECT c FROM Client c where c.clientId = ?1")
    Optional<Client> findByClientId(String clientId);

    @Query("SELECT c FROM Client c where c.username = ?1")
    Optional<Client> findByUsername(String username);
}
