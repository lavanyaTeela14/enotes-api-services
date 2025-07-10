package com.example.enotes.dto;

import com.example.enotes.entity.Roles;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
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
