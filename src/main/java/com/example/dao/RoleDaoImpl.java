package com.example.dao;

import com.example.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

// класс помечен аннотацией @Repository, Spring внедряет его в указанное место
@Repository
public class RoleDaoImpl implements RoleDao {

    private SessionFactory sessionFactory;

    // сюда инжектим Фабрику сессий из конфигкрационного класса Hibernate
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // получаем роль по id
    @Override
    @Transactional
    public Role getRoleUserById(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Role role = (Role) session.get(Role.class, id);
        return role;
    }
}