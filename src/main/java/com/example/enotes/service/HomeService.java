package com.example.enotes.service;

import com.example.enotes.exception.ResourceNotFoundException;

public interface HomeService {
    Boolean verifyStatus(Integer userId,String verificationCode) throws Exception;
}
