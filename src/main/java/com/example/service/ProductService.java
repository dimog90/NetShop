package com.example.service;

import com.example.entity.Product;

import java.util.List;

// интерфейс для работы с продуктами
public interface ProductService {

    void addProduct(Product product);

    void updateProduct(Product product);

    void removeProductById(Long id);

    Product getProductById(Long id);

    List<Product> productList(String param);

    List<Product> getCart(Long userId);
}
