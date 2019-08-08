package com.example.dao;

import com.example.entity.Cart;

import java.util.List;

// интерфейс для доступа к БД у данным о корзине с товарами
public interface CartDao {

    void addProductToCart(Long userId, Long productId);

    Integer removeProductFromCartById(Long id);

    void buyCart();

    List<Cart> getCart(Long userId);

}
