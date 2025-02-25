package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;

public interface CategoryService {
    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    //delete
    void delete(String categoryId);

    //getAll
    PageableResponse<CategoryDto> getAll(int pageNo,int pageSize,String sortBy,String sortDir);

    //get single category details
    CategoryDto get(String categoryId);

}
