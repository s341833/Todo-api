package com.nicolai.todo.api;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.nicolai.todo.domain.Todo;
import com.nicolai.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/postTodo")
    ResponseEntity<Integer> postTodo(@RequestParam Map<String, Object> todoMap) throws ParseException {
        String username = (String) todoMap.get("username");
        String todo = (String) todoMap.get("todo");
        Date date = new Date(System.currentTimeMillis());
        boolean done = Boolean.parseBoolean((String) todoMap.get("done"));

        int ret = todoService.addTodo(username, todo, date, done);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @PostMapping("/todos")
    ResponseEntity<List<Todo>> getTodos(@RequestParam String username) {
        return new ResponseEntity<>(todoService.getTodos(username), HttpStatus.OK);
    }

}
