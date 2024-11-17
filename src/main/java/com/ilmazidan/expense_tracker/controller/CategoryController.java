package com.ilmazidan.expense_tracker.controller;

import com.ilmazidan.expense_tracker.dto.request.CategoryRequest;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.response.CategoryResponse;
import com.ilmazidan.expense_tracker.entity.Category;
import com.ilmazidan.expense_tracker.service.CategoryService;
import com.ilmazidan.expense_tracker.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ilmazidan.expense_tracker.constant.Constant.*;

@RestController
@RequestMapping(CATEGORIES_API)
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Category Management", description = "Manage category")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Retrieve all categories with pagination and sorting")
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
        return ResponseUtil.buildResponsePagination(HttpStatus.OK, SUCCESS_RETRIEVE_ALL_CATEGORIES, categoryResponsePage);
    }

    @Operation(summary = "Retrieve a specific category by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable String id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_RETRIEVE_CATEGORY, categoryResponse);
    }

    @Operation(summary = "Create a new category")
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.createCategory(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, SUCCESS_CREATE_CATEGORY, categoryResponse);
    }

    @Operation(summary = "Update an existing category")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable String id,
            @RequestBody CategoryRequest request
    ) {
        CategoryResponse categoryResponse = categoryService.updateCategory(request, id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_UPDATE_CATEGORY, categoryResponse);
    }

    @Operation(summary = "Delete a category by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        Category category = categoryService.getOne(id);
        categoryService.deleteCategory(category.getId());
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_DELETE_CATEGORY, null);
    }
}
