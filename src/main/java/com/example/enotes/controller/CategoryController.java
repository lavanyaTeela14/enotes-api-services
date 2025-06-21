package com.example.enotes.controller;

import com.example.enotes.entity.Category;
import com.example.enotes.service.CategoryService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody Category category) {
        Boolean isSaved = categoryService.saveCategory(category);
        if(isSaved) {
            return new ResponseEntity<>("Category saved successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Category not saved.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategory() {
        List<Category> categories = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.noContent().build();
        }else{
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }
}
