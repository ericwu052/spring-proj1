package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.exceptions.MyAuthException;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public User registerUser(String phoneNumber, String name, String password) {
        Integer count = userRepository.getCountByPhone(phoneNumber);
        if (count > 0)
            throw new MyAuthException("phone number already in use");

        Integer userId = userRepository.create(phoneNumber, name, password);
        return userRepository.findById(userId);
    }
}
