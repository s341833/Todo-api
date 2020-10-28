package com.nicolai.todo.repository;

import com.nicolai.todo.domain.User;
import com.nicolai.todo.exception.UserException;

public interface UserRepository {

    User create(String username, String password) throws UserException;

    User validateUser(String username, String password) throws UserException;
}
