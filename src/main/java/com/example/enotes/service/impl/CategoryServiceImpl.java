package com.example.enotes.service.impl;

import com.example.enotes.entity.Category;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean saveCategory(Category category) {
        category.setIsDeleted(false);
        category.setCreatedOn(new Date());
        category.setCreatedBy(1);
        Category savedCategory = categoryRepository.save(category);
        if(ObjectUtils.isEmpty(savedCategory)){
            return false;
        }
        return true;
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }
}
