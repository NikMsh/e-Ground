package com.bsuir.sdtt.controller;

import com.bsuir.sdtt.dto.CategoryDto;
import com.bsuir.sdtt.entity.Category;
import com.bsuir.sdtt.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class of Offer REST Controller. Contains CRUD methods.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api/v1/catalog/categories")
public class CategoryController {
    /**
     * Field of Category Service.
     */
    private final CategoryService categoryService;

    /**
     * Field of Model Mapper converter.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor that accepts a object CategoryService class.
     *
     * @param categoryService object of CategoryService class
     */
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
        this.modelMapper = new ModelMapper();
    }

    /**
     * Method that converts DTO to class object and create it.
     *
     * @param categoryDto data transfer object
     * @return created object of Category class
     */
    @PostMapping
    public CategoryDto create(@Validated @RequestBody CategoryDto categoryDto) {
        Category categoryTemp = new Category();
        modelMapper.map(categoryDto, categoryTemp);
        CategoryDto categoryDtoTemp = new CategoryDto();
        modelMapper.map(categoryService.create(categoryTemp), categoryDtoTemp);
        return categoryDtoTemp;
    }

    /**
     * Method that finds an object.
     *
     * @param id Long of the object to be found
     * @return founded object or NullPointerException
     */
    @GetMapping(path = "/{id}")
    public CategoryDto getById(@PathVariable("id") UUID id) {
        CategoryDto categoryDtoTemp = new CategoryDto();
        modelMapper.map(categoryService.findById(id), categoryDtoTemp);
        return categoryDtoTemp;
    }

    /**
     * Method that finds all objects.
     *
     * @return founded objects
     */
    @GetMapping
    public List<CategoryDto> getAll() {
        List<CategoryDto> categoriesDtoTemp = new ArrayList<>();
        List<Category> categoriesTemp = categoryService.findAll();

        for (Category category : categoriesTemp) {
            CategoryDto categoryDto = new CategoryDto();
            modelMapper.map(category, categoryDto);
            categoriesDtoTemp.add(categoryDto);
        }

        return categoriesDtoTemp;
    }

    /**
     * Method that save updated object.
     *
     * @param categoryDto updated category that needs to save
     * @return updated and saved category
     */
    @PutMapping
    public CategoryDto update(@Validated @RequestBody CategoryDto categoryDto) {
        Category categoryTemp = new Category();
        modelMapper.map(categoryDto, categoryTemp);
        CategoryDto categoryDtoTemp = new CategoryDto();
        modelMapper.map(categoryService.update(categoryTemp), categoryDtoTemp);
        return categoryDtoTemp;
    }

    /**
     * Method that delete object.
     *
     * @param id Long object that needs to delete
     */
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") UUID id) {
        categoryService.delete(id);
    }
}