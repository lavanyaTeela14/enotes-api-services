package com.example.enotes.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private Integer id;
    private String firstName;
    private String LastName;
    private String email;
    private String password;
    private String mobNo;
    private List<RolesDto> roles;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RolesDto {
        private Integer id;
        private String name;
    }
}
