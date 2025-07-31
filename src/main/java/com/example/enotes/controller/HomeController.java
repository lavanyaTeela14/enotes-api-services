package com.example.enotes.controller;

import com.example.enotes.dto.PswdResetRequest;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.service.HomeService;
import com.example.enotes.service.UserService;
import com.example.enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    Logger log= LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer userId, @RequestParam String verificationCode) throws Exception {
        log.info("HomeController : verifyUserAccount() : execution Start");
        Boolean verifyAccount=homeService.verifyStatus(userId,verificationCode);
        if(!verifyAccount)
        {
            log.info("HomeController : verifyUserAccount() : Invalid link");
            return CommonUtil.createErrorResponseMessage("Invalid verification link",HttpStatus.BAD_REQUEST);
        }
        log.info("HomeController : verifyUserAccount() : Account verification success");
        log.info("HomeController : verifyUserAccount() : execution End");
        return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.CREATED);
    }

    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request)
            throws Exception {
        log.info("HomeController : sendEmailForPasswordReset() : execution Start");
        userService.sendEmailPasswordReset(email, request);
        log.info("HomeController : sendEmailForPasswordReset() : execution End");
        return CommonUtil.createBuildResponseMessage("Email Send Success !! Check Email Reset Password", HttpStatus.OK);
    }

    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code)
            throws Exception {
        log.info("HomeController : verifyPasswordResetLink() : execution Start");
        userService.verifyPswdResetLink(uid, code);
        log.info("HomeController : verifyPasswordResetLink() : execution End");
        return CommonUtil.createBuildResponseMessage("verification success", HttpStatus.OK);
    }

    @PostMapping("/reset-pswd")
    public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception {
        log.info("HomeController : resetPassword() : execution Start");
        userService.resetPassword(pswdResetRequest);
        log.info("HomeController : resetPassword() : execution End");
        return CommonUtil.createBuildResponseMessage("Password reset succes", HttpStatus.OK);
    }

}
