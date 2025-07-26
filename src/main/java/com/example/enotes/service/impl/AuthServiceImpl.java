package com.example.enotes.service.impl;

import com.example.enotes.config.security.CustomUserDetails;
import com.example.enotes.dto.*;
import com.example.enotes.entity.AccountStatus;
import com.example.enotes.entity.Roles;
import com.example.enotes.entity.User;
import com.example.enotes.repository.RolesRepository;
import com.example.enotes.repository.UserRepository;
import com.example.enotes.service.JwtService;
import com.example.enotes.service.AuthService;
import com.example.enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Boolean register(UserRequest userRequest, String url) throws Exception {
        validation.userValidation(userRequest);
        User user=modelMapper.map(userRequest,User.class);
        setRoles(userRequest,user);
        AccountStatus accountStatus= AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setStatus(accountStatus);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser=userRepository.save(user);
        if(!ObjectUtils.isEmpty(savedUser))
        {
            sendEmail(savedUser,url);
            return true;
        }
        return false;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        if (authentication.isAuthenticated())
        {
            CustomUserDetails customUserDetails=(CustomUserDetails)authentication.getPrincipal();
            String token= jwtService.generatetoken(customUserDetails.getUser());
            LoginResponse loginResponse=LoginResponse.builder()
                    .user(modelMapper.map(customUserDetails.getUser(), UserResponse.class))
                    .token(token)
                    .build();
            return loginResponse;
        }
        return null;
    }

    private void sendEmail(User savedUser,String url) throws Exception {
        String message="Hi,<b>[[username]]</b> "
                + "<br> Your account register sucessfully.<br>"
                +"<br> Click the below link verify & Active your account <br>"
                +"<a href='[[url]]'>Click Here</a> <br><br>"
                +"Thanks,<br>Enotes.com"
                ;

        message=message.replace("[[username]]", savedUser.getFirstName());
        message=message.replace("[[url]]",url+"/api/v1/home/verify?userId="+savedUser.getId()+"&&verificationCode="+savedUser.getStatus().getVerificationCode());

        EmailRequest emailRequest=EmailRequest.builder()
                .to(savedUser.getEmail())
                .title("Account Creation Message!")
                .subject("Account successfull creation message")
                .text(message)
                .build();
        emailService.sendEmail(emailRequest);
    }

    private void setRoles(UserRequest userRequest, User user) {
        List<Integer> reqRoleId= userRequest.getRoles().stream().map(r->r.getId()).toList();
        List<Roles> roles=rolesRepository.findAllById(reqRoleId);
        user.setRoles(roles);
    }
}
