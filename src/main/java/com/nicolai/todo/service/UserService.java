package com.nicolai.todo.service;

import com.nicolai.todo.domain.User;
import com.nicolai.todo.exception.UserException;

public interface UserService {

    User registerUser(String username, String password) throws UserException;

    User validateUser(String username, String password) throws UserException;
}
