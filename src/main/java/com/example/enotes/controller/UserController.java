package com.example.enotes.controller;

import com.example.enotes.dto.UserResponse;
import com.example.enotes.entity.User;
import com.example.enotes.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private ModelMapper mapper;

    @RequestMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        User user = CommonUtil.getLoggedInUser();
        UserResponse userResponse = mapper.map(user, UserResponse.class);
        return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
    }
}
