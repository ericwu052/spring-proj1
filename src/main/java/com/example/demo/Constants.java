package com.example.demo;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.KeyPair;

public class Constants {
    /* TODO how to store keypair instead of generating one? */
    public static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
    public static final long TOKEN_VALIDITY = 2 * 60 * 60 * 1000;

    public static final String INDONESIAN_PHONE_REGEX = "08\\d+";
    public static final String ONE_DIGIT_REGEX = ".*\\d.*";
    public static final String ONE_UPPERCASE_REGEX = ".*[A-Z].*";

}
