package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;

public interface CartService {

    // add item to cart
    //Case 1 : cart for user not available: we will create the cart and then add the item
    //Case 2 : cart available : add the items
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove from cart
    void removeItemFromCart(String userId,int cartItem);

    //remove all items from cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);

}
