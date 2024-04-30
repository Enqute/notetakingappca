package com.notetakingappca.api.key;

import com.notetakingappca.api.client.ClientRepository;
import com.notetakingappca.api.utils.crypto.AES;
import com.notetakingappca.api.utils.crypto.RSA;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

@Service
@AllArgsConstructor
public class KeyService {

    private final KeyRepository keyRepository;
    private final ClientKeyRepository clientKeyRepository;
    private final ClientRepository clientRepository;

    public Key createKey(String userId) throws Exception {
        HashMap<String, String> keys = generateAllKeys(new SecureRandom("1qaz2wsx3edc4rfv5tgb6yhn7ujm8ik,9ol.0p;/".getBytes()).toString());
        Key key = Key.builder()
                .keyId(UUID.randomUUID().toString())
                .userId(userId)
                .symmetricKey(keys.get("symmetricKey"))
                .publicKey(keys.get("publicKey"))
                .privateKey(keys.get("privateKey"))
                .build();
        return keyRepository.save(key);
    }

    private HashMap<String, String> generateAllKeys(String password) throws Exception {
        HashMap<String, String> keys = new HashMap<>();

        byte[] hashedByte = MessageDigest.getInstance("SHA-512").digest(password.getBytes());
        String hashedPass = Base64.getEncoder().encodeToString(hashedByte);
        byte[] randomBytes = new SecureRandom().generateSeed(32);
        String salt = Base64.getEncoder().encodeToString(randomBytes);
        String symmetricKey = Base64.getEncoder().encodeToString(AES.generateKey(hashedPass, salt).getEncoded());

        KeyPair keyPair = RSA.generateKeyPair();
        String p_puk = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String p_prk = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        keys.put("symmetric_key", RSA.encrypt(symmetricKey, p_puk));

        ClientKey clientKey = clientKeyRepository.findByClientId(
                clientRepository.findByUsername("ca").orElseThrow().getClientId()
        ).orElseThrow();

        String c1_prk = RSA.encrypt(p_prk, clientKey.getPublicKey());
        keys.put("public_key", p_puk);
        keys.put("private_key", c1_prk);

        return keys;
    }

    public Key getKey(String userId) {
        return keyRepository.findByUserId(userId).orElseThrow();
    }
}
