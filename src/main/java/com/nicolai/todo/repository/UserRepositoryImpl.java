package com.nicolai.todo.repository;

import com.nicolai.todo.domain.User;

import com.nicolai.todo.exception.UserException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;


@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SQL_CREATE_USER = "insert into user(username, password) values (";
    private static final String SQL_SELECT_USER = "select username, password from User where username = ?";

    @Override
    public User create(String username, String password) throws UserException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            jdbcTemplate.execute(SQL_CREATE_USER + "'" + username + "', '" + hashedPassword + "')");
            return new User(username, hashedPassword);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UserException("Failed to register new user");
        }

    }

    @Override
    public User validateUser(String username, String password) throws UserException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_USER, new Object[]{username}, userRowMapper);
            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new UserException("Login feilet, password");
            }
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new UserException("Login feilet, username");
        }
    }

    private RowMapper<User> userRowMapper = ((rs,rowNum) -> {
        return new User(
                rs.getString("username"), rs.getString("password")
        );
    });
}
