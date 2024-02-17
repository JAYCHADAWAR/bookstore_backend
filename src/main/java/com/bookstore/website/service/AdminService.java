package com.bookstore.website.service;

import com.bookstore.website.entity.Admin;
import com.bookstore.website.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookstore.website.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin signIn(String email, String password) throws UserNotFoundException{
        Admin admin= adminRepository.findByAdminEmailAndAdminPassword(email, password);
        if(admin==null)
        {
            throw new UserNotFoundException("User not found with the provided email and password");
        }
        return admin;
    }
}
