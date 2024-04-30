package com.notetakingappca.api.certificate;

import com.notetakingappca.api.client.ClientRepository;
import com.notetakingappca.api.key.ClientKey;
import com.notetakingappca.api.key.ClientKeyRepository;
import com.notetakingappca.api.key.KeyRepository;
import com.notetakingappca.api.utils.crypto.RSA;
import com.notetakingappca.api.utils.crypto.X509SelfSignedCert;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final ClientRepository clientRepository;
    private final ClientKeyRepository clientKeyRepository;

    public Certificate getCertificate(String clientId) {
        return certificateRepository.findByClientId(clientId).orElseThrow();
    }

    public String generateCertificate(String username) throws Exception {
//        ClientKey caKey = clientKeyRepository.findByClientId(
//                clientRepository.findByUsername("ca").orElseThrow().getClientId()
//        ).orElseThrow();
//        ClientKey clientKey = clientKeyRepository.findByClientId(clientId).orElseThrow();

        KeyPair pair = RSA.generateKeyPair();

        String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());

        File pukFile = new File("publicKey-" + username + ".pub");
        File prkFile = new File("privateKey-" + username + ".pem");
        FileWriter fileWriter = new FileWriter(pukFile);
        fileWriter.write(publicKey);
        fileWriter.close();
        fileWriter = new FileWriter(prkFile);
        fileWriter.write(privateKey);
        fileWriter.close();

        X509Certificate cert = X509SelfSignedCert.generateSelfSignedCertificate(
                pair.getPublic(),
                pair.getPrivate(),
                username
//                clientRepository
//                        .findByClientId(clientId)
//                        .orElseThrow()
//                        .getUsername()
        );
        String certificate = Base64.getEncoder().encodeToString(cert.getEncoded());
        FileWriter writer = new FileWriter("Certificate-" + username + ".crt");
        writer.write(certificate);
        writer.close();
        return certificate;

//        return certificateRepository.save(Certificate.builder()
//                .certificateId(UUID.randomUUID().toString())
//                .certificate(certificate)
//                .clientId(clientId)
//                .build());
    }

}
