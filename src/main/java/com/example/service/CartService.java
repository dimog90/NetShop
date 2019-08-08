package com.example.service;

import com.example.entity.Cart;

import java.util.List;

// интерфейс для работы с сервисом корзины
public interface CartService {

    List<Cart> getCart(Long userId);

    void addProductToCart(Long userId, Long productId);

    void removeProductFromCartById(Long id);

    void buyCart();
}
