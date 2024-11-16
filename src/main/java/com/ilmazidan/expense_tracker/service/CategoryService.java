package com.ilmazidan.expense_tracker.service;

import com.ilmazidan.expense_tracker.dto.request.CategoryRequest;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.response.CategoryResponse;
import com.ilmazidan.expense_tracker.entity.Category;
import org.springframework.data.domain.Page;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(CategoryRequest request, String id);

    Page<CategoryResponse> getAllCategories(PagingAndSortRequest request);

    CategoryResponse getCategoryById(String id);

    void deleteCategory(String id);

    Category findOne(String id);
}
