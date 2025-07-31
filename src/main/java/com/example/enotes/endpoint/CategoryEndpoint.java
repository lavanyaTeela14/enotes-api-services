package com.example.enotes.endpoint;

import com.example.enotes.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCategory();

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getActiveCategory();

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) throws Exception;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id);
}
