package com.ilmazidan.expense_tracker.service.impl;

import com.ilmazidan.expense_tracker.constant.Constant;
import com.ilmazidan.expense_tracker.constant.ExpenseCategory;
import com.ilmazidan.expense_tracker.dto.request.CategoryRequest;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.response.CategoryResponse;
import com.ilmazidan.expense_tracker.entity.Category;
import com.ilmazidan.expense_tracker.repository.CategoryRepository;
import com.ilmazidan.expense_tracker.service.CategoryService;
import com.ilmazidan.expense_tracker.util.Mapper;
import com.ilmazidan.expense_tracker.util.SortUtil;
import com.ilmazidan.expense_tracker.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        validationUtil.validate(request);
        Category category = Category.builder()
                .id(UUID.randomUUID().toString())
                .description(request.getDescription())
                .category(ExpenseCategory.findByDescription(request.getCategoryName()))
                .build();
        categoryRepository.saveCategory(category);
        return Mapper.toCategoryResponse(category);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryResponse updateCategory(CategoryRequest request, String id) {
        Category category = getOne(id);
        Category newCategory = Category.builder()
                .id(category.getId())
                .category(ExpenseCategory.findByDescription(request.getCategoryName()))
                .description(request.getDescription())
                .build();

        categoryRepository.updateById(newCategory);
        return Mapper.toCategoryResponse(newCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategory(String id) {
        Category category = getOne(id);
        categoryRepository.deleteById(category.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CategoryResponse> getAllCategories(PagingAndSortRequest request) {
        Sort sort = SortUtil.parseSort(request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(Mapper::toCategoryResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryResponse getCategoryById(String id) {
        Category category = getOne(id);
        return Mapper.toCategoryResponse(category);
    }

    @Transactional(readOnly = true)
    @Override
    public Category getOne(String id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_CATEGORY_NOT_FOUND)
        );
    }
}
