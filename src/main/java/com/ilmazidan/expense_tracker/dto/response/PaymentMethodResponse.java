package com.ilmazidan.expense_tracker.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentMethodResponse {
    private String id;
    private String paymentMethodType;
    private String description;
}
