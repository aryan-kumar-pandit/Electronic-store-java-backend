package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import com.lcwd.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category=mapper.map(categoryDto, Category.class);
        categoryRepository.save(category);

        return mapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category saved = categoryRepository.save(category);
        CategoryDto categoryDto1 = mapper.map(saved, CategoryDto.class);
        return categoryDto1;
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found"));

        categoryRepository.delete(category);

    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNo,int pageSize,String sortBy,String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<Category> categories = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(categories, CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found"));
        CategoryDto mapped = mapper.map(category, CategoryDto.class);
        return mapped;
    }
}
