package com.lcwd.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    private String cartId;
    private Date createdAt;
    @OneToOne
    private User user;
    //mapping cart items

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "cart")
    private List<CartItem> items=new ArrayList<>();

}
