package com.revature.flashbash.controller;

import com.revature.flashbash.model.User;
import com.revature.flashbash.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// localhost:8080/users

@RestController
@RequestMapping("/users") // this gets applied to every method
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping // this already inherits /users -> GET /users
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    // TODO: Implement a Get User By Id


    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @PostMapping //                            -> POST /users
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }



}
