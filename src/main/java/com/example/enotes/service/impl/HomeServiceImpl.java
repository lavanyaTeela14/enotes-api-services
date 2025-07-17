package com.example.enotes.service.impl;

import com.example.enotes.entity.AccountStatus;
import com.example.enotes.entity.User;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.exception.SuccessException;
import com.example.enotes.repository.UserRepository;
import com.example.enotes.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public Boolean verifyStatus(Integer userId, String verificationCode) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Invalid user"));

        if(user.getStatus().getVerificationCode()==null)
        {
            throw new SuccessException("Account already verified");
        }

        if(user.getStatus().getVerificationCode().equals(verificationCode))
        {
            AccountStatus status=user.getStatus();
            status.setIsActive(true);
            status.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
