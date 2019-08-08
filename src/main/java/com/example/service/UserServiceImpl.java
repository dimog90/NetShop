package com.example.service;

import com.example.dao.CartDao;
import com.example.dao.ProductDao;
import com.example.dao.RoleDao;
import com.example.dao.UserDao;
import com.example.entity.Role;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

//класс сервис с бизнес логикой, помечен аннотацией @Service для внедрения в классы контроллеры
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private ProductDao productDao;
    private CartDao cartDao;

    // сюда внедряются бины для получения объектов через которые получаем доступы к методам для взаимодействия с БД
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    public void setCartDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    // логика добавления юзера
    @Override
    @Transactional
    public void addUser(User user) {
        // получаем список возможных ролей
        Set<Role> roles = new HashSet<Role>();
        // присваеваем юзеру роль юзера
        roles.add(roleDao.getRoleUserById(1L));
        user.setRoles(roles);
        // добавляем запись о юзере в БД
        userDao.addUser(user);
    }

    // получение юзера по id
    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    // получение юзера по имени
    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    // обновление юзера
    @Override
    public void update(User user) {
        userDao.update(user);
    }
}
