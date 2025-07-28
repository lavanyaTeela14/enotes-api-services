package com.example.enotes.controller;

import com.example.enotes.dto.PswdResetRequest;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.service.HomeService;
import com.example.enotes.service.UserService;
import com.example.enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer userId, @RequestParam String verificationCode) throws Exception {
        Boolean verifyAccount=homeService.verifyStatus(userId,verificationCode);
        if(verifyAccount)
        {
            return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Invalid verification link",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request)
            throws Exception {
        userService.sendEmailPasswordReset(email, request);
        return CommonUtil.createBuildResponseMessage("Email Send Success !! Check Email Reset Password", HttpStatus.OK);
    }

    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code)
            throws Exception {
        userService.verifyPswdResetLink(uid, code);
        return CommonUtil.createBuildResponseMessage("verification success", HttpStatus.OK);
    }

    @PostMapping("/reset-pswd")
    public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception {
        userService.resetPassword(pswdResetRequest);
        return CommonUtil.createBuildResponseMessage("Password reset succes", HttpStatus.OK);
    }

}
