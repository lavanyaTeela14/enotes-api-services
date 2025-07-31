package com.example.enotes.endpoint;

import com.example.enotes.dto.PswdResetRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/home")
public interface HomeEndpoint {
    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer userId, @RequestParam String verificationCode) throws Exception;

    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request)
            throws Exception;

    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code)
            throws Exception;

    @GetMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception;
}
