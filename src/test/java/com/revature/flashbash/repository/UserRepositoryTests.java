package com.revature.flashbash.repository;

import com.revature.flashbash.model.User;
import com.revature.flashbash.model.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserCreate(){
        User user = new User();
        user.setUsername("TestUser");
        user.setPassword("TestPass");
        user.setUserType(UserType.USER);

        // now that we have the object, all we need to do to persist the object is call repo.save(object)
        userRepository.save(user);


        // let's check how to get that user back, findById returns an Optional ? Wrapper for a potentially null value
        Optional<User> result = userRepository.findById(1);
        User userResult;
        userResult = result.orElse(new User());

        Assertions.assertEquals(user.getUsername(), userResult.getUsername());

    }
}
