package com.example.enotes.service.impl;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.CategoryResponse;
import com.example.enotes.entity.Category;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.service.CategoryService;
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

    @Override
    public boolean saveCategory(CategoryDto categoryDto) {
        /*Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setIsActive(true);*/
        Category category = mapper.map(categoryDto, Category.class);
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
    public CategoryDto getCategoryById(Integer id) {
        Optional<Category> category=categoryRepository.findByIdAndIsDeletedFalse(id);
        if(category.isPresent()){
            return mapper.map(category.get(), CategoryDto.class);
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
