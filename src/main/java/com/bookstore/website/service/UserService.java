package com.bookstore.website.service;
import com.bookstore.website.controller.UserController;
import com.bookstore.website.exception.UserAlreadyExistsException;
import com.bookstore.website.exception.UserNotFoundException;
import com.bookstore.website.model.SignInRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookstore.website.repository.UserRepository;
import com.bookstore.website.model.SignUpRequest;
import com.bookstore.website.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private SignUpRequest signUpRequest;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    public void signUp(SignUpRequest signUpRequest) throws UserAlreadyExistsException {
        // Validate user input
        // Check if user with the same email already exists
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + signUpRequest.getEmail() + " already exists");
        }

        // Create a new user entity and save it to the database
        User user = new User(signUpRequest.getId(),signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword());
        userRepository.save(user);
    }

    public User signIn(SignInRequest signInRequest)  throws UserNotFoundException{
        log.info("Sign In Request: " + signInRequest.getEmailAndPasswordString());
        User user=userRepository.findByEmailAndPassword(signInRequest.getEmail(),signInRequest.getPassword());
        if(user==null)
        {
            log.info("user from db  " + user.getUser());
            throw new UserNotFoundException("User not found with the provided email and password");
        }
        log.info("user from db  " + user.getUser());
        return user;
    }

    public User findById(UUID userId)
    {
        try {
            // Retrieve user profile from the database
            User user = userRepository.findById(userId);
            if (user == null) {
                throw new UserNotFoundException("User profile not found");
            }

            return user;
        } catch (Exception e) {

            throw new UserNotFoundException("Error retrieving user profile");
        }
    }

    public Boolean updateProfile(String email, String name) {

            String oldEmail=email;
            int i= userRepository.updateUser(email,name,oldEmail);
            log.info(String.valueOf(i));
            if(i>0)
                return true;
            return false;

    }
}
