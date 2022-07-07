package com.revature.flashbash.service;

import com.revature.flashbash.exception.UserNotFoundException;
import com.revature.flashbash.model.User;
import com.revature.flashbash.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class UserService {

    private final UserRepository userRepository;

//    @Autowired   // this is redundant as constructor wiring is implicit
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // Create

    public User createUser(User user){
        // here is where you'd want to make some checks
        return userRepository.save(user);
    }
    // Read

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id){
        //                                if found, unwrap the user from the option and send it back
        //                                          or else -> throw an exception
        return userRepository.findById(id).orElseThrow(() ->  new UserNotFoundException());
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() ->  new UserNotFoundException());
    }

    // Update

    // Delete
}
