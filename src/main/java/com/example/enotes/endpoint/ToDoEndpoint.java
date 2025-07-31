package com.example.enotes.endpoint;

import com.example.enotes.dto.ToDoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/todo")
public interface ToDoEndpoint {

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveToDo(@RequestBody ToDoDto toDoDto) throws Exception;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getToDoById(@PathVariable Integer id) throws Exception;

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllToDoByUser() throws Exception;
}
