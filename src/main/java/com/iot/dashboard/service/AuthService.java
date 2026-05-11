package com.iot.dashboard.service;

import com.iot.dashboard.dto.AuthRequest;
import com.iot.dashboard.model.User;
import com.iot.dashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(AuthRequest authRequest) {
        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setEmail(authRequest.getEmail());
        return userRepository.save(user);
    }

    public User loginUser(AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElse(userRepository.findByEmail(authRequest.getEmail())
                        .orElseThrow(() -> new RuntimeException("User not found")));

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

}
