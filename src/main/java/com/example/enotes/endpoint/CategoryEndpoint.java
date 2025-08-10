package com.example.enotes.endpoint;

import com.example.enotes.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.enotes.util.Constants.ROLE_ADMIN;
import static com.example.enotes.util.Constants.ROLE_ADMIN_USER;

@RequestMapping("/api/v1/category")
@Tag(name = "Category",description = "All category API's")
public interface CategoryEndpoint {

    @Operation(summary = "Save Category", tags = {"Category"}, description = "Admin save category")
    @PostMapping("/save")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);

    @Operation(summary = "Get All Category", tags = {"Category"}, description = "Admin Get all category")
    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllCategory();

    @Operation(summary = "Get active Category", tags = {"Category"}, description = "Admin,User get active category")
    @GetMapping("/active")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> getActiveCategory();

    @Operation(summary = "Get Category By id", tags = {"Category"}, description = "Admin Get Category By id")
    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) throws Exception;

    @Operation(summary = "Delete category", tags = {"Category"},description = "Admin delete category")
    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id);
}
