package com.bookstore.website.repository;

import com.bookstore.website.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByAdminEmailAndAdminPassword(String email, String password);
}
