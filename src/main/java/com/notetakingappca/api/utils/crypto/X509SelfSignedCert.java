package com.notetakingappca.api.utils.crypto;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;

public class X509SelfSignedCert {

    public static X509Certificate generateSelfSignedCertificate(
            PublicKey subjectPublicKey,
            PrivateKey issuerPrivateKey,
            String username
    ) throws Exception {
        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                buildX500Name("Note Taking App Certificate Authority (CA)").build(),
                new BigInteger(64, new java.security.SecureRandom()),
                new Date(),
                new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000L),
                buildX500Name(username).build(),
                subjectPublicKey);

        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(issuerPrivateKey);
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        return certConverter.getCertificate(certBuilder.build(contentSigner));
    }

    private static X500NameBuilder buildX500Name(String commonName) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, commonName);
        return builder;
    }

    public static X509Certificate loadCertificate(String cert) throws Exception {
        X509CertificateHolder certHolder = new X509CertificateHolder(Base64.getDecoder().decode(cert.getBytes()));
        return new JcaX509CertificateConverter().getCertificate(certHolder);
    }

    public static PublicKey getPublicKey(X509Certificate cert) {
        return cert.getPublicKey();
    }

}
