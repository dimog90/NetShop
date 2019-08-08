package com.example.dao;

import com.example.entity.User;

// интерфейс для доступа к данным о юзере
public interface UserDao {

    void addUser(User user);

    User getUserById(Long id);

    User getUserByUsername(String username);

    void update(User user);

}
