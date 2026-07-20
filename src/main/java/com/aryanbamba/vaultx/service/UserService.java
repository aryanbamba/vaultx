package com.aryanbamba.vaultx.service;

import com.aryanbamba.vaultx.entity.User;
import com.aryanbamba.vaultx.repository.UserRepository;
import com.aryanbamba.vaultx.exception.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){

        logger.info("Creating user with email: {}", user.getEmail());

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            logger.warn("User already exists with email: {}", user.getEmail());

            throw new UserAlreadyExistsException("Email already exists");
        }

        User savedUser = userRepository.save(user);

        logger.info("User created successfully with id: {}", savedUser.getId());

        return savedUser;
    }


}
