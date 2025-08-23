package com.example.enotes.service.impl;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.CategoryResponse;
import com.example.enotes.entity.Category;
import com.example.enotes.exception.ExistingDataException;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.service.CacheService;
import com.example.enotes.service.CategoryService;
import com.example.enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
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

    @Autowired
    private CacheService cacheService;

    @Override
    public boolean saveCategory(CategoryDto categoryDto) {
        validation.categoryValidation(categoryDto);

        Boolean existingCategory=categoryRepository.existsByName(categoryDto.getName().trim());
        if(existingCategory){
            throw new ExistingDataException("Category already exists");
        }

        Category category = mapper.map(categoryDto, Category.class);
        if(ObjectUtils.isEmpty(category.getId())){
            category.setIsDeleted(false);
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
            category.setIsDeleted(existingCategory.getIsDeleted());
        }
    }

    @Override
    @Cacheable("allCategory")
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findByIsDeletedFalse();
        List<CategoryDto> categoryDtoList = categories.stream().map(category -> mapper.map(category, CategoryDto.class)).toList();
        return categoryDtoList;
    }

    @Override
    @Cacheable("activeCategory")
    public List<CategoryResponse> getActiveCategory() {
     List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
     List<CategoryResponse> categoryResponses = categories.stream().map(category->mapper.map(category, CategoryResponse.class)).toList();
     return categoryResponses;
    }

    @Override
    @Cacheable("getCategoryById")
    public CategoryDto getCategoryById(Integer id) throws Exception{
        Category category=categoryRepository.findByIdAndIsDeletedFalse(id).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        if(!ObjectUtils.isEmpty(category)){
            return mapper.map(category, CategoryDto.class);
        }
        return null;
    }

    @Override
    @CacheEvict(value = "getCategoryById",key = "#id")
    public Boolean deleteCategory(Integer id) {
        Optional<Category> findByCategory=categoryRepository.findById(id);
        if(findByCategory.isPresent()){
            Category category = findByCategory.get();
            category.setIsDeleted(true);
            categoryRepository.save(category);
            cacheService.removeCacheByName(Arrays.asList("activeCategory","allCategory"));
            return true;
        }
        return false;
    }
}
