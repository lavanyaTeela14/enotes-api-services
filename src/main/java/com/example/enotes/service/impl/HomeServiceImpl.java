package com.example.enotes.service.impl;

import com.example.enotes.entity.AccountStatus;
import com.example.enotes.entity.User;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.exception.SuccessException;
import com.example.enotes.repository.UserRepository;
import com.example.enotes.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public Boolean verifyStatus(Integer userId, String verificationCode) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Invalid user"));

        if(user.getStatus().getVerificationCode()==null)
        {
            log.info("HomeServiceImpl : verifyStatus() :Account already verified");
            throw new SuccessException("Account already verified");
        }

        if(user.getStatus().getVerificationCode().equals(verificationCode))
        {
            AccountStatus status=user.getStatus();
            status.setIsActive(true);
            status.setVerificationCode(null);
            userRepository.save(user);
            log.info("HomeServiceImpl : verifyStatus() :Account verified successfully");
            return true;
        }
        log.info("HomeServiceImpl : verifyStatus() :Invalid verification link");
        return false;
    }
}
