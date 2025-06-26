package com.example.enotes.util;

import com.example.enotes.handler.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtil {
    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus httpStatus)
    {
        GenericResponse response=GenericResponse.builder()
                .responseStatus(httpStatus)
                .status("success")
                .message("success")
                .data(data)
                .build();
        return response.createResponse();
    }
    public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus httpStatus)
    {
        GenericResponse response=GenericResponse.builder()
                .responseStatus(httpStatus)
                .status("success")
                .message(message)
                .build();
        return response.createResponse();
    }

    public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus httpStatus)
    {
        GenericResponse response=GenericResponse.builder()
                .responseStatus(httpStatus)
                .status("error")
                .message("error")
                .data(data)
                .build();
        return response.createResponse();
    }

    public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus httpStatus)
    {
        GenericResponse response=GenericResponse.builder()
                .responseStatus(httpStatus)
                .status("error")
                .message(message)
                .build();
        return response.createResponse();
    }
}
