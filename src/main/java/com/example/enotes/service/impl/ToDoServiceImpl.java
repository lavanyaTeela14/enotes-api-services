package com.example.enotes.service.impl;

import com.example.enotes.dto.ToDoDto;
import com.example.enotes.entity.ToDo;
import com.example.enotes.enums.ToDoStatus;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.repository.ToDoRepository;
import com.example.enotes.service.ToDoService;
import com.example.enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {
    
    @Autowired
    private ToDoRepository toDoRepository;
    
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Validation validation;

    @Override
    public Boolean saveToDo(ToDoDto toDoDto) throws Exception {

        validation.toDoValidation(toDoDto);

        ToDo toDo = mapper.map(toDoDto, ToDo.class);
        toDo.setStatusId(toDoDto.getStatus().getId());
        ToDo save = toDoRepository.save(toDo);
        if(!ObjectUtils.isEmpty(save))
        {
            return true;
        }
        return false;
    }

    @Override
    public ToDoDto getToDoById(Integer id) throws Exception {
        ToDo toDo = toDoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("To-Do Id is not valid!"));
        ToDoDto toDoDto=mapper.map(toDo, ToDoDto.class);
        setStatus(toDoDto,toDo);
        return toDoDto;
    }

    public void setStatus(ToDoDto toDoDto,ToDo toDo)
    {
        for(ToDoStatus st : ToDoStatus.values())
        {
            if(st.getId().equals(toDo.getStatusId()))
            {
                ToDoDto.StatusDto statusDto= ToDoDto.StatusDto.builder()
                        .id(st.getId())
                        .name(st.getName())
                        .build();
                toDoDto.setStatus(statusDto);
            }
        }
    }

    @Override
    public List<ToDoDto> getAllToDoByUser() {
        Integer userId=1;
        List<ToDo> toDos=toDoRepository.findByCreatedBy(userId);
        return toDos.stream().map(toDo->mapper.map(toDo, ToDoDto.class)).toList();
    }
}
