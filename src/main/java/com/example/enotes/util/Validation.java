package com.example.enotes.util;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.ToDoDto;
import com.example.enotes.enums.ToDoStatus;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Validation {
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
}
