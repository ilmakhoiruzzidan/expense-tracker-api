package com.ilmazidan.expense_tracker.service.impl;

import com.ilmazidan.expense_tracker.constant.PaymentMethodType;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.request.PaymentMethodRequest;
import com.ilmazidan.expense_tracker.dto.response.PaymentMethodResponse;
import com.ilmazidan.expense_tracker.entity.PaymentMethod;
import com.ilmazidan.expense_tracker.repository.PaymentMethodRepository;
import com.ilmazidan.expense_tracker.service.PaymentMethodService;
import com.ilmazidan.expense_tracker.specification.PaymentMethodSpecification;
import com.ilmazidan.expense_tracker.util.Mapper;
import com.ilmazidan.expense_tracker.util.SortUtil;
import com.ilmazidan.expense_tracker.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaymentMethodResponse createPaymentMethod(PaymentMethodRequest request) {
        validationUtil.validate(request);
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(UUID.randomUUID().toString())
                .paymentMethodType(PaymentMethodType.findByDescription(request.getPaymentMethod()))
                .description(request.getDescription())
                .build();
        paymentMethodRepository.savePaymentMethod(paymentMethod);
        return Mapper.toPaymentMethodResponse(paymentMethod);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaymentMethodResponse updatePaymentMethod(PaymentMethodRequest request, String id) {
        PaymentMethod paymentMethod = getOne(id);
        PaymentMethod newPaymentMethod = PaymentMethod.builder()
                .id(paymentMethod.getId())
                .paymentMethodType(PaymentMethodType.findByDescription(request.getPaymentMethod()))
                .description(request.getDescription())
                .build();
        paymentMethodRepository.updateById(newPaymentMethod);
        return Mapper.toPaymentMethodResponse(newPaymentMethod);
    }

    @Transactional(readOnly = true)
    @Override
    public PaymentMethodResponse getPaymentMethodById(String id) {
        PaymentMethod paymentMethod = getOne(id);
        return Mapper.toPaymentMethodResponse(paymentMethod);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PaymentMethodResponse> getAllPaymentMethods(PagingAndSortRequest request) {
        Sort sort = SortUtil.parseSort(request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        Specification<PaymentMethod> paymentMethodSpecification = PaymentMethodSpecification.getSpecification(request);
        Page<PaymentMethod> paymentMethods = paymentMethodRepository.findAll(paymentMethodSpecification, pageable);
        return paymentMethods.map(Mapper::toPaymentMethodResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePaymentMethod(String id) {
        PaymentMethod paymentMethod = getOne(id);
        paymentMethodRepository.deleteById(paymentMethod.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaymentMethod getOne(String id) {
        return paymentMethodRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }
}
