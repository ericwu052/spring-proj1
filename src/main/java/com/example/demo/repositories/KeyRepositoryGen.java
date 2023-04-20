package com.example.demo.repositories;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Repository;

import java.security.KeyPair;

/**
 * This key repository generate the keys.
 */
@Repository
public class KeyRepositoryGen implements KeyRepository {

    private final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    @Override
    public KeyPair getKeyPair() {
        return keyPair;
    }
}
