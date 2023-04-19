package com.example.demo.repositories;

import com.example.demo.domain.User;
import com.example.demo.exceptions.MyAuthException;

public interface UserRepository {
    Integer getCountByPhone(String phoneNumber);

    Integer create(String phoneNumber, String name, String password) throws MyAuthException;

    User findById(Integer userId);
}
