package com.example.service;

import com.example.dao.CartDao;
import com.example.dao.ProductDao;
import com.example.entity.Cart;
import com.example.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

// класс сервис с бизнес логикой, помечен аннотацией @Service для внедрения в классы контроллеры
@Service
public class CartServiceImpl implements CartService {

    private CartDao cartDao;
    private ProductDao productDao;

    // сюда внедряются бины для получения объектов через которые получаем доступы к методам для взаимодействия с БД
    @Autowired
    public void setCartDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    // метод с логикой добавления продукта в корзину
    @Override
    @Transactional
    public void addProductToCart(Long userId, Long productId) {
        // получаем продукт по id из БД
        Product product = productDao.getProductById(productId);
        // добавление продукта возможно при его количестве больше нуля
        if (product.getCount() > 0) {
            // добавление продукта в корзину
            cartDao.addProductToCart(userId, productId);
            // у объекта продукт из поля "количество" отнимаем единицу
            product.setCount(product.getCount() - 1);
            // обновляем информацию о продукте в БД
            productDao.updateProduct(product);
        }
    }

    // получаем список товаров в корзине у пользователя
    @Override
    public List<Cart> getCart(Long userId) {
        return cartDao.getCart(userId);
    }

    // удаление продукт из корзины
    @Override
    @Transactional
    public void removeProductFromCartById(Long id) {
        // получаем количество удаляемых продуктов и удаляем в БД записи об этих товарах в корзине
        Integer count = cartDao.removeProductFromCartById(id);
        // получаем объект продукт)
        Product product = productDao.getProductById(id);
        // обновляем данные о количестве продукта в магазине
        product.setCount(product.getCount() + count);
        // обновляем инфо о продукте в БД
        productDao.updateProduct(product);
    }

    // покупка товаров
    @Override
    public void buyCart() {
        cartDao.buyCart();
    }
}
