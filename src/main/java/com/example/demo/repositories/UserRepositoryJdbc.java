package com.example.demo.repositories;

import com.example.demo.domain.User;
import com.example.demo.exceptions.MyAuthException;
import com.example.demo.exceptions.MyBadRequestException;
import com.example.demo.exceptions.MyResourceNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryJdbc implements UserRepository {

    private static final String SQL_COUNT_BY_PHONE = "SELECT COUNT(*) FROM users WHERE phone_number = ?";
    private static final String SQL_CREATE = "INSERT INTO users(user_id, phone_number, name, hashed_password) " +
            "VALUES (NEXTVAL('users_seq'), ?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT user_id, phone_number, name, hashed_password FROM users WHERE user_id = ?";
    private static final String SQL_FIND_BY_PHONE = "SELECT user_id, phone_number, name, hashed_password FROM users WHERE phone_number = ?";
    public static final String SQL_UPDATE = "UPDATE users SET name = ? WHERE phone_number = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void updateNameByPhone(String phoneNumber, String name) throws MyBadRequestException {
        int affectedRows = 1;
        try {
            affectedRows = jdbcTemplate.update(SQL_UPDATE, name, phoneNumber);
        } catch (Exception e) {
            throw new MyBadRequestException("Invalid request");
        }
        if (affectedRows == 0)
            throw new MyBadRequestException("Phone number doesn't exist");
    }

    @Override
    public User findByPhone(String phoneNumber) throws MyResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_PHONE, userRowMapper, phoneNumber);

        } catch (EmptyResultDataAccessException e) {
            throw new MyResourceNotFoundException("user with the given phone not found");
        }
    }

    @Override
    public User findByPhoneAndPassword(String phoneNumber, String password) throws MyAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_PHONE, userRowMapper, phoneNumber);
            if (!BCrypt.checkpw(password, user.hashedPassword()))
                throw new MyAuthException("Invalid phone number or password");

            return user;

        } catch (EmptyResultDataAccessException e) {
            throw new MyAuthException("Invalid phone number or password");
        }
    }

    @Override
    public Integer getCountByPhone(String phoneNumber) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_PHONE, Integer.class, phoneNumber);
    }

    @Override
    public Integer create(String phoneNumber, String name, String password) throws MyAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, phoneNumber);
                ps.setString(2, name);
                ps.setString(3, hashedPassword);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("user_id");
        } catch (Exception e) {
            throw new MyAuthException("Invalid details. Failed to create account");
        }
    }

    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, userRowMapper, userId);
    }

    private final RowMapper<User> userRowMapper = ((rs, rowNum) -> new User(
            rs.getInt("user_id"),
            rs.getString("phone_number"),
            rs.getString("name"),
            null,
            rs.getString("hashed_password")
    ));
}
