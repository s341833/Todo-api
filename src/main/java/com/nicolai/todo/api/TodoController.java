package com.nicolai.todo.api;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.nicolai.todo.domain.Todo;
import com.nicolai.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
        String dateString = (String) todoMap.get("date");
        Date date = new Date(new SimpleDateFormat("yyyy-M-dd").parse(dateString).getTime());
        int ret = todoService.addTodo(username, todo, date, false);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @PostMapping("/todos")
    ResponseEntity<List<Todo>> getTodos(@RequestParam Map<String, Object> userMap) {
        return new ResponseEntity<>(todoService.getTodos((String) userMap.get("username")), HttpStatus.OK);
    }

    @PostMapping("/updateTodo")
    ResponseEntity<String> updateTodo(@RequestParam Map<String, Object> todoMap) {
        int id = Integer.parseInt((String) todoMap.get("id"));
        boolean done = Boolean.parseBoolean((String) todoMap.get("done"));
        String ut = todoService.updateTodo(id, done);

        return new ResponseEntity<>(ut, HttpStatus.OK);
    }

    @DeleteMapping("/deleteTodo")
    ResponseEntity<String> deleteTodo(@RequestParam Map<String, Object> todoMap) {
        int id = Integer.parseInt((String) todoMap.get("id"));

        String ut = todoService.deleteTodo(id);
        return new ResponseEntity<>(ut, HttpStatus.OK);
    }

}
