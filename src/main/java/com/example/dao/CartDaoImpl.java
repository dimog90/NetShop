package com.example.dao;

import com.example.entity.Cart;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

// класс помечен аннотацией @Repository, Spring внедряет его в указанное место
@Repository
public class CartDaoImpl implements CartDao {

    private SessionFactory sessionFactory;

    // сюда инжектим Фабрику сессий из конфигкрационного класса Hibernate
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // метод для сохранения объекта корзина в БД, запись будет содержать id товара и id юзера который его поместил в корзину
    @Override
    @Transactional
    public void addProductToCart(Long userId, Long productId) {
        // устанавливаем сессию
        Session session = this.sessionFactory.getCurrentSession();
        // создаем объект
        Cart cart = new Cart();
        cart.setUserid(userId);
        cart.setProductid(productId);
        // из сессии вызываем Hibernate метод для добавления записи в БД
        session.save(cart);
    }

    // метод для получения списка покупок юзера
    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Cart> getCart(Long userId) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Cart.class);
        List<Cart> cart = criteria.add(Restrictions.eq("userid", userId)).list();
        return cart;
    }

    // удаление записи с продуктом из таблицы корзина
    @Override
    @Transactional
    public Integer removeProductFromCartById(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Cart WHERE productid = " + id);
        Integer count = query.getResultList().size();
        query = session.createQuery("DELETE Cart WHERE productid = " + id);
        query.executeUpdate();
        return count;
    }

    // удаление товаров из корзины при покупке товаров
    @Override
    @Transactional
    public void buyCart() {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Cart c");
        query.executeUpdate();
    }
}
