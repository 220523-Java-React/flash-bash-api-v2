package com.revature.flashbash.service;

import com.revature.flashbash.exception.InvalidCredentialsException;
import com.revature.flashbash.exception.ResourceNotFoundException;
import com.revature.flashbash.exception.ResourceAlreadyExistsException;
import com.revature.flashbash.model.User;
import com.revature.flashbash.repository.UserRepository;
import com.revature.flashbash.security.AuthenticationRequest;
import com.revature.flashbash.security.AuthenticationResponse;
import com.revature.flashbash.security.JwtUtil;
import com.revature.flashbash.security.RegistrationRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

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

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Integer userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "userId", userId));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "username", username));
    }

    public User replaceUser(User user, Integer userId) {
        if(!userRepository.existsById(userId))
            throw new ResourceNotFoundException(User.class, "userId", userId);

        user.setUserId(userId);
        user.setLastUpdated(new Timestamp(System.currentTimeMillis()));

        return userRepository.save(user);
    }

    private void updateUser(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "username", username));

        user.setLastUpdated(new Timestamp(System.currentTimeMillis()));

        userRepository.save(user);
    }

    public User updateUser(User user, Integer userId){
        User dbUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "userId", userId));

        if(user.getUsername() != null){
            dbUser.setUsername(user.getUsername());
        }
        if(user.getPassword() != null){
            dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if(user.getFirstName() != null){
            dbUser.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null){
            dbUser.setLastName(user.getLastName());
        }
        if(user.getAuthority() != null){
            dbUser.setAuthority(user.getAuthority());
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());
        dbUser.setLastUpdated(now);

        if(user.getLastLoggedIn() != null) dbUser.setLastLoggedIn(now);

        return userRepository.save(dbUser);
    }

    public void deleteUserById(Integer userId){
        if(userRepository.deleteByUserId(userId) == 0) throw new ResourceNotFoundException(User.class, "userId", userId);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch(BadCredentialsException e){
            throw new InvalidCredentialsException();
        }

        updateUser(request.getUsername());
        User user = (User) loadUserByUsername(request.getUsername());

        return new AuthenticationResponse(jwtUtil.generateToken(user), request.getUsername(), user.getUserId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }
}
