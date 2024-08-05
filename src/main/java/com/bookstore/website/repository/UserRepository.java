package com.bookstore.website.repository;
import com.bookstore.website.entity.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmailAndPassword(String email,String password);

    User findById(UUID userId);

    @Modifying
    @Transactional
    @Query(value="UPDATE users  SET email = :email, name = :name WHERE email = :oldEmail",nativeQuery = true)
    Integer updateUser(String email, String name, String oldEmail);
}
