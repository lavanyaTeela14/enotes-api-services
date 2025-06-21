package com.example.enotes.service;

import com.example.enotes.entity.Category;

import java.util.List;

public interface CategoryService
{
    public boolean saveCategory(Category category);
    public List<Category> getAllCategory();
}
