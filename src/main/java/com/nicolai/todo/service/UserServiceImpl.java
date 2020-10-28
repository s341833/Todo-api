package com.nicolai.todo.service;

import com.nicolai.todo.domain.User;
import com.nicolai.todo.exception.UserException;
import com.nicolai.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User registerUser(String username, String password) throws UserException {
        return userRepository.create(username, password);
    }

    @Override
    public User validateUser(String username, String password) throws UserException {
        return userRepository.validateUser(username, password);
    }
}
