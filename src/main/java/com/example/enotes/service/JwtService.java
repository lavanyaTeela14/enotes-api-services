package com.example.enotes.service;

import com.example.enotes.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    public String generatetoken(User user);
    public Boolean validateToken(String token, UserDetails userDetails);
    public String extractUserName(String token);
}
