package com.example.service;

import com.example.dao.CartDao;
import com.example.dao.ProductDao;
import com.example.entity.Cart;
import com.example.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

// класс сервис с бизнес логикой, помечен аннотацией @Service для внедрения в классы контроллеры
@Service
public class ProductServiceImpl implements ProductService {

    private ProductDao productDao;
    private CartDao cartDao;

    // сюда внедряются бины для получения объектов через которые получаем доступы к методам для взаимодействия с БД
    @Autowired
    public void setCartDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    // добавление продукта в БД
    @Override
    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    // обновление данных о продукте в БД
    @Override
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    // удаление продукта
    @Override
    public void removeProductById(Long id) {
        productDao.removeProductById(id);
    }

    // получение продукта по id
    @Override
    public Product getProductById(Long id) {
        return productDao.getProductById(id);
    }

    // получение списка продуктов
    @Override
    public List<Product> productList(String param) {
        return productDao.productList(param);
    }

    // получение списка продуктов в корзине
    @Override
    @Transactional
    public List<Product> getCart(Long userId) {
        List<Product> productList = new ArrayList<>();
        List<Cart> cart = cartDao.getCart(userId);
        if (cart == null) {
            return productList;
        }
        for (Cart elem : cart) {
            productList.add(productDao.getProductById(elem.getProductid()));
        }
        return productList;
    }

}

