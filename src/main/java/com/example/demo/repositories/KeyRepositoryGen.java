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
    @Override
    public KeyPair getKeyPair() {
        return Keys.keyPairFor(SignatureAlgorithm.RS256);
    }
}
