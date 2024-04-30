package com.notetakingappca.api.certificate;

import com.notetakingappca.api.client.ClientDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/certificate")
@AllArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping("/{clientId}")
    public ResponseEntity<Certificate> getCertificate(@PathVariable String clientId) {
        return new ResponseEntity<>(certificateService.getCertificate(clientId), HttpStatus.OK);
    }

    @PostMapping("/{username}")
    public ResponseEntity<String> generateCertificate(@PathVariable String username) throws Exception {
        return new ResponseEntity<>(certificateService.generateCertificate(username), HttpStatus.OK);
    }

}
