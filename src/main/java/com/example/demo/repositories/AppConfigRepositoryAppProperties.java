package com.example.demo.repositories;

import com.example.demo.app.RepositoryChoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

@Repository
public class AppConfigRepositoryAppProperties implements AppConfigRepository {

    @Autowired
    private Environment env;

    @Override
    public RepositoryChoice getRepositoryChoice() {
        String useKeyStr = env.getProperty("myapp.key.useFiles");
        assert useKeyStr != null;
        return useKeyStr.equals("true") ? RepositoryChoice.FILE : RepositoryChoice.GENERATED;
    }
}
