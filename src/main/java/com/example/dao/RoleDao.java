package com.example.dao;

import com.example.entity.Role;

// интерфейс для доступа к данным о ролях пользователей
public interface RoleDao {

    Role getRoleUserById(Long id);
}
