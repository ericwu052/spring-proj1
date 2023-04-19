package com.example.demo.repositories;

import com.example.demo.domain.User;
import com.example.demo.exceptions.MyAuthException;
import com.example.demo.exceptions.MyBadRequestException;

public interface UserRepository {
    Integer getCountByPhone(String phoneNumber);

    Integer create(String phoneNumber, String name, String password) throws MyAuthException;

    User findById(Integer userId);
    User findByPhoneAndPassword(String phoneNumber, String password);
    User findByPhone(String phoneNumber) throws MyAuthException;

    void updateNameByPhone(String phoneNumber, String name) throws MyBadRequestException;
}
