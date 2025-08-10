package com.example.enotes.endpoint;

import com.example.enotes.dto.PasswordChangeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "USER",description = "All User API's")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {

    @Operation(summary = "Get User profile",tags = {"USER"},description = "Get user profile notes")
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile();

    @Operation(summary = "change password",tags = {"USER"},description = "change password")
    @PostMapping("/change-pwd")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);
}
