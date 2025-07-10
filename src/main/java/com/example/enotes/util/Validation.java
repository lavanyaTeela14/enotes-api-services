package com.example.enotes.util;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.ToDoDto;
import com.example.enotes.dto.UserDto;
import com.example.enotes.enums.ToDoStatus;
import com.example.enotes.exception.ExistingDataException;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.exception.ValidationException;
import com.example.enotes.repository.RolesRepository;
import com.example.enotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.enotes.util.Constants.EMAIL_REGEX;
import static com.example.enotes.util.Constants.MOBNO_REGEX;

@Component
public class Validation {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UserRepository userRepository;

    public void categoryValidation(CategoryDto categoryDto){
        Map<String,Object> errors=new LinkedHashMap<>();
        if (ObjectUtils.isEmpty(categoryDto)) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        else {
            if(ObjectUtils.isEmpty(categoryDto.getName())) {
                errors.put("name","Name cannot be null");
            }
            else {
                if(ObjectUtils.isEmpty(categoryDto.getName().length()<3))
                {
                    errors.put("name","Name cannot be less than 3 characters");
                }
                if(ObjectUtils.isEmpty(categoryDto.getName().length()>50))
                {
                    errors.put("name","Name cannot be greater than 50 characters");
                }
            }
            if(ObjectUtils.isEmpty(categoryDto.getDescription())) {
                errors.put("description","Description cannot be null");
            }
            if(ObjectUtils.isEmpty(categoryDto.getIsActive()))
            {
                errors.put("isActive","isActive cannot be null");
            }
            else {
                if(categoryDto.getIsActive()!=Boolean.TRUE && categoryDto.getIsActive()!=Boolean.FALSE)
                {
                    errors.put("isActive","isActive should be true or false");
                }
            }
        }
        if(!errors.isEmpty())
        {
            throw new ValidationException(errors);
        }
    }

    public void toDoValidation(ToDoDto toDoDto) throws Exception {
        ToDoDto.StatusDto reqStatus=toDoDto.getStatus();
        Boolean statusFound=false;
        for (ToDoStatus st : ToDoStatus.values())
        {
            if(st.getId().equals(reqStatus.getId()))
            {
                statusFound=true;
            }
        }
        if(!statusFound)
        {
            throw new ResourceNotFoundException("Invalid status");
        }
    }

    public void userValidation(UserDto userDto)
    {
        if(!StringUtils.hasText(userDto.getFirstName()))
        {
            throw new IllegalArgumentException("Invalid First Name");
        }

        if(!StringUtils.hasText(userDto.getLastName()))
        {
            throw new IllegalArgumentException("Invalid Last Name");
        }

        if(!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(EMAIL_REGEX))
        {
            throw new IllegalArgumentException("Invalid email");
        }
        else{
            Boolean isExists=userRepository.existsByEmail(userDto.getEmail());
            if(isExists)
            {
                throw new ExistingDataException("Email already exists");
            }
        }

        if(!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(MOBNO_REGEX))
        {
            throw new IllegalArgumentException("Invalid Mobile number");
        }

        if(CollectionUtils.isEmpty(userDto.getRoles()))
        {
            throw new IllegalArgumentException("Invalid roles");
        }
        else{
            List<Integer> roleIds=rolesRepository.findAll().stream().map(r->r.getId()).toList();
            List<Integer> invalidRoleIds=userDto.getRoles().stream()
                    .map(r->r.getId())
                    .filter(roleId->!roleIds.contains(roleId)).toList();

            if(!CollectionUtils.isEmpty(invalidRoleIds))
            {
                throw new IllegalArgumentException("Invalid roles is "+invalidRoleIds);
            }
        }
    }
}
