package com.example.enotes.service.impl;

import com.example.enotes.dto.EmailRequest;
import com.example.enotes.dto.UserDto;
import com.example.enotes.entity.AccountStatus;
import com.example.enotes.entity.Roles;
import com.example.enotes.entity.User;
import com.example.enotes.repository.RolesRepository;
import com.example.enotes.repository.UserRepository;
import com.example.enotes.service.UserService;
import com.example.enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Override
    public Boolean register(UserDto userDto, String url) throws Exception {
        validation.userValidation(userDto);
        User user=modelMapper.map(userDto,User.class);
        setRoles(userDto,user);
        AccountStatus accountStatus= AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setStatus(accountStatus);
        User savedUser=userRepository.save(user);
        if(!ObjectUtils.isEmpty(savedUser))
        {
            sendEmail(savedUser,url);
            return true;
        }
        return false;
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

    private void setRoles(UserDto userDto, User user) {
        List<Integer> reqRoleId=userDto.getRoles().stream().map(r->r.getId()).toList();
        List<Roles> roles=rolesRepository.findAllById(reqRoleId);
        user.setRoles(roles);
    }
}
