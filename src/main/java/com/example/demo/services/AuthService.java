package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.exceptions.MyAuthException;
import com.example.demo.repositories.UserRepository;

public class AuthService {

    UserRepository userRepository;

    public User registerUser(String phoneNumber, String name, String password) {
        Integer count = userRepository.getCountByPhone(phoneNumber);
        if (count > 0)
            throw new MyAuthException("phone number already in use");

        Integer userId = userRepository.create(phoneNumber, name, password);
        return userRepository.findById(userId);
    }
}
