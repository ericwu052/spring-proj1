package com.example.demo.repositories;

import com.example.demo.domain.User;
import com.example.demo.exceptions.MyAuthException;
import com.example.demo.exceptions.MyBadRequestException;
import com.example.demo.exceptions.MyResourceNotFoundException;

public interface UserRepository {
    Integer getCountByPhone(String phoneNumber);

    Integer create(String phoneNumber, String name, String hashedPassword) throws MyAuthException;

    User findById(Integer userId);
    User findByPhoneAndPassword(String phoneNumber, String password);
    User findByPhone(String phoneNumber) throws MyResourceNotFoundException;

    void updateNameByPhone(String phoneNumber, String name) throws MyBadRequestException;
}
