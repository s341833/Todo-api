package com.nicolai.todo.api;

import com.nicolai.todo.domain.User;
import com.nicolai.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/registrer")
    ResponseEntity<String> registerUser(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");
        
        User user = userService.registerUser(username, password);
        return new ResponseEntity<>(user.getUsername(), HttpStatus.OK);
    }

    @PostMapping("/login")
    ResponseEntity<String> loginUser(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");

        User user = userService.validateUser(username, password);
        return new ResponseEntity<>(user.getUsername(), HttpStatus.OK);
    }
}