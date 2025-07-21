package com.example.enotes.service;

import com.example.enotes.entity.User;

public interface JwtService {
    public String generatetoken(User user);
}
