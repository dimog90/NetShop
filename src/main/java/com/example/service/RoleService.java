package com.example.service;

import com.example.entity.Role;

// интерфейс для работы с сервисом ролей
public interface RoleService {

    Role getRoleUserById(Long id);

}
