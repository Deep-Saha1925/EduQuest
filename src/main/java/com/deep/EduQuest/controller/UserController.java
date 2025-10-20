package com.deep.EduQuest.controller;

import com.deep.EduQuest.model.User;
import com.deep.EduQuest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        System.out.println(user.toString());
        return ResponseEntity.ok(userService.createUser(user));
    }

    //get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //get User By id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findByUserId(id));
    }
}
