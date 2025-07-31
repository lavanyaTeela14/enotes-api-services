package com.example.enotes.controller;

import com.example.enotes.dto.LoginRequest;
import com.example.enotes.dto.LoginResponse;
import com.example.enotes.dto.UserRequest;
import com.example.enotes.endpoint.AuthEndpoint;
import com.example.enotes.service.AuthService;
import com.example.enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController implements AuthEndpoint {
    @Autowired
    private AuthService userService;

    @Override
    public ResponseEntity<?> saveUser(UserRequest userRequest, HttpServletRequest request) throws Exception {

        String url = request.getRequestURL().toString();
        url=url.replace(request.getServletPath(),"");
        Boolean register= userService.register(userRequest,url);
        log.info("AuthController : saveUser() : register successfully");
       if(register)
       {
           log.info("AuthController : saveUser() : register successfully");
           return CommonUtil.createBuildResponseMessage("User Registered successfully", HttpStatus.CREATED);
       }
       log.info("AuthController : saveUser() : register not successfully");
       return CommonUtil.createErrorResponseMessage("User not registered", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest)
    {
        log.info("AuthController : login() : login successfully");
       LoginResponse response= userService.login(loginRequest);
       if(ObjectUtils.isEmpty(response))
       {
           log.info("AuthController : login() : Invalid credentials");
           return CommonUtil.createErrorResponseMessage("Invalid credentials",HttpStatus.BAD_REQUEST);
       }
       log.info("AuthController : login() : login successfully");
        return CommonUtil.createBuildResponse(response,HttpStatus.OK);
    }

}
