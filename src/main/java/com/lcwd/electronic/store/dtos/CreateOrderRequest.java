package com.lcwd.electronic.store.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    @NotBlank(message = "cart id can't be blank")
    private String cartId;
    @NotBlank(message = "user id can't be blank")
    private String userId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOT-PAID";
    @NotBlank(message = "Address is required")
    private String billingAddress;
    @NotBlank(message = "Billing phone no is required")
    private String billingPhone;
    @NotBlank(message = "Billing name is required")
    private String billingName;

}
