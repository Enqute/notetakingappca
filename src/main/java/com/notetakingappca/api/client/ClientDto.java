package com.notetakingappca.api.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDto {

    private String clientId;
    private String username;

}
