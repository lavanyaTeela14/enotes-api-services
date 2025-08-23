package com.example.enotes.controller;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.CategoryResponse;
import com.example.enotes.entity.Category;
import com.example.enotes.service.CategoryService;
import com.example.enotes.util.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryDto categoryDto=null;

    private Category category=null;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Validation validation;

    @Mock
    private CategoryResponse categoryResponse;

    private List<Category> categories=new ArrayList<>();

    private List<CategoryDto> categoryDtoList=new ArrayList<>();

    private List<CategoryResponse> categoryResponses=new ArrayList<>();

    @BeforeEach
    public void intialize()
    {
        categoryDto=categoryDto.builder()
                .id(null)
                .name("Java programming")
                .description("Java programming description")
                .isActive(true)
                .build();

        //category=modelMapper.map(categoryDto, Category.class);
        category=category.builder()
                .id(null)
                .name("Java programming")
                .description("Java programming description")
                .isActive(true)
                .isDeleted(false)
                .build();

        categories.add(category);
        categoryDtoList.add(categoryDto);
    }

    @Test
    public void testSaveCategory()
    {
        when(categoryService.saveCategory(categoryDto)).thenReturn(true);
        ResponseEntity<?> responseEntity = categoryController.saveCategory(categoryDto);
        Object body = responseEntity.getBody();
        Map<String,String> json=(Map<String, String>) body;
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertEquals(json.get("status"),"success");
    }

    @Test
    public void testNotSavedCategory()
    {
        when(categoryService.saveCategory(categoryDto)).thenReturn(false);
        ResponseEntity<?> responseEntity = categoryController.saveCategory(categoryDto);
        Object body = responseEntity.getBody();
        Map<String,String > json=(Map<String, String>) body;
        assertEquals(responseEntity.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(json.get("status"),"error");
        //assertEquals(json.get("Message"),"Category not saved.");
    }
}
