package com.revature.flashbash.service;

import com.revature.flashbash.exception.ResourceNotFoundException;
import com.revature.flashbash.exception.ResourceAlreadyExistsException;
import com.revature.flashbash.model.User;
import com.revature.flashbash.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

//    @Autowired   // this is redundant as constructor wiring is implicit
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // Create

    public User createUser(User user){
        // here is where you'd want to make some checks if the username exists
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new ResourceAlreadyExistsException(User.class, "username", user.getUsername());
        }

        return userRepository.save(user);
    }
    // Read

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Integer userId){
        //                                if found, unwrap the user from the option and send it back
        //                                          or else -> throw an exception
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(User.class, "userId", userId));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(User.class, "username", username));
    }

    // Update
    public User updateUser(User user){
        // check if the user exists
        if(!userRepository.existsById(user.getUserId())){
            throw new ResourceNotFoundException(User.class, "userId", user.getUserId());
        }

        return userRepository.save(user);
    }

    // Delete
    public void deleteUserById(Integer userId){
        if(userRepository.deleteByUserId(userId) == 0) throw new ResourceNotFoundException(User.class, "userId", userId);
    }
}
