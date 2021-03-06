package com.nicolai.todo.service;

import com.nicolai.todo.domain.Todo;
import com.nicolai.todo.exceptions.TodoException;
import com.nicolai.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class TodoServiceImpl implements TodoService{

    @Autowired
    TodoRepository todoRepository;

    @Override
    public List<Todo> getTodos(String username) {
        return todoRepository.getTodos(username);
    }

    @Override
    public int addTodo(String username, String todo, Date date, boolean done) throws TodoException {
        return todoRepository.addTodo(username, todo, date, done);
    }

    @Override
    public String updateTodoDone(int id, boolean done) throws TodoException {
        return todoRepository.updateTodoDone(id, done);
    }

    @Override
    public String updateTodo(int id, String todo, Date done) throws TodoException {
        return todoRepository.updateTodo(id, todo, done);
    }

    @Override
    public String deleteTodo(int id) throws TodoException {
        return todoRepository.deleteTodo(id);
    }
}
