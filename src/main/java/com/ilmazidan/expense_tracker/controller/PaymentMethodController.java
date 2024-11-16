package com.ilmazidan.expense_tracker.controller;

import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.request.PaymentMethodRequest;
import com.ilmazidan.expense_tracker.dto.response.PaymentMethodResponse;
import com.ilmazidan.expense_tracker.entity.PaymentMethod;
import com.ilmazidan.expense_tracker.service.PaymentMethodService;
import com.ilmazidan.expense_tracker.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

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
        return ResponseUtil.buildResponsePagination(HttpStatus.OK, "Success retrieve all payment methods", paymentMethodResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentMethodById(@PathVariable String id) {
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.getPaymentMethodById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success retrieve payment method", paymentMethodResponse);
    }

    @PostMapping
    public ResponseEntity<?> createPaymentMethod(@RequestBody PaymentMethodRequest request) {
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.createPaymentMethod(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Payment method created", paymentMethodResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentMethod(
            @PathVariable String id,
            @RequestBody PaymentMethodRequest request)
    {
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.updatePaymentMethod(request, id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Payment method updated", paymentMethodResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable String id) {
        PaymentMethod paymentMethod = paymentMethodService.getOne(id);
        paymentMethodService.deletePaymentMethod(paymentMethod.getId());
        return ResponseUtil.buildResponse(HttpStatus.OK, "Payment method deleted", null);
    }

}
