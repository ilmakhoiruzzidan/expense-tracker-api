package com.ilmazidan.expense_tracker.controller;

import com.ilmazidan.expense_tracker.dto.request.CategoryRequest;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.response.CategoryResponse;
import com.ilmazidan.expense_tracker.entity.Category;
import com.ilmazidan.expense_tracker.service.CategoryService;
import com.ilmazidan.expense_tracker.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false) String sortBy

    ) {
        PagingAndSortRequest pagingAndSortRequest = PagingAndSortRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build();
        Page<CategoryResponse> categoryResponsePage = categoryService.getAllCategories(pagingAndSortRequest);
        return ResponseUtil.buildResponsePagination(HttpStatus.OK, "Success retrieve all categories", categoryResponsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable String id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success retrieve category", categoryResponse);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.createCategory(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Successfully create category", categoryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable String id,
            @RequestBody CategoryRequest request)
    {
        CategoryResponse categoryResponse = categoryService.updateCategory(request, id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully update category", categoryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        Category category = categoryService.findOne(id);
        categoryService.deleteCategory(category.getId());
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully delete category", null);
    }
}

