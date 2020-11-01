package com.nicolai.todo.service;


import com.nicolai.todo.domain.Todo;
import com.nicolai.todo.exceptions.TodoException;

import java.sql.Date;
import java.util.List;

public interface TodoService {

    List<Todo> getTodos(String username);

    int addTodo(String username, String todo, Date date, boolean done) throws TodoException;

    String updateTodo(int id, boolean done) throws TodoException;

    String deleteTodo(int id) throws TodoException;

}
