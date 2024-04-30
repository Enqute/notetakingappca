package com.notetakingappca.api.key;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Key {

    @Id
    @Column(length = 36, updatable = false)
    private String keyId;

    @Column(length = 36, updatable = false)
    private String userId;

    @Column(nullable = false, updatable = false, columnDefinition = "TEXT")
    private String symmetricKey;

    @Column(nullable = false, updatable = false, columnDefinition = "TEXT")
    private String publicKey;

    @Column(nullable = false, updatable = false, columnDefinition = "TEXT")
    private String privateKey;

}
