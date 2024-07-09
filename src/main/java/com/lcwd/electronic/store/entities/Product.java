package com.lcwd.electronic.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class Product {
    @Id
    private String productId;
    private String title;
    @Column(length = 100)
    private String description;
    private int price;
    private int quantity;
    private Date addedDate;

    private boolean live;
    private boolean stock;
    private int discountedPrice;


}
