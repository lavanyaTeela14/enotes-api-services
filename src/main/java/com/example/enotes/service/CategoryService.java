package com.example.enotes.service;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.CategoryResponse;
import com.example.enotes.entity.Category;

import java.util.List;

public interface CategoryService
{
    public boolean saveCategory(CategoryDto categoryDto);
    public List<CategoryDto> getAllCategory();
    public List<CategoryResponse> getActiveCategory();
    CategoryDto getCategoryById(Integer id);

    Boolean deleteCategory(Integer id);
}
