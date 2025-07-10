package com.example.enotes.service.impl;

import com.example.enotes.dto.UserDto;
import com.example.enotes.entity.Roles;
import com.example.enotes.entity.User;
import com.example.enotes.repository.RolesRepository;
import com.example.enotes.repository.UserRepository;
import com.example.enotes.service.UserService;
import com.example.enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Boolean register(UserDto userDto) {
        validation.userValidation(userDto);
        User user=modelMapper.map(userDto,User.class);
        setRoles(userDto,user);
        User savedUser=userRepository.save(user);
        if(!ObjectUtils.isEmpty(savedUser))
        {
            return true;
        }
        return false;
    }

    private void setRoles(UserDto userDto, User user) {
        List<Integer> reqRoleId=userDto.getRoles().stream().map(r->r.getId()).toList();
        List<Roles> roles=rolesRepository.findAllById(reqRoleId);
        user.setRoles(roles);
    }
}
