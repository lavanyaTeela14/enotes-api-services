package com.example.enotes.endpoint;

import com.example.enotes.dto.ToDoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ToDo",description = "All ToDo API's")
@RequestMapping("/api/v1/todo")
public interface ToDoEndpoint {

    @Operation(summary = "Save ToDo notes",tags = {"Notes"},description = "User ToDo notes")
    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveToDo(@RequestBody ToDoDto toDoDto) throws Exception;

    @Operation(summary = "Get ToDo notes by ID",tags = {"Notes"},description = "Get ToDo user notes")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getToDoById(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get all user ToDo notes",tags = {"Notes"},description = "Get all user ToDo notes")
    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllToDoByUser() throws Exception;
}
