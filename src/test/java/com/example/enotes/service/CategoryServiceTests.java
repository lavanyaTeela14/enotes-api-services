package com.example.enotes.service;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.CategoryResponse;
import com.example.enotes.dto.NotesDto;
import com.example.enotes.entity.Category;
import com.example.enotes.exception.ExistingDataException;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.service.impl.CategoryServiceImpl;
import com.example.enotes.util.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

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
    public void TestSaveCategory(){

        when(categoryRepository.existsByName(categoryDto.getName().trim())).thenReturn(false);
        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);

        Boolean saveCategory=categoryService.saveCategory(categoryDto);

        Assertions.assertTrue(saveCategory);

        verify(validation).categoryValidation(categoryDto);
        verify(categoryRepository).existsByName(categoryDto.getName().trim());
        verify(categoryRepository).save(category);
    }

    @Test
    public void TestExistsCategory()
    {
        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(true);
        ExistingDataException exception = assertThrows(ExistingDataException.class, () -> {
            categoryService.saveCategory(categoryDto);
        });

        assertEquals("Category already exists",exception.getMessage());
        verify(validation).categoryValidation(categoryDto);
        verify(categoryRepository).existsByName(categoryDto.getName());
        verify(categoryRepository,never()).save(category);
    }

    @Test
    public void TestUpdateCategory(){
        category.setId(1);
        categoryDto.setId(1);
        when(categoryRepository.existsByName(categoryDto.getName().trim())).thenReturn(false);
        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);

        Boolean saveCategory=categoryService.saveCategory(categoryDto);

        Assertions.assertTrue(saveCategory);

        verify(validation).categoryValidation(categoryDto);
        verify(categoryRepository).existsByName(categoryDto.getName().trim());
        verify(categoryRepository).save(category);
    }

    @Test
    public void TestGetAllCategory()
    {
        when(categoryRepository.findByIsDeletedFalse()).thenReturn(categories);
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        assertEquals(allCategory.size(),categories.size());
        verify(categoryRepository).findByIsDeletedFalse();
    }

    /*@Test
    public void TestGetActiveCategory()
    {
        when(categoryRepository.findByIsActiveTrueAndIsDeletedFalse()).thenReturn(categoryResponses);
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        assertEquals(allCategory.size(),categories.size());
        verify(categoryRepository).findByIsActiveTrueAndIsDeletedFalse();
    }*/
}
