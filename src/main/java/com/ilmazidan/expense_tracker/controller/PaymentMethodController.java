package com.ilmazidan.expense_tracker.controller;

import com.ilmazidan.expense_tracker.constant.Constant;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.request.PaymentMethodRequest;
import com.ilmazidan.expense_tracker.dto.response.PaymentMethodResponse;
import com.ilmazidan.expense_tracker.entity.PaymentMethod;
import com.ilmazidan.expense_tracker.service.PaymentMethodService;
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
@RequestMapping(Constant.PAYMENT_METHODS_API)
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Payment Method Management", description = "Manage payment method")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @Operation(summary = "Retrieve all payment methods with pagination and sorting")
    @GetMapping
    public ResponseEntity<?> getAllPaymentMethods(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false) String sortBy
    ) {
        PagingAndSortRequest pagingAndSortRequest = PagingAndSortRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build();
        Page<PaymentMethodResponse> paymentMethodResponses = paymentMethodService.getAllPaymentMethods(pagingAndSortRequest);
        return ResponseUtil.buildResponsePagination(HttpStatus.OK, SUCCESS_RETRIEVE_ALL_PAYMENT_METHODS, paymentMethodResponses);
    }

    @Operation(summary = "Retrieve a specific payment method by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentMethodById(@PathVariable String id) {
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.getPaymentMethodById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_RETRiEVE_PAYMENT_METHOD, paymentMethodResponse);
    }

    @Operation(summary = "Create a new payment method")
    @PostMapping
    public ResponseEntity<?> createPaymentMethod(@RequestBody PaymentMethodRequest request) {
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.createPaymentMethod(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, SUCCESS_CREATE_PAYMENT_METHOD, paymentMethodResponse);
    }

    @Operation(summary = "Update an existing payment method")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentMethod(
            @PathVariable String id,
            @RequestBody PaymentMethodRequest request) {
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.updatePaymentMethod(request, id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_UPDATE_PAYMENT_METHOD, paymentMethodResponse);
    }

    @Operation(summary = "Delete a payment method by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable String id) {
        PaymentMethod paymentMethod = paymentMethodService.getOne(id);
        paymentMethodService.deletePaymentMethod(paymentMethod.getId());
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_DELETE_PAYMENT_METHOD, null);
    }
}
