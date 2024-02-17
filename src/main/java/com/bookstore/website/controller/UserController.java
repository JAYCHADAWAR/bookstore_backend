package com.bookstore.website.controller;
import com.bookstore.website.Interceptor.AuthInterceptor;
import com.bookstore.website.entity.User;
import com.bookstore.website.exception.UserAlreadyExistsException;
import com.bookstore.website.exception.UserNotFoundException;
import com.bookstore.website.model.SignInRequest;
import com.bookstore.website.model.SignUpRequest;
import com.bookstore.website.model.UpdateProfileRequest;
import com.bookstore.website.model.UpdateUserBookLikesRequest;
import com.bookstore.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bookstore.website.util.JwtUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    private JwtUtil jwtUtil;
    private User user;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            userService.signUp(signUpRequest);
            String token = jwtUtil.generateToken(signUpRequest.getId().toString());

            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("token", token);
            responseMap.put("message","User signed up successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or email already exists");
        }
    }

    @PostMapping("/sigin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest)
    {
        log.info("in sigin");
        log.info("siginrequest in signin: " + signInRequest.getEmailAndPasswordString());
        try{
            User user=userService.signIn(signInRequest);
            log.info("User:in sigin  " + user.getUser());
            String token = jwtUtil.generateToken(user.getId().toString());
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("token", token);
            responseMap.put("message","User signed In successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or email does not match");
        }
    }

    @PostMapping("/updateuserbklikes")
    @Transactional
    public ResponseEntity<?> updateUserBookLikes(@RequestBody UpdateUserBookLikesRequest request) {
        try {
            System.out.println("in like bookid");
            UUID bookId = request.getBookId();
            boolean liked = request.isLiked();
            UUID userId = request.getUserId();

            if (liked) {
                entityManager.createNativeQuery(
                                "DELETE FROM user_likes WHERE bookid = ? AND userid = ?")
                        .setParameter(1, bookId)
                        .setParameter(2, userId)
                        .executeUpdate();
                return ResponseEntity.ok().body("Book with ID " + bookId + " unliked successfully");
            } else {
                entityManager.createNativeQuery(
                                "INSERT INTO user_likes (bookid, userid) VALUES (?, ?)")
                        .setParameter(1, bookId)
                        .setParameter(2, userId)
                        .executeUpdate();
                return ResponseEntity.ok().body("Book with ID " + bookId + " liked successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfile(@RequestParam("userId") UUID userId) {
        try {

            User user = userService.findById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found");
            }

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user profile");
        }
    }

    @PostMapping("/updateprofile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request) {
        try {
            String email = request.getEmail();
            String name = request.getName();
            Boolean isupdated=userService.updateProfile(email, name);
            log.info(String.valueOf(isupdated));
            if(isupdated)
            {
                return ResponseEntity.status(HttpStatus.OK).body("Profile updated successfully");
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating profile");
        }
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Profile not updated successfully");
    }


}
