package com.ilmazidan.expense_tracker.service;

import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.request.PaymentMethodRequest;
import com.ilmazidan.expense_tracker.dto.response.PaymentMethodResponse;
import com.ilmazidan.expense_tracker.entity.PaymentMethod;
import org.springframework.data.domain.Page;

public interface PaymentMethodService {
    PaymentMethodResponse createPaymentMethod(PaymentMethodRequest request);

    PaymentMethodResponse updatePaymentMethod(PaymentMethodRequest request, String id);

    PaymentMethodResponse getPaymentMethodById(String id);

    Page<PaymentMethodResponse> getAllPaymentMethods(PagingAndSortRequest request);

    void deletePaymentMethod(String id);

    PaymentMethod getOne(String id);

}
