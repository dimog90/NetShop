package com.example.dao;

import com.example.entity.Product;

import java.util.List;

// интерфейс для доступа к БД для получения данных о продуктах
public interface ProductDao {

    void addProduct(Product product);

    void updateProduct(Product product);

    void removeProductById(Long id);

    Product getProductById(Long id);

    List<Product> productList(String param);
}
