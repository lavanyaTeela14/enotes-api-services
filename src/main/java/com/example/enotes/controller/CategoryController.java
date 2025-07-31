package com.example.enotes.controller;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.CategoryResponse;
import com.example.enotes.endpoint.CategoryEndpoint;
import com.example.enotes.entity.Category;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.service.CategoryService;
import com.example.enotes.util.CommonUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CategoryController implements CategoryEndpoint {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseEntity<?> saveCategory(CategoryDto categoryDto) {
        Boolean isSaved = categoryService.saveCategory(categoryDto);
        if(isSaved) {
            return CommonUtil.createBuildResponseMessage("saved successfully.", HttpStatus.CREATED);
            //return new ResponseEntity<>("Category saved successfully.", HttpStatus.CREATED);
        } else {
            return CommonUtil.createErrorResponseMessage("Category not saved.", HttpStatus.INTERNAL_SERVER_ERROR);
            //return new ResponseEntity<>("Category not saved.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllCategory() {
        List<CategoryDto> categories = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.noContent().build();
        }else{
            return CommonUtil.createBuildResponse(categories, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getActiveCategory() {
        List<CategoryResponse> categories = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.noContent().build();
        }else{
            return CommonUtil.createBuildResponse(categories, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getCategoryById(Integer id) throws Exception {
      /*  try{
            CategoryDto category = categoryService.getCategoryById(id);
            if(ObjectUtils.isEmpty(category)) {
                return new ResponseEntity<>("Category not found.", HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(category, HttpStatus.OK);
            }
        }
        catch (ResourceNotFoundException e) {
            log.error("Controller::getCategoryById::Exception::", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
        CategoryDto category = categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(category)) {
            return CommonUtil.createBuildResponseMessage("Category not found.", HttpStatus.NOT_FOUND);
        }else{
            return CommonUtil.createBuildResponse(category, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> deleteCategory(Integer id) {
        Boolean isDeleted = categoryService.deleteCategory(id);
        if(isDeleted) {
            return CommonUtil.createBuildResponseMessage("Category deleted successfully.", HttpStatus.OK);
        } else {
            return CommonUtil.createErrorResponseMessage("Category not deleted.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
