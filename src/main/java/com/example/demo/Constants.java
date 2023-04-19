package com.example.demo;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.KeyPair;

public class Constants {
    public static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
    public static final long TOKEN_VALIDITY = 2 * 60 * 60 * 1000;
}
