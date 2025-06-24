package com.example.enotes.service.impl;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.CategoryResponse;
import com.example.enotes.entity.Category;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.service.CategoryService;
import com.example.enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Validation validation;

    @Override
    public boolean saveCategory(CategoryDto categoryDto) {
        validation.categoryValidation(categoryDto);
        Category category = mapper.map(categoryDto, Category.class);
        if(ObjectUtils.isEmpty(category.getId())){
            category.setIsDeleted(false);
            category.setCreatedOn(new Date());
            category.setCreatedBy(1);
        }
        else
        {
            updateCategory(category);
        }

        Category savedCategory = categoryRepository.save(category);
        if(ObjectUtils.isEmpty(savedCategory)){
            return false;
        }
        return true;
    }

    private void updateCategory(Category category) {
        Optional<Category> findById=categoryRepository.findById(category.getId());
        if(findById.isPresent()) {
            Category existingCategory = findById.get();
            category.setCreatedOn(existingCategory.getCreatedOn());
            category.setCreatedBy(existingCategory.getCreatedBy());
            category.setUpdatedOn(new Date());
            category.setUpdatedBy(1);
            category.setIsDeleted(existingCategory.getIsDeleted());
        }
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findByIsDeletedFalse();
        List<CategoryDto> categoryDtoList = categories.stream().map(category -> mapper.map(category, CategoryDto.class)).toList();
        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
     List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
     List<CategoryResponse> categoryResponses = categories.stream().map(category->mapper.map(category, CategoryResponse.class)).toList();
     return categoryResponses;
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws Exception{
        Category category=categoryRepository.findByIdAndIsDeletedFalse(id).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        if(!ObjectUtils.isEmpty(category)){
            return mapper.map(category, CategoryDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        Optional<Category> findByCategory=categoryRepository.findById(id);
        if(findByCategory.isPresent()){
            Category category = findByCategory.get();
            category.setIsDeleted(true);
            categoryRepository.save(category);
            return true;
        }
        return false;
    }
}
