package com.revature.flashbash.controller;

import com.revature.flashbash.model.User;
import com.revature.flashbash.security.RegistrationRequest;
import com.revature.flashbash.service.UserService;
import org.springframework.http.HttpStatus;
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


    @PostMapping() //                            -> POST /users
    public User createUser(@RequestBody RegistrationRequest registrationRequest){
        return userService.createUser(registrationRequest);
    }

    @GetMapping // this already inherits /users -> GET /users
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("{userId}")
    public User getUserById(@PathVariable Integer userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @PatchMapping
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Integer userId){
        userService.deleteUserById(userId);
    }


}

