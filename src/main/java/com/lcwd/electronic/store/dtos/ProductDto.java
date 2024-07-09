package com.lcwd.electronic.store.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private String productId;
    private String title;

    private String description;
    private int price;
    private int quantity;
    private Date addedDate;

    private boolean live;
    private boolean stock;
    private int discountedPrice;
}
