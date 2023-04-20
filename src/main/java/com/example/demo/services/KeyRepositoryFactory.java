package com.example.demo.services;

import com.example.demo.app.RepositoryChoice;
import com.example.demo.repositories.AppConfigRepository;
import com.example.demo.repositories.KeyRepository;
import com.example.demo.repositories.KeyRepositoryFile;
import com.example.demo.repositories.KeyRepositoryGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyRepositoryFactory {

    @Autowired
    AppConfigRepository appConfigRepository;

    KeyRepository keyRepository;

    public KeyRepository getKeyRepository() {
        if (keyRepository == null)
            keyRepository = initKeyRepository();
        return keyRepository;
    }

    private KeyRepository initKeyRepository() {
        return appConfigRepository.getRepositoryChoice() == RepositoryChoice.FILE ? new KeyRepositoryFile() : new KeyRepositoryGen();
    }
}
