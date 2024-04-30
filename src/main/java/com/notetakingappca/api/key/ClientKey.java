package com.notetakingappca.api.key;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientKey {

    @Id
    @Column(length = 36, updatable = false)
    private String clientKeyId;

    @Column(length = 36, updatable = false)
    private String clientId;

    @Column(nullable = false, updatable = false, columnDefinition = "TEXT")
    private String publicKey;

    @Column(nullable = false, updatable = false, columnDefinition = "TEXT")
    private String privateKey;

}
