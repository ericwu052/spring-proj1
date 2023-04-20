package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.exceptions.MyAuthException;
import com.example.demo.exceptions.MyBadRequestException;
import com.example.demo.exceptions.MyResourceNotFoundException;
import com.example.demo.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public User registerUser(String phoneNumber, String name, String password) throws MyBadRequestException {
        Integer count = userRepository.getCountByPhone(phoneNumber);
        if (count > 0)
            throw new MyBadRequestException("phone number already in use");

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        Integer userId = userRepository.create(phoneNumber, name, hashedPassword);
        return userRepository.findById(userId);
    }

    public User validateUser(String phoneNumber, String password) throws MyAuthException {
        return userRepository.findByPhoneAndPassword(phoneNumber, password);
    }

    public User getUserByPhone(String phoneNumber) throws MyResourceNotFoundException {
        return userRepository.findByPhone(phoneNumber);
    }

    public void updateUserName(String phoneNumber, String name) throws MyBadRequestException {
        userRepository.updateNameByPhone(phoneNumber, name);
    }
}
