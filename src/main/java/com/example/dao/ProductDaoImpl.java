package com.example.dao;

import com.example.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

// класс помечен аннотацией @Repository, Spring внедряет его в указанное место
@Repository
public class ProductDaoImpl implements ProductDao {

    private SessionFactory sessionFactory;

    // сюда инжектим Фабрику сессий из конфигкрационного класса Hibernate
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // добавление продукта в БД
    @Override
    @Transactional
    public void addProduct(Product product) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(product);
    }

    // метод для обновления данных о продукте в БД
    @Override
    @Transactional
    public void updateProduct(Product product) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(product);
    }

    // удаление продукта по id
    @Override
    @Transactional
    public void removeProductById(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Product product = session.load(Product.class, id);
        if (product != null) {
            session.delete(product);
        }
    }

    //получение продукта по id
    @Override
    @Transactional
    public Product getProductById(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Product product = session.get(Product.class, id);
        return product;
    }

    // получения списка прожукта в зависимости от входящего параметра
    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Product> productList(String param) {
        Session session = this.sessionFactory.getCurrentSession();
        String query;
        // все продукты
        if (param == null || param.equals("all")) {
            query = "FROM Product";
            //продукты отсортированы по возрастанию
        } else if (param.equals("asc")) {
            query = "FROM Product E ORDER BY E.price ASC";
            // продукты отсортированы по убыванию
        } else if (param.equals("desc")) {
            query = "FROM Product E ORDER BY E.price DESC";
            // продукты осортированы по названию
        } else {
            query = "FROM Product E WHERE E.productname LIKE '%"+ param +"%'";
        }
        return session.createQuery(query).list();
    }
}
