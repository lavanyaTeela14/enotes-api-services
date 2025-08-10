package com.example.enotes.endpoint;

import com.example.enotes.dto.LoginRequest;
import com.example.enotes.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
@Tag(name = "User Authentication", description = "User Authentication APIs")
public interface AuthEndpoint {
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Operation(summary = "User Registration", tags = {"Authentication"})
    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception;

    @Operation(summary = "User Registration", tags = {"Authentication"})
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);

    }
