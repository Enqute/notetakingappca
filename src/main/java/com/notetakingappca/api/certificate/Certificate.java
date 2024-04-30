package com.notetakingappca.api.certificate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table
@Data
@Builder
public class Certificate {

    @Id
    @Column(length = 36, updatable = false)
    private String certificateId;

    @Column(nullable = false)
    private String certificate;

    @Column(length = 36, nullable = false)
    private String clientId;

}
