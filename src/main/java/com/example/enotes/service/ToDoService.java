package com.example.enotes.service;

import com.example.enotes.dto.ToDoDto;

import java.util.List;

public interface ToDoService {
    public Boolean saveToDo(ToDoDto toDoDto) throws Exception;
    public ToDoDto getToDoById(Integer id) throws Exception;
    public List<ToDoDto> getAllToDoByUser();
}
