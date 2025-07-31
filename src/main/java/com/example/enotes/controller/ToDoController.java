package com.example.enotes.controller;

import com.example.enotes.dto.ToDoDto;
import com.example.enotes.endpoint.ToDoEndpoint;
import com.example.enotes.entity.ToDo;
import com.example.enotes.service.ToDoService;
import com.example.enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToDoController implements ToDoEndpoint {
    @Autowired
    ToDoService toDoService;

    @Override
    public ResponseEntity<?> saveToDo(ToDoDto toDoDto) throws Exception {
        Boolean save = toDoService.saveToDo(toDoDto);
        if (save) {
            return CommonUtil.createBuildResponseMessage("ToDo saved successfully", HttpStatus.CREATED);
        }else{
            return CommonUtil.createErrorResponseMessage("ToDo not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getToDoById(Integer id) throws Exception {
        ToDoDto toDoDto = toDoService.getToDoById(id);
        return CommonUtil.createBuildResponse(toDoDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllToDoByUser() throws Exception {
        List<ToDoDto> list=toDoService.getAllToDoByUser();
        if(CollectionUtils.isEmpty(list)) {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(list, HttpStatus.OK);
    }
}
