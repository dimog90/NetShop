package com.example.dao;

import com.example.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

// класс помечен аннотацией @Repository, Spring внедряет его в указанное место
@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    // сюда инжектим Фабрику сессий из конфигкрационного класса Hibernate
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //получение юзера по id
    @Override
    @Transactional
    public User getUserById(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        return (User) session.load(User.class, new Long(id));
    }

    // добавление юзера в БД
    @Override
    @Transactional
    public void addUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
    }

    //олучение юзера по имени
    @Override
    @Transactional
    public User getUserByUsername(String username) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria userCriteria = session.createCriteria(User.class);
        userCriteria.add(Restrictions.eq("username", username));
        User user = (User) userCriteria.uniqueResult();
        return user;
    }

    // обновление данных о юзере
    @Override
    @Transactional
    public void update(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(user);
    }

}