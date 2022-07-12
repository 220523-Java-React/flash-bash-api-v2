package com.revature.flashbash.service;

import com.revature.flashbash.exception.InvalidCredentialsException;
import com.revature.flashbash.exception.ResourceNotFoundException;
import com.revature.flashbash.exception.ResourceAlreadyExistsException;
import com.revature.flashbash.model.User;
import com.revature.flashbash.repository.UserRepository;
import com.revature.flashbash.security.AuthenticationRequest;
import com.revature.flashbash.security.AuthenticationResponse;
import com.revature.flashbash.security.JwtUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

//    @Autowired   // this is redundant as constructor wiring is implicit

    public UserService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    // Create

    public User createUser(User user){
        // here is where you'd want to make some checks if the username exists
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new ResourceAlreadyExistsException(User.class, "username", user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch(BadCredentialsException e){
            throw new InvalidCredentialsException();
        }

        return new AuthenticationResponse(jwtUtil.generateToken((User) loadUserByUsername(request.getUsername())));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }
}
