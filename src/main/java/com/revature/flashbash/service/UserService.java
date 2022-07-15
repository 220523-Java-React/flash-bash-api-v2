package com.revature.flashbash.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.flashbash.exception.InvalidCredentialsException;
import com.revature.flashbash.exception.ResourceNotFoundException;
import com.revature.flashbash.exception.ResourceAlreadyExistsException;
import com.revature.flashbash.model.User;
import com.revature.flashbash.repository.UserRepository;
import com.revature.flashbash.security.AuthenticationRequest;
import com.revature.flashbash.security.AuthenticationResponse;
import com.revature.flashbash.security.JwtUtil;
import com.revature.flashbash.security.RegistrationRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

//    @Autowired   // this is redundant as constructor wiring is implicit

    public UserService(UserRepository userRepository, JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                       ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }


    // Create

    public User createUser(RegistrationRequest registrationRequest){
        if(userRepository.existsByUsername(registrationRequest.getUsername()))
            throw new ResourceAlreadyExistsException(User.class, "username", registrationRequest.getUsername());

        User newUser = User.builder()
                .username(registrationRequest.getUsername())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .authority(User.Authority.USER)
                .isActive(true)
                .build();

        return userRepository.save(newUser);
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

    private User findUser(User user){
        Integer userId = user.getUserId();
        String username = user.getUsername();

        User foundUser = null;

        if(userId != null)
            foundUser = userRepository.findById(userId).orElse(null);

        if(foundUser == null && username != null)
            foundUser = userRepository.findByUsername(username).orElse(null);

        if(foundUser == null){
            if(user.getUserId() != null)
                throw new ResourceNotFoundException(User.class, "userId", user.getUserId());
            else
                throw new ResourceNotFoundException(User.class, "username", user.getUsername());
        }

        return foundUser;

    }

    // Update
    public User updateUser(User user){
        // check if the user exists
        User dbUser = findUser(user);
        Boolean fieldUpdated = false;
        if(user.getUsername() != null){
            dbUser.setUsername(user.getUsername());
            fieldUpdated = true;
        }
        if(user.getPassword() != null){
            dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
            fieldUpdated = true;
        }
        if(user.getFirstName() != null){
            dbUser.setFirstName(user.getFirstName());
            fieldUpdated = true;
        }
        if(user.getLastName() != null){
            dbUser.setLastName(user.getLastName());
            fieldUpdated = true;
        }
        if(user.getAuthority() != null){
            dbUser.setAuthority(user.getAuthority());
            fieldUpdated = true;
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(fieldUpdated) dbUser.setLastUpdated(now);

        if(user.getLastLoggedIn() != null) dbUser.setLastLoggedIn(now);

        return userRepository.save(dbUser);
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

        // update the lastLoggedInValue on the user
        updateUser(User.builder().username(request.getUsername()).build());

        return new AuthenticationResponse(jwtUtil.generateToken((User) loadUserByUsername(request.getUsername())));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }
}
