package com.example.service;

import com.example.dao.RoleDao;
import com.example.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// класс сервис с бизнес логикой, помечен аннотацией @Service для внедрения в классы контроллеры
@Service
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    // сюда внедряются бины для получения объектов через которые получаем доступы к методам для взаимодействия с БД
    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    // получение роли по id
    @Override
    public Role getRoleUserById(Long id) {
        return roleDao.getRoleUserById(id);
    }
}
