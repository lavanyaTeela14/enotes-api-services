package com.example.enotes.service.impl;

import com.example.enotes.dto.EmailRequest;
import com.example.enotes.dto.PasswordChangeRequest;
import com.example.enotes.dto.PswdResetRequest;
import com.example.enotes.entity.User;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.repository.UserRepository;
import com.example.enotes.service.UserService;
import com.example.enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        User loggedInUser = CommonUtil.getLoggedInUser();
        if(!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), loggedInUser.getPassword()))
        {
            throw new IllegalArgumentException("Old password entered is incorrect!!");
        }
        String encodedPassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
        loggedInUser.setPassword(encodedPassword);
        userRepository.save(loggedInUser);
    }
    @Override
    public void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception {
        User user = userRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(user))
        {
            throw new ResourceNotFoundException("invalid Email");
        }

        // Generate unique password reset token
        String passwordResetToken = UUID.randomUUID().toString();
        user.getStatus().setPasswordResetToken(passwordResetToken);
        User updateUser = userRepository.save(user);

        String url = CommonUtil.getUrl(request);
        sendEmailRequest(updateUser,url);

    }

    private void sendEmailRequest(User user,String url) throws Exception {

        String message = "Hi <b>[[username]]</b> "
                +"<br><p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=[[url]]>Change my password</a></p>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p><br>"
                + "Thanks,<br>Enotes.com";

        message = message.replace("[[username]]", user.getFirstName());
        message = message.replace("[[url]]", url + "/api/v1/home/verify-pswd-link?uid=" + user.getId() + "&&code="
                + user.getStatus().getPasswordResetToken());

        EmailRequest emailRequest = EmailRequest.builder().to(user.getEmail())
                .title("Password Reset").subject("Password Reset link").text(message).build();

        // send password reset email to user
        emailService.sendEmail(emailRequest);
    }

    @Override
    public void verifyPswdResetLink(Integer uid, String code) throws Exception {
        User user = userRepository.findById(uid).orElseThrow(()->new ResourceNotFoundException("invalid user"));
        verifyPasswordResetToken(user.getStatus().getPasswordResetToken(),code);

    }

    private void verifyPasswordResetToken(String existToken, String reqToken) {

        // request token not null
        if(StringUtils.hasText(reqToken))
        {
            // password already reset
            if(!StringUtils.hasText(existToken))
            {
                throw new IllegalArgumentException("Already Password reset");
            }
            // user req token changes
            if(!existToken.equals(reqToken))
            {
                throw new IllegalArgumentException("invalid url");
            }
        }else {
            throw new IllegalArgumentException("invalid token");
        }
    }

    @Override
    public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception {
        User user = userRepository.findById(pswdResetRequest.getUid()).orElseThrow(()->new ResourceNotFoundException("invalid user"));
        String encodePassword = passwordEncoder.encode(pswdResetRequest.getNewPassword());
        user.setPassword(encodePassword);
        user.getStatus().setPasswordResetToken(null);
        userRepository.save(user);
    }

}
