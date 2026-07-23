package com.aryanbamba.vaultx.service;

import com.aryanbamba.vaultx.dto.LoginRequest;
import com.aryanbamba.vaultx.dto.UserResponse;
import com.aryanbamba.vaultx.entity.User;
import com.aryanbamba.vaultx.exception.InvalidCredentialsException;
import com.aryanbamba.vaultx.repository.UserRepository;
import com.aryanbamba.vaultx.exception.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user){

        logger.info("Creating user with email: {}", user.getEmail());

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            logger.warn("User already exists with email: {}", user.getEmail());

            throw new UserAlreadyExistsException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        logger.info("User created successfully with id: {}", savedUser.getId());

        return savedUser;
    }

    public String login(LoginRequest loginRequest) {

        logger.info("Login attempt for email: {}", loginRequest.getEmail());

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    logger.warn("Login failed. User not found: {}", loginRequest.getEmail());
                    return new InvalidCredentialsException("Invalid email or password");
                });

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

            logger.warn("Login failed. Invalid password for email: {}", loginRequest.getEmail());

            throw new InvalidCredentialsException("Invalid email or password");
        }

        logger.info("Login successful for email: {}", loginRequest.getEmail());

        return "Login Successful";
    }

    public UserResponse getUserByEmail(String email) {

        logger.info("Fetching user with email: {}", email);
        logger.info("Fetching user with email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("User not found with email: {}", email);
                    return new RuntimeException("User not found");
                });

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
