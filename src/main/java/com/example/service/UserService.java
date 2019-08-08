package com.example.service;

import com.example.entity.User;

// интерфейс для работы с сервисом юзеров
public interface UserService {

    void addUser(User user);

    User getUserById(Long id);

    User getUserByUsername(String username);

    void update(User user);

}
