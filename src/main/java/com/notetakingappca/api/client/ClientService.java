package com.notetakingappca.api.client;

import com.notetakingappca.api.key.ClientKeyRepository;
import com.notetakingappca.api.key.ClientKey;
import com.notetakingappca.api.key.Key;
import com.notetakingappca.api.key.KeyRepository;
import com.notetakingappca.api.utils.crypto.AES;
import com.notetakingappca.api.utils.crypto.RSA;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientKeyRepository clientKeyRepository;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClient(String clientId) {
        return clientRepository.findByClientId(clientId);
    }

    public Client createClient(ClientDto clientDto) throws Exception {
        ClientKey caKey = clientKeyRepository.findByClientId(
                clientRepository.findByUsername("ca").orElseThrow().getClientId()
        ).orElseThrow();
        KeyPair keyPair = RSA.generateKeyPair();
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = RSA.encrypt(
                Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()),
                caKey.getPublicKey()
        );

        Client client = clientRepository.save(Client.builder()
                .clientId(UUID.randomUUID().toString())
                .username(clientDto.getUsername())
                .build());
        ClientKey clientKey = ClientKey.builder()
                .clientKeyId(UUID.randomUUID().toString())
                .clientId(client.getClientId())
                .publicKey(publicKey)
                .privateKey(privateKey)
                .build();
        clientKeyRepository.save(clientKey);
        return client;
    }

}
