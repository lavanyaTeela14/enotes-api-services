package com.example.enotes.endpoint;

import com.example.enotes.dto.PswdResetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/home")
@Tag(name = "Home",description = "All Home API's")
public interface HomeEndpoint {
    @Operation(summary = "Verify user account",tags = "Home",description = "User account verification after registration")
    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer userId, @RequestParam String verificationCode) throws Exception;

    @Operation(summary = "send email for password reset",tags = "Home",description = "User can send email for password reset")
    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request)
            throws Exception;

    @Operation(summary = "verify password reset link",tags = "Home",description = "verify password reset link")
    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code)
            throws Exception;
    @Operation(summary = "reset password",tags = "Home",description = "password reset")
    @GetMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception;
}
