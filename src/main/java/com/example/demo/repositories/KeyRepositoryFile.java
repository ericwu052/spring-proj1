package com.example.demo.repositories;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * This key repository is file based. Put your private and public key on the root directory of the project with
 * the following filenames:
 * - publicKey: public key raw format
 * - privateKey: private key raw format
 */
@Repository
@Primary
public class KeyRepositoryFile implements KeyRepository {

    @Override
    public KeyPair getKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, IOException, InvalidKeySpecException {

        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");

        PublicKey publicKey;
        try (FileInputStream publicKeyFis = new FileInputStream(publicKeyFilename)) {
            byte[] publicKeyBytes = publicKeyFis.readAllBytes();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            publicKey = keyFactory.generatePublic(pubKeySpec);
        }

        PrivateKey privateKey;
        try (FileInputStream privateKeyFis = new FileInputStream(privateKeyFilename)) {
            byte[] privateKeyBytes = privateKeyFis.readAllBytes();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(privateKeyBytes);
            privateKey = keyFactory.generatePrivate(pubKeySpec);
        }

        return new KeyPair(publicKey, privateKey);
    }

    private static final String privateKeyFilename = "privateKey";
    private static final String publicKeyFilename = "publicKey";
}
