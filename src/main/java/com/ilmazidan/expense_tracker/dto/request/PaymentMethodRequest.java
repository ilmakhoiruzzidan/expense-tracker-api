package com.ilmazidan.expense_tracker.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethodRequest {
    private String paymentMethod;
    private String description;
}
