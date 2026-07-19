package com.aryanbamba.vaultx.service;

import com.aryanbamba.vaultx.entity.User;
import com.aryanbamba.vaultx.repository.UserRepository;
import com.aryanbamba.vaultx.exception.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        return userRepository.save(user);
    }
}
