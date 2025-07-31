package com.example.enotes.endpoint;

import com.example.enotes.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.enotes.util.Constants.ROLE_ADMIN;
import static com.example.enotes.util.Constants.ROLE_ADMIN_USER;

@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @PostMapping("/save")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);

    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllCategory();

    @GetMapping("/active")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> getActiveCategory();

    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) throws Exception;

    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id);
}
