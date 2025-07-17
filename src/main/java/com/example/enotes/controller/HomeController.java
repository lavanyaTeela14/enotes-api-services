package com.example.enotes.controller;

import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.service.HomeService;
import com.example.enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer userId, @RequestParam String verificationCode) throws Exception {
        Boolean verifyAccount=homeService.verifyStatus(userId,verificationCode);
        if(verifyAccount)
        {
            return CommonUtil.createBuildResponseMessage("Account verificaion success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Invalid verification link",HttpStatus.BAD_REQUEST);
    }
}
