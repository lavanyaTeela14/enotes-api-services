package com.example.enotes.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    private String firstName;
    private String LastName;
    private String email;
    private ToDoDto.StatusDto status;
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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StatusDto {
        private Integer id;
        private Boolean isActive;
    }
}
