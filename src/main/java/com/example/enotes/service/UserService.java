package com.example.enotes.service;

import com.example.enotes.dto.PasswordChangeRequest;
import com.example.enotes.dto.PswdResetRequest;
import com.example.enotes.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    public void changePassword(PasswordChangeRequest passwordChangeRequest);
    public void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception;

    public void verifyPswdResetLink(Integer uid, String code) throws Exception;

    public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception;

}
