package com.example.demo.repositories;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

public interface KeyRepository {

    KeyPair getKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, IOException, InvalidKeySpecException;
}
